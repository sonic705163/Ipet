package idv.randy.me;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.java.iPet.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import idv.randy.ut.AsyncAdapter;
import idv.randy.ut.AsyncObjTask;
import idv.randy.ut.Me;

import static android.content.Context.MODE_PRIVATE;

public class RegisterFragment extends Fragment implements View.OnClickListener {

    private registerFragmentListener mListener;
    private Button btLogin;
    private View view;
    private JsonObject jsonObject;
    private Button btSet1;
    private Button btSet2;
    private Button btRegister;
    private Integer memNo;
    private EditText etMemName;
    private EditText etID;
    private EditText etPD;
    private ImageView ivMemImg;
    private LinearLayout llPhotoLib;
    private LinearLayout llCamera;
    String imageBase64;
    private File file;
    private static final int REQUESTCODE_SHOT = 1;
    private static final int REQUESTCODE_PHOTO_LIB = 2;
    private static final String TAG = "RegisterFragment";
    private byte[] image;
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public RegisterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.r_fragment_register, container, false);
        findViews();
        pref = getActivity().getSharedPreferences("UserData", MODE_PRIVATE);
        editor = getActivity().getSharedPreferences("UserData", MODE_PRIVATE).edit();
        btSet1.setOnClickListener(v1 -> {
            int count = pref.getInt("count", 1);
            etID.setText("Randy00" + count);
            etPD.setText("123456");
            etMemName.setText("陳致遠0" + count);
            count += 1;
            editor.putInt("count", count);
            editor.apply();
        });

        btRegister.setOnClickListener(v -> {
            String id = etID.getText().toString().trim();
            String pd = etPD.getText().toString().trim();
            String memName = etMemName.getText().toString().trim();
            if (id.length() == 0 || pd.length() == 0) {
                Toast.makeText(Me.gc(), "帳號或密碼不可以空白", Toast.LENGTH_SHORT).show();
            } else {
                if (image != null) {
                    imageBase64 = Base64.encodeToString(image, Base64.DEFAULT);
                } else {
                    imageBase64 = "";
                }
                try {
                    if (isValid(id, pd, memName)) {
                        memNo = jsonObject.get("memNo").getAsInt();
                        editor.putInt("memNo", memNo);
                        editor.putBoolean("login", true);
                        editor.apply();
                        Toast.makeText(Me.gc(), "註冊成功", Toast.LENGTH_SHORT).show();
                        mListener.login();
                    } else {
                        editor.putBoolean("login", false);
                        editor.apply();
                        Toast.makeText(Me.gc(), "註冊失敗", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
//            etPD.setText("");
        });
        ivMemImg.setOnClickListener(this);
        llCamera.setOnClickListener(this);
        llPhotoLib.setOnClickListener(this);
        makeIconGone();
        return view;
    }

    private void makeIconGone() {
        llCamera.setVisibility(View.GONE);
        llPhotoLib.setVisibility(View.GONE);
    }

    private void findViews() {
        etID = (EditText) view.findViewById(R.id.etID);
        etPD = (EditText) view.findViewById(R.id.etPD);
        llCamera = (LinearLayout) view.findViewById(R.id.llCamera);
        llPhotoLib = (LinearLayout) view.findViewById(R.id.llPhotoLib);
        etMemName = (EditText) view.findViewById(R.id.etMemName);
        btRegister = (Button) view.findViewById(R.id.btRegister);
        btSet1 = (Button) view.findViewById(R.id.btSet1);
        ivMemImg = (ImageView) view.findViewById(R.id.ivMemImg);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof registerFragmentListener) {
            mListener = (registerFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement registerFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private boolean isValid(String id, String pd, String memName) throws JSONException {
        jsonObject = new JsonObject();
        jsonObject.addProperty("action", "Register");
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("pd", pd);
        jsonObject.addProperty("imageBase64", imageBase64);
        jsonObject.addProperty("memName", memName);
        boolean isValid = false;
        try {
            String inputString = new AsyncObjTask(new AsyncAdapter(), jsonObject).execute(Me.MembersServlet).get();
            jsonObject = new Gson().fromJson(inputString, JsonObject.class);
            isValid = jsonObject.get("isValid").getAsBoolean();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivMemImg:
                llCamera.setVisibility(View.VISIBLE);
                llPhotoLib.setVisibility(View.VISIBLE);
                break;
            case R.id.llCamera:
                if (ContextCompat.checkSelfPermission(Me.gc(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    String state = Environment.getExternalStorageState();
                    if (state.equals(Environment.MEDIA_MOUNTED)) {
                        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                        file = new File(file, "picture.jpg");
                        Uri uri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    }
                    if (isIntentAvailable(getActivity(), intent)) {
                        startActivityForResult(intent, REQUESTCODE_SHOT);
                    } else {
                        Toast.makeText(getActivity(), "No Camera Apps Found",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.llPhotoLib:
                if (ContextCompat.checkSelfPermission(Me.gc(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUESTCODE_PHOTO_LIB);
                }
                break;
        }
    }


    public interface registerFragmentListener {
        void login();

        void cancel();
    }

    private boolean isIntentAvailable(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");
        if (resultCode == getActivity().RESULT_OK) {
            makeIconGone();
            switch (requestCode) {
                case REQUESTCODE_SHOT:
                    Log.d(TAG, "onActivityResult: " + file.getPath());
                    Bitmap bitmap1 = BitmapFactory.decodeFile(file.getPath());
                    bitmap1 = Me.downSize(bitmap1, 512);
                    ivMemImg.setImageBitmap(bitmap1);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap1.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    image = byteArrayOutputStream.toByteArray();
                    break;
                case REQUESTCODE_PHOTO_LIB:
                    Uri uri = data.getData();
                    String[] columns = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getActivity().getContentResolver().query(uri, columns,
                            null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        String imagePath = cursor.getString(0);
                        cursor.close();
                        Bitmap bitmap2 = BitmapFactory.decodeFile(imagePath);
                        bitmap2 = Me.downSize(bitmap2, 512);
                        ivMemImg.setImageBitmap(bitmap2);
                        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
                        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, out2);
                        image = out2.toByteArray();
                    }
                    break;
                default:
                    Toast.makeText(getActivity(), "no react", Toast.LENGTH_SHORT).show();
                    break;
            }


        }
    }

}
