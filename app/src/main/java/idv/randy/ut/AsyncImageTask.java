package idv.randy.ut;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.example.java.iPet.R;
import com.google.gson.JsonObject;

import java.io.ByteArrayOutputStream;
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
    byte[] byt;


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
//        try {
//            byt = extract(in);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Bitmap bitmap = BitmapFactory.decodeStream(in);
        return bitmap;
    }


    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);

//            Glide.with(Me.gc()).load(byt).into(imageView);
//            Glide.with(Me.gc()).load(bitmap).into(imageView);

        } else {
            imageView.setImageResource(R.drawable.ic_touch_app_black_24dp);
//            imageView.setImageBitmap(null);


//            imageView.setVisibility(View.GONE);

//            imageView.set;

//            Glide.with(Me.gc()).load(bitmap).into(imageView);

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

    private byte[] extract(InputStream inputStream) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int read = 0;
        while ((read = inputStream.read(buffer, 0, buffer.length)) != -1) {
            baos.write(buffer, 0, read);
        }
        baos.flush();
        return baos.toByteArray();
    }
}
