package com.example.java.iPet;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import idv.jack.ApdotionActivity;
import idv.randy.me.MeFragment;
import idv.randy.petwall.PetWallActivityM;
import idv.randy.petwall.PetWallFragmentS;
import idv.randy.zNouse.ShopFragment;

public class MainActivity extends AppCompatActivity implements ShopFragment.OnFragmentInteractionListener, PetWallFragmentS.OnFragmentInteractionListener, MeFragment.OnFragmentInteractionListener {
    private BottomNavigationView bnv;
    private static final String TAG = "MainActivity";
    private FragmentManager fragmentManager = getSupportFragmentManager();
    private Fragment currentFragment;
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
                            Intent intent2 = new Intent(MainActivity.this, ApdotionActivity.class);
                            startActivity(intent2);
                            break;
                        case R.id.shop:

                            break;
                        case R.id.petWall:
//                                fm = PetWallFragmentS.newInstance("", "");
//                                fragmentManager.beginTransaction().replace(R.id.forMainFragment, fm).commit();
                            Intent intent = new Intent(MainActivity.this, PetWallActivityM.class);
                            startActivity(intent);

                            break;
                        case R.id.me:
                            Fragment meFragment = fragmentManager.findFragmentByTag("MeFragment");
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            if (meFragment == null) {
                                fragmentManager.beginTransaction().replace(R.id.forMainFragment, MeFragment.newInstance("", "").newInstance("", ""), "MeFragment").commit();
                            } else {
                                transaction.replace(R.id.forMainFragment, meFragment).commit();
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

    private FragmentTransaction switchFragment(Fragment targetFragment) {

        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
            //第一次使用switchFragment()時currentFragment為null，所以要判斷一下
            if (currentFragment != null) {
                transaction.hide(currentFragment);
            }
            transaction.add(R.id.forMainFragment, targetFragment, targetFragment.getClass().getName());

        } else {
            transaction
                    .hide(currentFragment)
                    .show(targetFragment);


        }
        currentFragment = targetFragment;
        return transaction;
    }


}
