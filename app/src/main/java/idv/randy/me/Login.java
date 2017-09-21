package idv.randy.me;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.example.java.iPet.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.concurrent.ExecutionException;
import idv.randy.ut.AsObjTask;
import idv.randy.ut.ASAdapter;
import idv.randy.ut.Me;

public class Login extends AppCompatActivity {
    private static final String TAG = "Login";
    private EditText etID;
    private EditText etPD;
    Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        btLogin.setOnClickListener(v -> {
            String id = etID.getText().toString().trim();
            String pd = etPD.getText().toString().trim();
            if (isValid(id, pd)) {
                SharedPreferences.Editor editor = getSharedPreferences("UserData", MODE_PRIVATE).edit();
                editor.putString("id", id);
                editor.putString("pd", pd);
                editor.putBoolean("login", true);
                editor.apply();
                finish();
            } else {
                Toast.makeText(Me.gc(), "Account or Password is invalid", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findViews() {
        etID = (EditText) findViewById(R.id.etID);
        etPD = (EditText) findViewById(R.id.etPD);
        btLogin = (Button) findViewById(R.id.btLogin);
    }

    private boolean isValid(String id, String pd) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("pd", pd);
        boolean isValid = false;
        try {
            String s = new AsObjTask(new ASAdapter(), jsonObject, Me.gc()).execute(Me.MembersServlet).get();
            jsonObject = new Gson().fromJson(s, JsonObject.class);
            isValid = jsonObject.get("param").getAsBoolean();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return isValid;
    }
}

