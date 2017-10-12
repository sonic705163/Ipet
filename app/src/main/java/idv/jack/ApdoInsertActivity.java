package idv.jack;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.java.iPet.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import idv.randy.petwall.PwInsertActivity;
import idv.randy.ut.Me;

public class ApdoInsertActivity extends AppCompatActivity {
    String TAG ="ApdoInsertActivity";
    private EditText edpetname  , edpetcloor, edposition , edsituation;
    private Button btFinishInsert,btpic,bttp;
    private ImageView ivp;
    private byte[] image;
    private File file;
    private RadioGroup rdage ,rdsex , rdsize ,rdpetic ,rdtnr;
    private Case cs = new Case();
    private static final int REQUEST_TAKE_PICTURE =0;
    private static final int REQUEST_PICK_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apdo_insert);
        findViews();
        rdage.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RadioButton radiobutton = (RadioButton) radioGroup.findViewById(i);
                String petAge = radiobutton.getText().toString();
                cs.setPetAge(petAge);
            }
        });
        rdsex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RadioButton radiobutton = (RadioButton) radioGroup.findViewById(i);
                String petSex = radiobutton.getText().toString();
                cs.setPetSex(petSex);
            }
        });
        rdsize.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RadioButton radiobutton = (RadioButton) radioGroup.findViewById(i);
                String petSize = radiobutton.getText().toString();
                cs.setPetSize(petSize);
            }
        });
        rdpetic.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RadioButton radiobutton = (RadioButton) radioGroup.findViewById(i);
                String petIc = radiobutton.getText().toString();
                cs.setPetIc(petIc);
            }
        });
        rdtnr.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int i) {
                RadioButton radiobutton = (RadioButton) radioGroup.findViewById(i);
                String TNR = radiobutton.getText().toString();
                cs.setTNR(TNR);

            }
        });
        bttp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Me.gc(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ApdoInsertActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE
                    }, 1);
                } else {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                    file = new File(file, "picture.jpg");
                    Uri contentUri = FileProvider.getUriForFile(view.getContext(), getPackageName() + ".provider", file);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                    if (isIntentAvailable(view.getContext(), intent)) {
                        startActivityForResult(intent, REQUEST_TAKE_PICTURE);
                    } else {
                        Toast.makeText(view.getContext(), "沒照片", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btpic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(Me.gc(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(ApdoInsertActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQUEST_PICK_IMAGE);
                }
            }
        });
        btFinishInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String petName = edpetname.getText().toString().trim();
                String petColor = edpetcloor.getText().toString().trim();
                String petPosition  = edposition.getText().toString().trim();
                String situation = edsituation.getText().toString().trim();
                cs.setPetName(petName);
                cs.setPetColor(petColor);
                cs.setPetPosition(petPosition);
                cs.setSituation(situation);

                if(image==null){
                    Common.showToast(view.getContext(),"沒圖片請新增");
                    return;
                }

                else if (petName.length() <= 0 || petName ==null) {
                    Common.showToast(view.getContext(), "請輸入寵物姓名");
                    return;
                }
                else if(petColor.length()==0 ||petColor==null){
                    Common.showToast(view.getContext(),"請輸入寵物顏色");
                    return;
                }
                else if(petPosition.length()==0||petPosition==null){
                    Common.showToast(view.getContext(),"請輸入地址");
                    return;
                }
                else if(situation.length()==0||situation==null){
                    Common.showToast(view.getContext(),"請輸入寵物狀態");
                    return;
                }
                else if(cs.getPetAge()==null ){
                    Common.showToast(view.getContext(),"請輸入寵物年紀");
                    return;
                }
                else if(cs.getPetSex() == null){
                    Common.showToast(view.getContext(),"請填寵物性別");
                    return;
                }
                else if(cs.getPetSize() == null){
                    Common.showToast(view.getContext(),"請填寵物大小");
                    return;
                }

                else if(cs.getPetIc()==null ){
                    Common.showToast(view.getContext(),"請選擇有沒有晶片");
                    return;
                }
                else if(cs.getTNR()==null ){
                    Common.showToast(view.getContext(),"請選擇有沒有結紮");
                    return;
                }
                List<Address> addressList;
                Double petLatitude =0.0;
                Double petLongitude =0.0;
                try {
                    addressList = new Geocoder(view.getContext()).getFromLocationName(petPosition, 1);
                    petLatitude = addressList.get(0).getLatitude();
                    petLongitude = addressList.get(0).getLongitude();
                    cs.setPetLatitude(petLatitude);
                    cs.setPetLongitude(petLongitude);
                }catch (IOException e){
                    Log.e(TAG,e.toString());
                }

                insert(cs);
                finish();
            }

        });

    }

    private void findViews() {
        btFinishInsert = (Button)findViewById(R.id.btFinishInsert);
        bttp =(Button)findViewById(R.id.bttp);
        btpic =(Button)findViewById(R.id.btpic);
        ivp = (ImageView)findViewById(R.id.ivp);
        edpetname =(EditText)findViewById(R.id.edpetname);
        edpetcloor = (EditText)findViewById(R.id.edpetcolor);
        edposition = (EditText)findViewById(R.id. edposition);
        edsituation = (EditText)findViewById(R.id.edsituation);
        rdage = (RadioGroup) findViewById(R.id.rdage);
        rdsex = (RadioGroup)findViewById(R.id.rdsex);
        rdsize = (RadioGroup)findViewById(R.id.rdsize);
        rdpetic = (RadioGroup)findViewById(R.id.rdpetic);
        rdtnr = (RadioGroup)findViewById(R.id.rdtnr);

    }
    private boolean isIntentAvailable(Context context , Intent intent){
        PackageManager packageManager =context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,PackageManager.MATCH_DEFAULT_ONLY);
        return list.size()>0;
    }    @Override
    public void  onActivityResult(int requestCode,int resultCode ,Intent intent){
        super.onActivityResult(requestCode,resultCode,intent);
        if(resultCode == RESULT_OK){
            switch (requestCode){
                case REQUEST_TAKE_PICTURE:
                    Bitmap picture = BitmapFactory.decodeFile(file.getPath());
                    ivp.setImageBitmap(picture);
                    ByteArrayOutputStream out1 = new ByteArrayOutputStream();
                    picture.compress(Bitmap.CompressFormat.JPEG,100,out1);
                    image = out1.toByteArray();
                    break;
                case REQUEST_PICK_IMAGE:
                    Uri uri = intent.getData();
                    String[] columns ={MediaStore.Images.Media.DATA};
                    Cursor cursor =this.getContentResolver().query(uri,columns,null,null,null);
                    if(cursor!=null && cursor.moveToFirst()){
                        String imagePath = cursor.getString(0);
                        cursor.close();
                        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
                        ivp.setImageBitmap(bitmap);
                        ByteArrayOutputStream out2 = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG,100,out2);
                        image = out2.toByteArray();
                    }
                    break;
            }
        }
    }

    private void insert(Case cs){
        SharedPreferences pref = this.getSharedPreferences("UserData" ,MODE_PRIVATE);//抓偏好設定黨
        Integer memNo = pref.getInt("memNo", 0);
        if (Common.networkConnected(this)) {
            String url = Common.URL ;
            cs.setMemNo(memNo);

            String petFilm = Base64.encodeToString(image, Base64.DEFAULT);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("param", "spotInsert");
            jsonObject.addProperty("petInformationVO", new Gson().toJson(cs));
            jsonObject.addProperty("petFilm", petFilm);
            int count = 0;
            try {
                String result = new MyTask(url, jsonObject.toString()).execute().get();
                count = Integer.valueOf(result);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (count == 0) {
                Common.showToast(this, R.string.msg_InsertFail);
            } else {
                Common.showToast(this, R.string.msg_InsertSuccess);
            }
        } else {
            Common.showToast(this, R.string.msg_NoNetwork);
        }

    }


}
