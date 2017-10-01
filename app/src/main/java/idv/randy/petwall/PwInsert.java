package idv.randy.petwall;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java.iPet.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.sql.Date;
import java.util.List;

import idv.randy.ut.AsyncObjTask;
import idv.randy.ut.Me;

import static android.os.Environment.getExternalStoragePublicDirectory;

public class PwInsert extends AppCompatActivity {
    ImageView ivCamera;
    ImageView ivPhotoLib;
    ImageView ivPwPicture;
    TextView tvSend;
    private byte[] image;
    File file;
    private static final int REQUESTCODE_SHOT = 1;
    private static final int REQUESTCODE_PHOTO_LIB = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pw_insert);
        findViews();
        ivCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                file = new File(file, "picture.jpg");
                Uri uri = FileProvider.getUriForFile(Me.gc(), getPackageName() + ".provider", file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

                if (isIntentAvailable(Me.gc(), intent)) {
                    startActivityForResult(intent, REQUESTCODE_SHOT);
                } else {
                    Toast.makeText(Me.gc(), "No Camera Apps Found",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("UserData", MODE_PRIVATE);
                if (!pref.getBoolean("login", false)) {
                    return;
                }

                PwVO pwVO = new PwVO();
                pwVO.setPwDate(new Date(System.currentTimeMillis()));
                pwVO.setPwContent("我我我");
                pwVO.setPwPraise("0");
                pwVO.setMemno(pref.getInt("memNo", 0));


                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                String imageBase64 = Base64.encodeToString(image, Base64.DEFAULT);
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "insertPw");
                jsonObject.addProperty("pwVO", gson.toJson(pwVO));
                jsonObject.addProperty("image", imageBase64);
                new AsyncObjTask(null, jsonObject);
            }
        });

    }

    private void findViews() {
        ivCamera = (ImageView) findViewById(R.id.ivCamera);
        ivPhotoLib = (ImageView) findViewById(R.id.ivPhotoLib);
        ivPwPicture = (ImageView) findViewById(R.id.ivPwPicture);
        tvSend = (TextView) findViewById(R.id.tvSend);
    }

    private boolean isIntentAvailable(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (resultCode) {
                case REQUESTCODE_SHOT:
                    Bitmap bitmap = BitmapFactory.decodeFile(file.getPath());
                    ivPwPicture.setImageBitmap(bitmap);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                    image = byteArrayOutputStream.toByteArray();


            }


        }
    }
}
