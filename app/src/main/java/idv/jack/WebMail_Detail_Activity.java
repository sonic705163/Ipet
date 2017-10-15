package idv.jack;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.java.iPet.R;

import java.sql.Timestamp;

public class WebMail_Detail_Activity extends AppCompatActivity {
    private static final String TAG = "WebMail_Detail_Activity";
    private WebmailVO wv;
    private TextView tvWhomemno,tvWebmailDateDetail,tvWebMailContentDetail;
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

        tvWhomemno.setText(String.valueOf(wv.getTomemNo()));
        tvWebmailDateDetail.setText(wv.getMailDate().toString().substring(0,10));
        tvWebMailContentDetail.setText(wv.getMailContext());
    }
}
