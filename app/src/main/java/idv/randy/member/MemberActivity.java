package idv.randy.member;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.java.iPet.R;

import idv.randy.member.dummy.DummyContent;
import idv.randy.petwall.PetWallFragment;

public class MemberActivity extends AppCompatActivity implements MemberPwFragment.OnListFragmentInteractionListener, PetWallFragment.OnFragmentInteractionListener {
    private static final String TAG = "MemberActivity";
    private int memNo;

    public static void start(Context context, int memNo) {
        Intent intent = new Intent(context, MemberActivity.class);
        intent.putExtra("memNo", memNo);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_activity_member);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("");
        }
        Intent intent = getIntent();
        int memNo = intent.getExtras().getInt("memNo");
        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putInt("memNo",
                    memNo);
            Fragment fragment = new PetWallFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.forMemberPwFragment, fragment)
                    .commit();
        }
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
