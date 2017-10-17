package idv.randy.ut;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;

public class Me extends Application {
    private static Context context;
    private static String ip = "http://10.0.2.2:8081/ba103g1_Android/";
//        private static String ip = "http://10.120.38.31:8081/ba103g1_Android/";

//    private static String ip = "http://10.120.38.13:8081/ba103g1_Android/";
    public static final String MembersServlet = ip + "MembersServlet";
    public static final String PetServlet = ip + "PetServlet";
    public static final String PwrServlet = ip + "PwrServlet";
    public static Fragment current;

    public static Context gc() {
        return context;
    }

    private static final String TAG = "Me";


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

    public static Bitmap downSize(Bitmap srcBitmap, int newSize) {
        // 如果欲縮小的尺寸過小，就直接定為128
        if (newSize <= 50) {
            newSize = 128;
        }
        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        String text = "source image size = " + srcWidth + "x" + srcHeight;
        Log.d(TAG, text);
        int longer = Math.max(srcWidth, srcHeight);

        if (longer > newSize) {
            double scale = longer / (double) newSize;
            int dstWidth = (int) (srcWidth / scale);
            int dstHeight = (int) (srcHeight / scale);
            srcBitmap = Bitmap.createScaledBitmap(srcBitmap, dstWidth, dstHeight, false);
            System.gc();
            text = "\nscale = " + scale + "\nscaled image size = " +
                    srcBitmap.getWidth() + "x" + srcBitmap.getHeight();
            Log.d(TAG, text);
        }
        return srcBitmap;
    }

//    byte[] imgByte = pw.getPwPicture();
//    String imgString = Base64.encodeToString(imgByte, Base64.DEFAULT);
//    byte[] decodeString = Base64.decode(imgString, Base64.DEFAULT);
//    Bitmap bitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
//                holder.ivPet.setImageBitmap(bitmap);
}
