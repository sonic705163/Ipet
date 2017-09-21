package idv.randy.me;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.java.iPet.R;
import com.google.gson.JsonObject;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";
    private EditText etID;
    private EditText etPD;
    Button btLogin;
    JsonObject jsonObject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_activity_login);
//        findViews();
//        btLogin.setOnClickListener(v -> {
//            String id = etID.getText().toString().trim();
//            String pd = etPD.getText().toString().trim();
//            if (isValid(id, pd)) {
//                int no = jsonObject.get("id").getAsInt();
//                SharedPreferences.Editor editor = getSharedPreferences("UserData", MODE_PRIVATE).edit();
//                editor.putString("id", id);
//                editor.putString("pd", pd);
//                editor.putBoolean("login", true);
//                editor.apply();
//                finish();
//            } else {
//                Toast.makeText(Me.gc(), "Account or Password is invalid", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

//    private void findViews() {
//        etID = (EditText) findViewById(R.id.etID);
//        etPD = (EditText) findViewById(R.id.etPD);
//        btLogin = (Button) findViewById(R.id.btLogin);
//    }
//
//    private boolean isValid(String id, String pd) {
//        jsonObject = new JsonObject();
//        jsonObject.addProperty("id", id);
//        jsonObject.addProperty("pd", pd);
//        boolean isValid = false;
//        try {
//            String s = new AsyncObjTask(new AsyncAdapter(), jsonObject, Me.gc()).execute(Me.MembersServlet).get();
//            jsonObject = new Gson().fromJson(s, JsonObject.class);
//            isValid = jsonObject.get("param").getAsBoolean();
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }
//        return isValid;
//    }
}

