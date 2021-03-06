package idv.jack;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.java.iPet.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

import idv.randy.me.MembersVO;

public class WebmailSend extends AppCompatActivity {
    private final static String TAG = "WebmailSend";
    private TextView tvgetmemno;
    private EditText etMailContent;
    private Button btnInsertMessage;
    private WebmailVO webmailvo = new WebmailVO();
    int memno;
    private MyTask sendtask;
    private MembersVO mbVO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webmail_send);
        Bundle bundle = getIntent().getExtras();
        memno = (Integer) bundle.getSerializable("memno");
        getMbName(memno);
        webmailvo.setGetmemNo(memno);
        findviews();
        btnInsertMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MailContent = etMailContent.getText().toString().trim();
                webmailvo.setMailContext(MailContent);
                getmemno(webmailvo);
                finish();
            }

        });

    }

    private void findviews() {
        tvgetmemno = (TextView) findViewById(R.id.tvgetmemno);
        etMailContent = (EditText) findViewById(R.id.etMailContent);
        btnInsertMessage =(Button)findViewById(R.id.btnInsertMessage);
    }
    @Override
    public void onStart() {
        super.onStart();
       tvgetmemno.setText(mbVO.getMemName());
    }

    private void getmemno(WebmailVO webmailvo) {
        SharedPreferences pref = this.getSharedPreferences("UserData" ,MODE_PRIVATE);//抓偏好設定黨
        Integer memNo = pref.getInt("memNo", 0);
        if (Common.networkConnected(this)) {
            String url = Common.URL1 ;
            webmailvo.setTomemNo(memNo);
            Gson gson = new Gson();
            String msgStr = gson.toJson(webmailvo);
            JsonObject jsonObject =new JsonObject();
            jsonObject.addProperty("action", "insert");
            jsonObject.addProperty("webmailvo", msgStr);

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

    private void getMbName(Integer memNo) {
        if (Common.networkConnected(this)) {
            String url = null;
            url = Common.URL2;
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getMbInfo");
            jsonObject.addProperty("memNo", memNo);
            String jsonOut = jsonObject.toString();
            sendtask = new MyTask(url, jsonOut);
            try {
                String jsonIn = sendtask.execute().get();
                Log.d(TAG, jsonIn);
                Type listType = new TypeToken<MembersVO>(){}.getType();
                mbVO = gson.fromJson(jsonIn, listType);

            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }


        } else {
            Common.showToast(this, "錯誤");
        }
    }

}
