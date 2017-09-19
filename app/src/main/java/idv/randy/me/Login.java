package idv.randy.me;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.java.iPet.R;

public class Login extends AppCompatActivity {
    private static final String TAG = "Login";
    private EditText etID;
    private EditText etPD;
    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences pref = getSharedPreferences("preference", MODE_PRIVATE);
        boolean needLogin = pref.getBoolean("login", false);
        if (needLogin) {
            String name = pref.getString("user", "");
            String password = pref.getString("password", "");
            if (isUserValid(name, password)) {
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText("w")
                showMessage(R.string.msg_InvalidUserOrPassword);
            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findviews();
    }

    private void findviews() {
        etID = (EditText) findViewById(R.id.etID);
        etPD = (EditText) findViewById(R.id.etPD);
        Button btLogin = (Button) findViewById(R.id.btLogin);


    }
}
