package idv.randy.ut;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetVOTask extends AsyncTask<String, Integer, String> {
    private static final String TAG = "GetVOTask";
    private AsyncListener asyncListener;
    private String keyWord;
    private ProgressDialog progressDialog;
    private Context context;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");
    private int tempProgress;
    int progress;

    public GetVOTask(@Nullable AsyncListener asyncListener, String keyWord, Context context) {
        this.asyncListener = asyncListener;
        this.keyWord = keyWord;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "onPreExecute: ");
        super.onPreExecute();

    }

    @Override
    protected void onProgressUpdate(Integer... values) {
//        progress = values[0];
//            progressDialog.setMessage("Loading..." + progress + "%");
//        if (progress > tempProgress) {
//            asyncListener.onGoing(progress);
//            tempProgress = progress;
//            Log.d(TAG, "onProgressUpdate: " + tempProgress);
//        }
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
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", keyWord);
            Log.d(TAG, "outputString: " + jsonObject.toString());
            RequestBody requestBody = RequestBody.create(JSON, jsonObject.toString());
            Request request = new Request.Builder().url(serverAddr).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            responseData = response.body().string();
            Log.d(TAG, "inputString: " + responseData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseData;
    }


    @Override
    protected void onPostExecute(String result) {
//        Log.d(TAG, "onPostExecute: ");
        super.onPostExecute(result);
        if (asyncListener != null) {
            asyncListener.onFinish(result);
        }

//        progressDialog.cancel();
    }
}
