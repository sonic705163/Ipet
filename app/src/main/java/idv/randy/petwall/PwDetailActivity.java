package idv.randy.petwall;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.java.iPet.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.sql.Date;
import java.util.concurrent.ExecutionException;

import idv.randy.ut.AsyncImageTask;
import idv.randy.ut.AsyncObjTask;
import idv.randy.ut.Me;


public class PwDetailActivity extends AppCompatActivity implements PwDetailFragment.OnListFragmentInteractionListener, View.OnClickListener {
    private static final String TAG = "PwDetailActivity";
    private Bundle arguments;
    public static Fragment current;
    private int pwNo;
    private int memNo;

    private EditText etPwrContent;
    private ImageView ivSend;
    private ImageView ivPwPicture;
    private LinearLayout llPwr;
    private FloatingActionButton btnFab;
    private PwDetailFragment fragment = new PwDetailFragment();
    private InputMethodManager imm;
    private SharedPreferences pref;
    private boolean loginStatus;

    public static void start(Context context, int pwNo, int memNo) {
        Intent intent = new Intent(context, PwDetailActivity.class);
        intent.putExtra("pwNo", pwNo);
        intent.putExtra("memNo", memNo);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        setContentView(R.layout.r_activity_pw_detail);
        findViews();
        btnFab.setVisibility(View.VISIBLE);
        llPwr.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        pref = getSharedPreferences("UserData", MODE_PRIVATE);
        loginStatus = pref.getBoolean("login", false);
        btnFab.setOnClickListener(this);

        Intent intent = getIntent();
        pwNo = intent.getExtras().getInt("pwNo");
        memNo = intent.getExtras().getInt("memNo");

        new AsyncImageTask(pwNo, ivPwPicture, R.drawable.aa1418273).execute(Me.PetServlet);

        if (savedInstanceState == null) {
            arguments = new Bundle();
            arguments.putInt("pwNo",
                    pwNo);
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
        ivSend.setOnClickListener(onClickListener);


    }

    private void findViews() {
        etPwrContent = (EditText) findViewById(R.id.etPwrContent);
        llPwr = (LinearLayout) findViewById(R.id.llPwr);
        btnFab = (FloatingActionButton) findViewById(R.id.btnFab);
        ivSend = (ImageView) findViewById(R.id.ivSend);
        ivPwPicture = (ImageView) findViewById(R.id.ivPwPicture);
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String content = etPwrContent.getText().toString();
            if (content.trim().equals("")) {
                hideKeyPad();
                llPwr.setVisibility(View.GONE);
                btnFab.setVisibility(View.VISIBLE);
                return;
            }
            if (!loginStatus) {
                Toast.makeText(Me.gc(), "登入後可留言", Toast.LENGTH_SHORT).show();
                return;
            }
            try {
                new AsyncObjTask(null, getJsonObject(content)).execute(Me.PwrServlet).get();
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
            etPwrContent.setText("");
            hideKeyPad();
            llPwr.setVisibility(View.GONE);
            btnFab.setVisibility(View.VISIBLE);
        }
    };

    private JsonObject getJsonObject(String content) {
        PwrVO pwrVO = new PwrVO();
        pwrVO.setPwrdate(new Date(System.currentTimeMillis()));
        pwrVO.setPwrcontent(content);
        pwrVO.setMemno(pref.getInt("memNo", 0));
        pwrVO.setPwno(pwNo);
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "insertPwr");
        jsonObject.addProperty("pwrVO", gson.toJson(pwrVO));
        return jsonObject;
    }

    @Override
    public void onListFragmentInteraction(PwrVO item) {

    }

    @Override
    public void refresh() {
        arguments = new Bundle();
        arguments.putInt("pwNo",
                pwNo);
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.item_detail_container, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        SharedPreferences pref = getSharedPreferences("UserData", MODE_PRIVATE);
        if (loginStatus && pref.getInt("memNo", 0) == memNo) {
            getMenuInflater().inflate(R.menu.menu_pw_detail, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        switch (item.getItemId()) {
            case R.id.delete:
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "deletePw");
                jsonObject.addProperty("pwNo", pwNo);
                new AsyncObjTask(null, jsonObject).execute(Me.PetServlet);
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    void hideKeyPad() {
        etPwrContent.clearFocus();
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(this
                                .getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFab:
                btnFab.setVisibility(View.GONE);
                llPwr.setVisibility(View.VISIBLE);
                showKeyboard();
                break;
        }
    }

    private void showKeyboard() {
        etPwrContent.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.showSoftInput(etPwrContent, 0);
    }

}
