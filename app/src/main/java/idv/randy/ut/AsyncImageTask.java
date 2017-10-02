package idv.randy.ut;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.JsonObject;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AsyncImageTask extends AsyncTask<String, Integer, Bitmap> {
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private static final String TAG = "AsyncImageTask";
    ImageView imageView;
    private int no;
    private int imgSize = 0;


    public AsyncImageTask(int no, ImageView imageView) {
        this.no = no;
        this.imageView = imageView;
    }

    public AsyncImageTask(int no, ImageView imageView, int imgSize) {
        this.no = no;
        this.imageView = imageView;
        this.imgSize = imgSize;
    }

    @Override
    protected Bitmap doInBackground(String... params) {
        Log.d(TAG, "doInBackground: ");
        String serverAddr = params[0];
        InputStream in = getData(serverAddr);
        Bitmap bitmap = BitmapFactory.decodeStream(in);
        return bitmap;
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageBitmap(null);
        }


    }

    private InputStream getData(String serverAddr) {
        InputStream responseData = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getImg");
            jsonObject.addProperty("no", no);
            Log.d(TAG, "outputString: " + jsonObject.toString());
            RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());
            Request request = new Request.Builder().url(serverAddr).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            responseData = response.body().byteStream();
            Log.d(TAG, "inputByteStream: " + responseData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseData;
    }
}
