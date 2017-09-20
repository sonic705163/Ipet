package idv.randy.me;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.java.iPet.R;

import idv.randy.ut.Me;

public class Login extends AppCompatActivity {
    private static final String TAG = "Login";
    private EditText etID;
    private EditText etPD;
    Button btLogin;

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences pref = getSharedPreferences("iPetUserData", MODE_PRIVATE);
        boolean loginStatus = pref.getBoolean("login", false);
        if (loginStatus) {
            String name = pref.getString("id", "");
            String password = pref.getString("pd", "");

            if (isValid(name, password)) {
                setResult(RESULT_OK);
                finish();
            } else {
                Toast.makeText(Me.gc(), "Account or Password is invalid",Toast.LENGTH_SHORT).show();

            }
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findviews();
        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = etID.getText().toString();
                String pd = etPD.getText().toString();
                if(isValid(id, pd)){
                    SharedPreferences.Editor editor = getSharedPreferences("iPetUserData", MODE_PRIVATE).edit();
                    editor.putString("id", id);
                    editor.putString("pd", pd);
                    editor.putBoolean("login", true);
                    editor.apply();
                    finish();
                }
                else{
                    Toast.makeText(Me.gc(), "Account or Password is invalid",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void findviews() {
        etID = (EditText) findViewById(R.id.etID);
        etPD = (EditText) findViewById(R.id.etPD);
        btLogin = (Button) findViewById(R.id.btLogin);
    }
    private boolean isValid(String id, String pd){


        if (true){
            return true;
        }else{
            return false;
        }



    };
}
