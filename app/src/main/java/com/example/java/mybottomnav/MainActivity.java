package com.example.java.mybottomnav;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements ShopFragment.OnFragmentInteractionListener, WallFragment.OnFragmentInteractionListener, MeFragment.OnFragmentInteractionListener {
    private BottomNavigationView bnv;

    private FragmentManager fmgr = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ActionBar acb = getSupportActionBar();
        bnv = (BottomNavigationView) findViewById(R.id.bnv);
        bnv.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Fragment fm = null;
                        switch (item.getItemId()) {
                            case R.id.petWall:
                                fm = WallFragment.newInstance("", "");
                                fmgr.beginTransaction().replace(R.id.frameLayoutForFragment, fm).commit();
                                break;
                            case R.id.shop:
                                fm = ShopFragment.newInstance("", "");
                                fmgr.beginTransaction().replace(R.id.frameLayoutForFragment, fm).commit();
                                break;
                            case R.id.me:
                                fm = MeFragment.newInstance("", "");
                                fmgr.beginTransaction().replace(R.id.frameLayoutForFragment, fm).commit();
                                break;
                            case R.id.adopt:
                                Intent intent = new Intent(MainActivity.this, AdoptActivity.class);
                                startActivity(intent);
                                break;
                            default:
                                fm = ShopFragment.newInstance("", "");
                                fmgr.beginTransaction().replace(R.id.frameLayoutForFragment, fm).commit();
                                break;
                        }
                        return false;
                    }
                }
        );
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
