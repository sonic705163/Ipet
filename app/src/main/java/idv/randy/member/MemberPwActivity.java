package idv.randy.member;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.java.iPet.R;

public class MemberPwActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.r_activity_member_pw);
        Intent intent = getIntent();
        int memNo = intent.getExtras().getInt("memNo");
        Bundle arguments = new Bundle();
        arguments.putInt("memNo", memNo);
        Fragment fragment = new MemberPwFragment();
        fragment.setArguments(arguments);
        getSupportFragmentManager().beginTransaction()
                .add(R.id.forMemberPwFragment, fragment)
                .commit();

    }
    public static void start(Context context, int memNo) {
        Intent intent = new Intent(context, MemberPwActivity.class);
        intent.putExtra("memNo", memNo);
        context.startActivity(intent);

    }
}
