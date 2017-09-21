package com.example.java.iPet;

import android.content.Intent;
import android.content.SharedPreferences;
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

import idv.randy.me.Login;
import idv.randy.me.MeFragment;
import idv.randy.petwall.PetWallFragmentS;
import idv.randy.petwall.PetWallM;
import idv.randy.zNouse.ShopFragment;

public class MainActivity extends AppCompatActivity implements ShopFragment.OnFragmentInteractionListener, PetWallFragmentS.OnFragmentInteractionListener, MeFragment.OnFragmentInteractionListener {
    private BottomNavigationView bnv;
    private static final String TAG = "MainActivity";
    private FragmentManager fragmentManager = getSupportFragmentManager();
    boolean loginStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ActionBar acb = getSupportActionBar();
        bnv = (BottomNavigationView) findViewById(R.id.bnv);
        bnv.setOnNavigationItemSelectedListener(
                item -> {
                    Fragment fm = null;
                    SharedPreferences pref = getSharedPreferences("UserData", MODE_PRIVATE);
                    loginStatus = pref.getBoolean("login", false);
                    switch (item.getItemId()) {
                        case R.id.adopt:

                            break;
                        case R.id.shop:

                            break;
                        case R.id.petWall:
//                                fm = PetWallFragmentS.newInstance("", "");
//                                fragmentManager.beginTransaction().replace(R.id.forMainFragment, fm).commit();
                            Intent intent = new Intent(MainActivity.this, PetWallM.class);
                            startActivity(intent);
                            break;
                        case R.id.me:

                            if(loginStatus){
                                fragmentManager.beginTransaction().replace(R.id.forMainFragment, MeFragment.newInstance("", ""), fm.getClass().getSimpleName()).commit();
                            }else{
                                Intent loginIntent = new Intent(MainActivity.this, Login.class);
                                startActivity(loginIntent);
                            }
                            break;

                        default:
                            fm = ShopFragment.newInstance("", "");
                            fragmentManager.beginTransaction().replace(R.id.forMainFragment, fm, fm.getClass().getSimpleName()).commit();
                            break;
                    }
                    return false;
                }
        );
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }


}
