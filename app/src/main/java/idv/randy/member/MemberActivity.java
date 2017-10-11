package idv.randy.member;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.java.iPet.R;
import com.google.gson.JsonObject;

import idv.randy.me.MembersVO;
import idv.randy.petwall.PetWallFragment;
import idv.randy.ut.AsyncAdapter;
import idv.randy.ut.AsyncImageTask;
import idv.randy.ut.AsyncObjTask;
import idv.randy.ut.ImageListener;
import idv.randy.ut.Me;

public class MemberActivity extends AppCompatActivity implements PetWallFragment.OnFragmentInteractionListener{
    private static final String TAG = "MemberActivity";
    private Toolbar toolbar;
    private TextView tvMemName;
    private TextView tvMemID;
    private TextView tvMemAddress;
    private TextView tvMemEmail;
    private TextView tvMemBirthday;
    private TextView tvMemCreateDate;
    private ImageView ivMemImg;
    private MembersVO membersVO;

    public static void start(Context context, int memNo) {
        Intent intent = new Intent(context, MemberActivity.class);
        intent.putExtra("memNo", memNo);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_activity_member);
        findViews();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        Intent intent = getIntent();
        int memNo = intent.getExtras().getInt("memNo");
        new AsyncImageTask(memNo, ivMemImg, R.drawable.person).execute(Me.MembersServlet);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "getVO");
        jsonObject.addProperty("memNo", memNo);
        AsyncAdapter asyncAdapeter = new AsyncAdapter() {
            @Override
            public void onFinish(String result) {
                super.onFinish(result);
                membersVO = MembersVO.decodeToVO(result);
                tvMemName.setText(membersVO.getMemName());
                tvMemID.setText(membersVO.getMenId());
                tvMemAddress.setText(membersVO.getMemAddress());
                tvMemEmail.setText(membersVO.getMemEmail());
                tvMemBirthday.setText(membersVO.getMemBirthday().toString().substring(5, 10));
                tvMemCreateDate.setText(membersVO.getMemCreateDate().toString().substring(0, 10));
            }
        };
        new AsyncObjTask(asyncAdapeter, jsonObject).execute(Me.MembersServlet);

        Bundle arguments = new Bundle();
        arguments.putInt("memNo", memNo);
        Fragment fragment = new MemberPwFragment();
//            Fragment fragment = new PetWallFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.forMemberPwFragment, fragment)
                .commit();
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        ivMemImg = (ImageView) findViewById(R.id.ivMemImg);
        tvMemName = (TextView) findViewById(R.id.tvMemName);
        tvMemID = (TextView) findViewById(R.id.tvMemId);
        tvMemAddress = (TextView) findViewById(R.id.tvMemAddress);
        tvMemEmail = (TextView) findViewById(R.id.tvMemEmail);
        tvMemBirthday = (TextView) findViewById(R.id.tvMemBirthday);
        tvMemCreateDate = (TextView) findViewById(R.id.tvMemCreateDate);
    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return super.onOptionsItemSelected(item);

    }

}
