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

public class GetByteTask extends AsyncTask<String, Integer, Bitmap> {
    private static final String TAG = "GetByteTask";
    private ByteListener byteListener;
    private Context context;
    private int id, progress;
    private int imgSize = 0;

    public GetByteTask(ByteListener byteListener, Context context, int id, int imgSize) {
        this.byteListener = byteListener;
        this.context = context;
        this.id = id;
        this.imgSize = imgSize;
    }

    public GetByteTask(ByteListener byteListener, Context context, int id) {
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
//        try {
//            HttpURLConnection connection = getConnetion(serverAddr);
//            int resCode = output(connection);
//            if (resCode == 200) {
//                InputStream in = connection.getInputStream();
//                bitmap = decodeByBitmap(in);
//            } else {
//                Log.d(TAG, "doInBackground: resCode not 200 resCode is" + resCode);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.d(TAG, "doInBackground: Exception");
//        }
        bitmap = decodeByBitmap(getData(serverAddr));
        return bitmap;
    }

    private HttpURLConnection getConnetion(String serverAddr) throws IOException {
        URL url = new URL(serverAddr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setDoInput(true);
        connection.setDoOutput(true);
        connection.setUseCaches(false);
        connection.setConnectTimeout(10000);
        connection.setReadTimeout(10000);
        connection.setRequestMethod("POST");
        connection.setRequestProperty("charset", "UTF-8");
        return connection;
    }

    private Bitmap decodeByBitmap(InputStream in) {
        Bitmap bitmap = BitmapFactory.decodeStream(in);
        return bitmap;
    }

    private int output(HttpURLConnection connection) throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        if (imgSize != 0) {
            jsonObject.addProperty("param", "getImg");
        } else {
            jsonObject.addProperty("param", "getImg");
        }
        OutputStream out = connection.getOutputStream();
        BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
        bufferedWriter.write(jsonObject.toString());
        Log.d(TAG, "doInBackgroundOut: jsonOut" + jsonObject.toString());
        bufferedWriter.close();
        int resCode = connection.getResponseCode();
        return resCode;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        progress = values[0];
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
            JsonArray jsonArray = new JsonArray();
            jsonArray.add(jsonObject);
            Log.d(TAG, "outputString: " + jsonArray.toString());
            RequestBody requestBody = RequestBody.create(JSON, jsonArray.toString());
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
