package idv.randy.zNouse;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.java.iPet.R;

public class AdoptActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.znouse_adopt_act);
        ActionBar acb = getSupportActionBar();
    }
}
