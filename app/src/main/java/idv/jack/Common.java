package idv.jack;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

public class Common { // Android官方模擬器連結本機web server可以直接使用 http://10.0.2.2

    public final static String URL = "http://10.0.2.2:8081/ba103g1_Android/PetServletAndroid";
    public final static String URL1 = "http://10.0.2.2:8081/ba103g1_Android/WebMailServletAndroid";
    public final static String URL2 = "http://10.0.2.2:8081/ba103g1_Android/MembersServlet";

//    public final static String URL =  "http://10.120.38.13:8081/ba103g1_Android/PetServletAndroid";
//    public final static String URL1 = "http://10.120.38.13:8081/ba103g1_Android/WebMailServletAndroid";
//    public final static String URL2 = "http://10.120.38.13:8081/ba103g1_Android/MembersServlet";

    public static boolean networkConnected(Activity activity) {
        ConnectivityManager conManager =
                (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = conManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    public static void showToast(Context context, int messageResId) {
        Toast.makeText(context, messageResId, Toast.LENGTH_SHORT).show();
    }

    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


}
