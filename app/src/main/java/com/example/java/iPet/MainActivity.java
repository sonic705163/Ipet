package com.example.java.iPet;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import idv.randy.me.MeFragment;
import idv.randy.petwall.PetWallFragmentS;
import idv.randy.petwall.PetWallM;
import idv.randy.zNouse.ShopFragment;

public class MainActivity extends AppCompatActivity implements ShopFragment.OnFragmentInteractionListener, PetWallFragmentS.OnFragmentInteractionListener, MeFragment.OnFragmentInteractionListener {
    private BottomNavigationView bnv;
    private static final String TAG = "MainActivity";
    private FragmentManager fmgr = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
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
                            case R.id.adopt:
                                break;
                            case R.id.shop:
                                break;
                            case R.id.petWall:
//                                fm = PetWallFragmentS.newInstance("", "");
//                                fmgr.beginTransaction().replace(R.id.forMainFragment, fm).commit();
                                Intent intent = new Intent(MainActivity.this, PetWallM.class);
                                startActivity(intent);
                                break;
                            case R.id.me:
                                fm = MeFragment.newInstance("", "");
                                fmgr.beginTransaction().replace(R.id.forMainFragment, fm).commit();
                                break;
                            default:
                                fm = ShopFragment.newInstance("", "");
                                fmgr.beginTransaction().replace(R.id.forMainFragment, fm).commit();
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
