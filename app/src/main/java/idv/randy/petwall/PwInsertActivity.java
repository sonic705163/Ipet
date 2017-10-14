package idv.randy.petwall;

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
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java.iPet.R;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutionException;

import idv.randy.ut.AsyncObjTask;
import idv.randy.ut.Me;

public class PwInsertActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout ivCamera;
    LinearLayout ivPhotoLib;
    ImageView ivPwPicture;
    EditText etPwContent;
    TextView tvSend;
    TextView tvCancel;
    private byte[] image;
    File file;
    String imageBase64;
    String pwContent;
    Toolbar toolbar;
    private static final int REQUESTCODE_SHOT = 1;
    private static final int REQUESTCODE_PHOTO_LIB = 2;
    private static final String TAG = "PwInsertActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_activity_pw_insert);
        findViews();
        setSupportActionBar(toolbar);
        ivCamera.setOnClickListener(this);
        ivPhotoLib.setOnClickListener(this);
        tvCancel.setOnClickListener(this);
        tvSend.setOnClickListener(this);

    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ivCamera = (LinearLayout) findViewById(R.id.ivCamera);
        ivPhotoLib = (LinearLayout) findViewById(R.id.ivPhotoLib);
        ivPwPicture = (ImageView) findViewById(R.id.ivPwPicture);
        tvSend = (TextView) findViewById(R.id.tvSend);
        etPwContent = (EditText) findViewById(R.id.etFeedback);
        tvCancel = (TextView)findViewById(R.id.tvCancel);
    }

    private boolean isIntentAvailable(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: ");
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUESTCODE_SHOT:
                    Log.d(TAG, "onActivityResult: " + file.getPath());
                    Bitmap bitmap1 = BitmapFactory.decodeFile(file.getPath());
                    bitmap1 = Me.downSize(bitmap1, 512);
                    ivPwPicture.setImageBitmap(bitmap1);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap1.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    image = byteArrayOutputStream.toByteArray();
                    break;
                case REQUESTCODE_PHOTO_LIB:
                    Uri uri = data.getData();
                    String[] columns = {MediaStore.Images.Media.DATA};
                    Cursor cursor = getContentResolver().query(uri, columns,
                            null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        String imagePath = cursor.getString(0);
                        cursor.close();
                        Bitmap bitmap2 = BitmapFactory.decodeFile(imagePath);
                        bitmap2 = Me.downSize(bitmap2, 512);
                        ivPwPicture.setImageBitmap(bitmap2);
                        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
                        bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, out2);
                        image = out2.toByteArray();
                    }
                    break;
                default:
                    Toast.makeText(this, "no react", Toast.LENGTH_SHORT).show();
                    break;
            }


        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivCamera:
                if (ContextCompat.checkSelfPermission(Me.gc(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PwInsertActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    String state = Environment.getExternalStorageState();
                    if (state.equals(Environment.MEDIA_MOUNTED)) {
                        file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                        file = new File(file, "picture.jpg");
                        Uri uri = FileProvider.getUriForFile(PwInsertActivity.this, getPackageName() + ".provider", file);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    }
                    if (isIntentAvailable(PwInsertActivity.this, intent)) {
                        startActivityForResult(intent, REQUESTCODE_SHOT);
                    } else {
                        Toast.makeText(PwInsertActivity.this, "No Camera Apps Found",
                                Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.ivPhotoLib:
                if (ContextCompat.checkSelfPermission(Me.gc(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(PwInsertActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUESTCODE_PHOTO_LIB);
                }
                break;
            case R.id.tvCancel:
                finish();
                break;
            case R.id.tvSend:
                SharedPreferences pref = getSharedPreferences("UserData", MODE_PRIVATE);
                if (image != null) {
                    imageBase64 = Base64.encodeToString(image, Base64.DEFAULT);
                } else {
                    imageBase64 = "";
                }
                pwContent = etPwContent.getText().toString().trim();
                if (imageBase64.equals("") && pwContent.equals("")) {
                    Toast.makeText(Me.gc(), "寫點什麼...", Toast.LENGTH_SHORT).show();
                    return;
                }
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "insertPw");
                jsonObject.addProperty("imageBase64", imageBase64);
                jsonObject.addProperty("pwContent", etPwContent.getText().toString());
                jsonObject.addProperty("memNo", pref.getInt("memNo", 0));
                try {
                    new AsyncObjTask(null, jsonObject).execute(Me.PetServlet).get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                finish();

                break;
            default:
                break;

        }
    }
}
