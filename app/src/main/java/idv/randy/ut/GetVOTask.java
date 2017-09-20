package idv.randy.ut;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import idv.randy.petwall.AsyncListener;
import idv.randy.petwall.PetWallVO;
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

    public GetVOTask(AsyncListener asyncListener, String keyWord, Context context) {
        this.asyncListener = asyncListener;
        this.keyWord = keyWord;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        Log.d(TAG, "onPreExecute: ");
        super.onPreExecute();
//        progressDialog = new ProgressDialog(context);
//        progressDialog.setMessage("Loading..." + progress + "%");
//        progressDialog.show();
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
            jsonObject.addProperty("param", keyWord);
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
    protected void onPostExecute(String petWallVO) {
//        Log.d(TAG, "onPostExecute: ");
        super.onPostExecute(petWallVO);
        asyncListener.onFinish(petWallVO);
//        progressDialog.cancel();
    }
}
