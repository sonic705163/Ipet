package idv.randy.idv.randy.friends;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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

import idv.randy.petwall.PwDetailFragment;
import idv.randy.petwall.PwrVO;
import idv.randy.ut.AsyncAdapter;
import idv.randy.ut.AsyncImageTask;
import idv.randy.ut.AsyncObjTask;
import idv.randy.ut.Me;

public class FriendsActivity extends AppCompatActivity {
    private static final String TAG = "PwDetailActivity";
    private Bundle arguments;
    private int pwNo;
    public static int memNo;
    private EditText etPwrContent;
    private ImageView ivSend;
    private ImageView ivPwPicture;
    private LinearLayout llPwr;
    private FloatingActionButton btnFab;
    private FriendsFragment fragment = new FriendsFragment();
    private SharedPreferences pref;
    private boolean loginStatus;


    public static void start(Context context) {
        Intent intent = new Intent(context, FriendsActivity.class);
        context.startActivity(intent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        pref = getSharedPreferences("UserData", MODE_PRIVATE);
        loginStatus = pref.getBoolean("login", false);
        memNo = pref.getInt("memNo", 1);

        if (savedInstanceState == null) {
            arguments = new Bundle();
            arguments.putInt("memNo",
                    memNo);
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.forFriendsFragment, fragment)
                    .commit();
        }

    }

}