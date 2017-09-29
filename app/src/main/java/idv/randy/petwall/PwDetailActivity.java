package idv.randy.petwall;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.java.iPet.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.sql.Date;
import java.util.concurrent.ExecutionException;

import idv.randy.ut.AsyncObjTask;
import idv.randy.ut.Me;


public class PwDetailActivity extends AppCompatActivity implements PwDetailFragment.OnListFragmentInteractionListener {
    private static final String TAG = "PwDetailActivity";
    Bundle arguments;
    public static Fragment current;
    PwDetailFragment fragment = new PwDetailFragment();

    public static void start(Context context, int pwNo) {
        Intent intent = new Intent(context, PwDetailActivity.class);
        intent.putExtra("pwNo", pwNo);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.r_activity_pw_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }

        EditText etPwrContent = (EditText) findViewById(R.id.etPwrContent);
        Intent intent = getIntent();
        int pwNo = intent.getExtras().getInt("pwNo");

        if (savedInstanceState == null) {
            arguments = new Bundle();
            arguments.putInt("pwNo",
                    pwNo);
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
        ImageView ivSend = (ImageView) findViewById(R.id.ivSend);
        ivSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = etPwrContent.getText().toString();
                if (!content.trim().equals("")) {
                    SharedPreferences pref = getSharedPreferences("UserData", MODE_PRIVATE);
                    boolean loginStatus = pref.getBoolean("login", false);
                    if (loginStatus) {
                        PwrVO pwrVO = new PwrVO();
                        pwrVO.setPwrdate(new Date(System.currentTimeMillis()));
                        pwrVO.setPwrcontent(content);
                        pwrVO.setMemno(pref.getInt("memNo", 0));
                        pwrVO.setPwno(pwNo);
                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("action", "insertPwr");
                        jsonObject.addProperty("pwrVO", gson.toJson(pwrVO));
                        try {
                            new AsyncObjTask(null, jsonObject).execute(Me.PwrServlet).get();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        }
                        PwDetailFragment fragment = new PwDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Toast.makeText(Me.gc(), "登入後可留言", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }

    @Override
    public void onListFragmentInteraction(PwrVO item) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }
}
