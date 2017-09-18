package com.example.java.mybottomnav;


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

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class GetVOTask extends AsyncTask<String, Integer, List<PetWallVO>> {
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
        progress = values[0];
        Log.d(TAG, "onProgressUpdate: " + progress);
//            progressDialog.setMessage("Loading..." + progress + "%");
        if (progress > tempProgress) {
            asyncListener.onGoing(progress);
            tempProgress = progress;
            Log.d(TAG, "onProgressUpdate: " + tempProgress);
        }
    }

    @Override
    protected List<PetWallVO> doInBackground(String... params) {
        Log.d(TAG, "doInBackground: ");
        String serverAddr = params[0];
        List<PetWallVO> list = null;

//        HttpURLConnection connection = getConnetion(serverAddr);
//        int resCode = output(connection);
//        if (resCode == 200) {
//            list = decodeArray(input(connection));
//        } else {
//            Log.d(TAG, "doInBackground: not 200" + resCode);
//        }
        list = decodeArray(getStringData(serverAddr));
        return list;


    }

    private String input(HttpURLConnection connection) {
        String stringIn = null;
        try {
            InputStream in = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuilder jsonIn = new StringBuilder();
            String s;
            while ((s = bufferedReader.readLine()) != null) {
                jsonIn.append(s);
            }
            Log.d(TAG, "inPutString" + jsonIn);
            connection.disconnect();
            stringIn = jsonIn.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringIn;
    }

    private List<PetWallVO> decodeArray(String stringIn) {
        Gson gsonb = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        List<PetWallVO> list = gsonb.fromJson(stringIn, new TypeToken<List<PetWallVO>>() {
        }.getType());
        return list;
    }

    private int output(HttpURLConnection connection) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("param", keyWord);
        OutputStream out = null;
        int resCode = 0;
        try {
            out = connection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(out));
            bufferedWriter.write(jsonObject.toString());
            bufferedWriter.close();
            resCode = connection.getResponseCode();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.d(TAG, "doInBackgroundOut: jsonOut" + jsonObject.toString());

        return resCode;
    }

    private HttpURLConnection getConnetion(String serverAddr) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(serverAddr);
            connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setConnectTimeout(10000);
            connection.setReadTimeout(10000);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("charset", "UTF-8");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return connection;
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
    protected void onPostExecute(List<PetWallVO> petWallVO) {
        Log.d(TAG, "onPostExecute: ");
        super.onPostExecute(petWallVO);
        asyncListener.onFinish(petWallVO);
//        progressDialog.cancel();
    }
}
