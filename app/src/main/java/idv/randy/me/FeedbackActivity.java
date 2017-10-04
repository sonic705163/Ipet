package idv.randy.me;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.example.java.iPet.R;
import com.google.gson.JsonObject;
import idv.randy.ut.AsyncObjTask;
import idv.randy.ut.Me;

public class FeedbackActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "FeedbackActivity";
    EditText etFeedback;
    TextView tvSend;
    TextView tvCancel;
    String mFeedback;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_activity_feedback);
        findViews();
        setSupportActionBar(toolbar);
        tvCancel.setOnClickListener(this);
        tvSend.setOnClickListener(this);
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvSend = (TextView) findViewById(R.id.tvSend);
        etFeedback = (EditText) findViewById(R.id.etFeedback);
        tvCancel = (TextView) findViewById(R.id.tvCancel);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvCancel:
                finish();
                break;
            case R.id.tvSend:
                SharedPreferences pref = getSharedPreferences("UserData", MODE_PRIVATE);
                mFeedback = etFeedback.getText().toString().trim();
                if (mFeedback.equals("")) {
                    Toast.makeText(Me.gc(), "請輸入意見內容", Toast.LENGTH_SHORT).show();
                    return;
                }
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "feedback");
                jsonObject.addProperty("mFeedback", etFeedback.getText().toString());
                jsonObject.addProperty("memNo", pref.getInt("memNo", 0));
                new AsyncObjTask(null, jsonObject).execute(Me.MembersServlet);
                finish();
                break;
            default:
                break;

        }
    }
}