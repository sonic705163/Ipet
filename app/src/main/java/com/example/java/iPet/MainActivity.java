package com.example.java.iPet;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import idv.randy.me.LoginFragment;
import idv.randy.me.MeFragment;
import idv.randy.member.MemberActivity;
import idv.randy.petwall.PetWallFragment;
import idv.randy.petwall.PwEnterFragment;
import idv.randy.ut.Me;

public class MainActivity extends AppCompatActivity implements PetWallFragment.OnFragmentInteractionListener, MeFragment.MeFragmentListener, LoginFragment.LoginFragmentListener, PwEnterFragment.PwEnterFragmentListener {
    private static final String TAG = "MainActivity";
    public MeFragment mMeFragment = MeFragment.newInstance("", "");
    public LoginFragment mLoginFragment = new LoginFragment();
    public PwEnterFragment mPwEnterFragment = new PwEnterFragment();
    boolean loginStatus;
    int mainFragment = R.id.forMainFragment;
    private BottomNavigationView bnv;
    private FragmentManager fragmentManager = getSupportFragmentManager();

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
                            MemberActivity.start(Me.gc(), 100000);
                            break;
                        case R.id.shop:

                            break;
                        case R.id.petWall:
                            Me.switchFragment(this, mainFragment, mPwEnterFragment).commit();
                            break;
                        case R.id.me:
                            if (loginStatus) {
                                Me.switchFragment(this, mainFragment, mMeFragment).commit();
                            } else {
                                Me.switchFragment(this, mainFragment, mLoginFragment).commit();
                            }
                            break;

                        default:
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
        Me.switchFragment(this, mainFragment, mLoginFragment).commit();
    }

    @Override
    public void login() {
        mMeFragment = MeFragment.newInstance("", "");
        Me.switchFragment(this, mainFragment, mMeFragment).commit();
    }

    @Override
    public void selectEnter() {
    }
}
