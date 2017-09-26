package idv.randy.petwall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.example.java.iPet.R;


public class PwDetailActivity extends AppCompatActivity implements PwDetailFragment.OnListFragmentInteractionListener {
    private static final String TAG = "PwDetailActivity";

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
        }

        Intent intent = getIntent();
        int pwNo = intent.getExtras().getInt("pwNo");

        if (savedInstanceState == null) {
            Bundle arguments = new Bundle();
            arguments.putInt("pwNo",
                    pwNo);
            PwDetailFragment fragment = new PwDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.item_detail_container, fragment)
                    .commit();
        }
    }

    @Override
    public void onListFragmentInteraction(PwrVO item) {

    }
}
