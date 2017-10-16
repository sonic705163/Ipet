package idv.jack;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.java.iPet.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ReplyMail extends AppCompatActivity {
    private final static String TAG = "ReplyMail";
    private WebmailVO wv;
    int getmemno;
    private Button btnReplyMessage;
    private WebmailVO webmailvo = new WebmailVO();
    private EditText etMailContents;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply_mail);
        findviews();
        btnReplyMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MailContent = etMailContents.getText().toString().trim();
                webmailvo.setMailContext(MailContent);
                getmemno(webmailvo);
                finish();
            }
        });
    }

    private void findviews() {
        etMailContents = (EditText) findViewById(R.id.etMailContents);
        btnReplyMessage =(Button)findViewById(R.id.btnReplyMessage);
    }

    @Override
    public void onStart () {
        super.onStart();
        Bundle bundle = getIntent().getExtras();
        getmemno = (Integer) bundle.getSerializable("wv");
        Log.e("aa",getmemno +"");
    }
    private void getmemno(WebmailVO webmailvo) {
        SharedPreferences pref = this.getSharedPreferences("UserData" ,MODE_PRIVATE);//抓偏好設定黨
        Integer memNo = pref.getInt("memNo", 0);
        if (Common.networkConnected(this)) {
            String url = Common.URL1 ;
            webmailvo.setTomemNo(memNo);
            webmailvo.setGetmemNo(getmemno);
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

}
