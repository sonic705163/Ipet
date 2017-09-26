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

import idv.randy.me.LoginFragment;
import idv.randy.me.MeFragment;
import idv.randy.petwall.PetWallFragmentS;
import idv.randy.petwall.PwDetailActivity;
import idv.randy.petwall.PwEnterFragment;
import idv.randy.zNouse.ShopFragment;

public class MainActivity extends AppCompatActivity implements ShopFragment.OnFragmentInteractionListener, PetWallFragmentS.OnFragmentInteractionListener, MeFragment.MeFragmentListener, LoginFragment.LoginFragmentListener, PwEnterFragment.PwEnterFragmentListener {
    private BottomNavigationView bnv;
    private static final String TAG = "MainActivity";
    private FragmentManager fragmentManager = getSupportFragmentManager();
    public Fragment currentFragment;
    public MeFragment mMeFragment = MeFragment.newInstance("", "");
    public LoginFragment mLoginFragment = new LoginFragment();
    public PwEnterFragment mPwEnterFragment = new PwEnterFragment();
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
//                            Intent intent2 = new Intent(MainActivity.this, ApdotionActivity.class);
//                            startActivity(intent2);
                            break;
                        case R.id.shop:

                            break;
                        case R.id.petWall:
                            switchFragment(mPwEnterFragment).commit();
//                            Intent intent = new Intent(MainActivity.this, PwDetailActivity.class);
//                            startActivity(intent);
//                            PwDetailActivity.start(MainActivity.this,10000);
                            break;
                        case R.id.me:
                            if (loginStatus) {
                                switchFragment(mMeFragment).commit();
                            } else {
                                switchFragment(mLoginFragment).commit();
                            }
                            break;

                        default:
//                            fm = ShopFragment.newInstance("", "");
//                            fragmentManager.beginTransaction().replace(R.id.forMainFragment, fm, fm.getClass().getSimpleName()).commit();
//                            break;
                    }
                    return true;
                }
        );
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
    }

    @Override
    public void logOut() {
        switchFragment(mLoginFragment).commit();
    }

    public FragmentTransaction switchFragment(Fragment targetFragment) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction();
        if (!targetFragment.isAdded()) {
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


    @Override
    public void logIn() {
        mMeFragment = MeFragment.newInstance("", "");
        switchFragment(mMeFragment).commit();

    }

    @Override
    public void selectEnter() {

    }
}
