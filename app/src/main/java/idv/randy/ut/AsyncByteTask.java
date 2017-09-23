package idv.randy.ut;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AsyncByteTask extends AsyncTask<String, Integer, Bitmap> {
    private static final String TAG = "AsyncByteTask";
    private ByteListener byteListener;
    private Context context;
    private int id, progress;
    private int imgSize = 0;

    public AsyncByteTask(ByteListener byteListener, Context context, int id, int imgSize) {
        this.byteListener = byteListener;
        this.context = context;
        this.id = id;
        this.imgSize = imgSize;
    }

    public AsyncByteTask(ByteListener byteListener, Context context, int id) {
        this.byteListener = byteListener;
        this.context = context;
        this.id = id;
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "onPreExecute: ");
        super.onPreExecute();
    }

    @Override
    protected Bitmap doInBackground(String... params) {

        Log.d(TAG, "doInBackground: ");
        String serverAddr = params[0];
        Bitmap bitmap = null;
        bitmap = decodeByBitmap(getData(serverAddr));
        return bitmap;
    }
    private Bitmap decodeByBitmap(InputStream in) {
        Bitmap bitmap = BitmapFactory.decodeStream(in);
        return bitmap;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        Log.d(TAG, "onProgressUpdate: " + progress);
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        Log.d(TAG, "onPostExecute: ");
        super.onPostExecute(bitmap);
        byteListener.onFinish(bitmap);
    }

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private InputStream getData(String serverAddr) {
        InputStream responseData = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("param", "getImg");
            jsonObject.addProperty("id", id);
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
