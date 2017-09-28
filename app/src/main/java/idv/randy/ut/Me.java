package idv.randy.ut;

import android.app.Application;
import android.content.Context;
import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.example.java.iPet.R;

public class Me extends Application {
    private static Context context;
    private static String ip = "http://10.0.2.2:8081/ba103g1/";
//    private static String ip = "http://10.120.38.3:8081/BA103G1/";
    public static final String MembersServlet = ip + "MembersServlet";
    public static final String PetServlet = ip + "PetServlet";
    public static final String PwrServlet = ip + "PwrServlet";
    public static Fragment current;
    public static Context gc() {
        return context;
    }

    public static FragmentTransaction switchFragment(FragmentActivity context, int resid, Fragment target) {
        FragmentTransaction transaction = context.getSupportFragmentManager()
                .beginTransaction();
        if (!target.isAdded()) {
            if (current != null) {
                transaction.hide(current);
            }
            transaction.add(resid, target, target.getClass().getName());
        } else {
            transaction
                    .hide(current)
                    .show(target);
        }
        current = target;
        return transaction;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }
}
