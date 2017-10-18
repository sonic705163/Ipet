package idv.randy.member;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.java.iPet.R;
import com.google.gson.JsonObject;

import java.util.HashSet;
import java.util.List;

import idv.randy.idv.randy.friends.FriendsVO;
import idv.randy.me.MembersVO;
import idv.randy.petwall.PetWallFragment;
import idv.randy.ut.AsyncAdapter;
import idv.randy.ut.AsyncImageTask;
import idv.randy.ut.AsyncObjTask;
import idv.randy.ut.Me;

public class MemberActivity extends AppCompatActivity implements PetWallFragment.OnFragmentInteractionListener {
    private static final String TAG = "MemberActivity";
    private Toolbar toolbar;
    private TextView tvMemName;
    private TextView tvMemID;
    private TextView tvMemAddress;
    private TextView tvMemEmail;
    private TextView tvMemBirthday;
    private TextView tvMemCreateDate;
    private ImageView ivMemImg;
    private Button btnAddFriend;
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
        SharedPreferences pref = getSharedPreferences("UserData", MODE_PRIVATE);
        int myMemNo = pref.getInt("memNo", 1);
        Intent intent = getIntent();
        int memNo = intent.getExtras().getInt("memNo");
        JsonObject jsonObject1 = new JsonObject();
        jsonObject1.addProperty("action", "checkStatus");
        jsonObject1.addProperty("myMemNo", myMemNo);
        new AsyncObjTask(new AsyncAdapter() {
            @Override
            public void onFinish(String result) {
                super.onFinish(result);
                List<FriendsVO> friendsVOs = FriendsVO.decodeToList(result);
                HashSet<Integer> myFriends = new HashSet<>();
                for (FriendsVO f : friendsVOs) {
                    myFriends.add(f.getMemNo1());
                    myFriends.add(f.getMemNo2());
                }
                if (myFriends.contains(memNo)) {
                    btnAddFriend.setVisibility(View.GONE);
                }
            }
        }, jsonObject1).execute(Me.addFriends);

        btnAddFriend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty("action", "addFriend");
                jsonObject2.addProperty("memNo", memNo);
                jsonObject2.addProperty("myMemNo", myMemNo);
                new AsyncObjTask(new AsyncAdapter(), jsonObject2).execute(Me.addFriends);
                btnAddFriend.setVisibility(View.GONE);
            }
        });
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
                if (membersVO.getMemBirthday() != null) {
                    tvMemBirthday.setText(membersVO.getMemBirthday().toString().substring(5, 10));
                }
                if (membersVO.getMemCreateDate() != null) {
                    tvMemCreateDate.setText(membersVO.getMemCreateDate().toString().substring(0, 10));
                }

            }
        };
        new AsyncObjTask(asyncAdapeter, jsonObject).execute(Me.MembersServlet);

        Bundle arguments = new Bundle();
        arguments.putInt("memNo", memNo);
        Fragment fragment = new MemberPwFragment();
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
        btnAddFriend = (Button) findViewById(R.id.btnAddFriend);
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
