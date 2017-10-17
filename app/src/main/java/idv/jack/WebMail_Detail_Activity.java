package idv.jack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.java.iPet.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.sql.Timestamp;

import idv.randy.me.MembersVO;

public class WebMail_Detail_Activity extends AppCompatActivity {
    private static final String TAG = "WebMail_Detail_Activity";
    private WebmailVO wv;
    private TextView tvWhomemno,tvWebmailDateDetail,tvWebMailContentDetail;
    private MyTask sendtask;
    private MembersVO mbVO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webmail_detail);
        findView(wv);



    }

    private void findView(WebmailVO wv) {
        tvWhomemno = (TextView)findViewById(R.id.tvWhomemno);
        tvWebmailDateDetail = (TextView)findViewById(R.id.tvWebmailDateDetail);
        tvWebMailContentDetail = (TextView)findViewById(R.id.tvWebMailContentDetail);
    }

    @Override
    public void onStart (){
        super.onStart();
        wv = (WebmailVO) this.getIntent().getExtras().getSerializable("wv");

//        tvWhomemno.setText(String.valueOf(wv.getTomemNo()));
        getMbName(wv.getTomemNo());
        tvWhomemno.setText(mbVO.getMemName());
        tvWebmailDateDetail.setText(wv.getMailDate().toString().substring(0,10));
        tvWebMailContentDetail.setText(wv.getMailContext());
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
