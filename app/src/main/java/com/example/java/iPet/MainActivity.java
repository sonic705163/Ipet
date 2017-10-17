package com.example.java.iPet;

import android.content.SharedPreferences;
import android.net.Uri;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import idv.jack.ApenterFragment;
import idv.randy.me.LoginFragment;
import idv.randy.me.MeFragment;
import idv.randy.me.RegisterFragment;
import idv.randy.petwall.PetWallFragment;
import idv.randy.petwall.PwEnterFragment;
import idv.randy.ut.Me;

public class MainActivity extends AppCompatActivity implements PetWallFragment.OnFragmentInteractionListener, MeFragment.MeFragmentListener, LoginFragment.LoginFragmentListener, PwEnterFragment.PwEnterFragmentListener, RegisterFragment.registerFragmentListener {
    private static final String TAG = "MainActivity";
    public MeFragment mMeFragment = MeFragment.newInstance("", "");
    public LoginFragment mLoginFragment = new LoginFragment();
    public PwEnterFragment mPwEnterFragment = new PwEnterFragment();
    public ApenterFragment mApenterFragment = new ApenterFragment();
    RegisterFragment registerFragment = new RegisterFragment();
    boolean loginStatus;
    int mainFragment = R.id.forMainFragment;
    private BottomNavigationView bnv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ActionBar acb = getSupportActionBar();
        Me.switchFragment(this, mainFragment, mPwEnterFragment).commit();
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
                            Me.switchFragment(this, mainFragment, mApenterFragment).commit();
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
    public void cancel() {
        Me.switchFragment(this, mainFragment, mLoginFragment).commit();
    }

    @Override
    public void toRegister() {
        Me.switchFragment(this, mainFragment, registerFragment).commit();
    }

    @Override
    public void selectEnter() {
    }
}
