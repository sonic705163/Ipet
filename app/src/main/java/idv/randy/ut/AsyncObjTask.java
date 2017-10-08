package idv.randy.ut;


import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AsyncObjTask extends AsyncTask<String, Integer, String> {
    private static final String TAG = "AsyncObjTask";
    private AsyncListener asyncListener;
    private JsonObject jsonObject;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");


    public AsyncObjTask(@Nullable AsyncListener asyncListener, JsonObject jsonObject) {
        this.asyncListener = asyncListener;
        this.jsonObject = jsonObject;
    }

    @Override
    protected String doInBackground(String... params) {
        Log.d(TAG, "doInBackground: ");
        String serverAddr = params[0];
        return getStringData(serverAddr);
    }

    private String getStringData(String serverAddr) {
        String responseData = null;
        try {
            OkHttpClient okHttpClient = new OkHttpClient();
            Log.d(TAG, "OutputString: " + jsonObject.toString());
            RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());
            Request request = new Request.Builder().url(serverAddr).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            responseData = response.body().string();
            Log.d(TAG, "InputString: " + responseData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseData;
    }


    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        if (asyncListener != null && result != null) {
            asyncListener.onFinish(result);
        }
    }
}
