package idv.jack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.java.iPet.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class MailReceive extends AppCompatActivity {
    private final static String TAG = "MailReceive";
    private RecyclerView rlwebmail;
    List<WebmailVO> webmaillist;
    private MyTask webmailtask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mailreceive);
        getWebMail();
        rlwebmail = (RecyclerView) findViewById(R.id.rlwebmail);
        rlwebmail.setLayoutManager(new LinearLayoutManager(this));
        rlwebmail.setAdapter(new WebmailSendAdapter(this));

    }

    private void getWebMail() {
        SharedPreferences pref = this.getSharedPreferences("UserData" ,MODE_PRIVATE);
        Integer memNo = pref.getInt("memNo", 0);
        if (Common.networkConnected(this)) {
            String url = Common.URL1;
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("action", "receive");
            jsonObject2.addProperty("memNo", memNo);
            String jsonOut2 = jsonObject2.toString();
            webmailtask = new MyTask(url, jsonOut2);
            try {
                String jsonIn2 = webmailtask.execute().get();
                Log.d(TAG, jsonIn2);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<WebmailVO>>() {
                }.getType();
                webmaillist = gson.fromJson(jsonIn2, listType);
                Log.d("aaa",webmaillist.size()+"");
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }

        } else {
            Common.showToast(this, "失敗");
        }
    }



    private class WebmailSendAdapter extends RecyclerView.Adapter<WebmailSendAdapter.MyViewHolder> {
        private LayoutInflater layoutInflater;
        private int imageSize;

        WebmailSendAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
            /* 螢幕寬度除以4當作將圖的尺寸 */
            imageSize = getResources().getDisplayMetrics().widthPixels / 4;
        }

        @Override
        public int getItemCount() {

            return webmaillist.size();
        }

        @Override
        public WebmailSendAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View itemView = layoutInflater.inflate(R.layout.activity_mailreceive_list, parent, false);
            return new WebmailSendAdapter.MyViewHolder(itemView);
        }


        //要抓值設定UI顯示的寫在這裡
        @Override
        public void onBindViewHolder(WebmailSendAdapter.MyViewHolder myViewHolder, int position) {
            final WebmailVO wv = webmaillist.get(position);
//            spotGetImageTask = new SpotGetImageTask(Common.URL, cs.getPetNo(), imageSize, myViewHolder.tvImg);
//            spotGetImageTask.execute();
            myViewHolder.tvWhoSend.setText(String.valueOf(wv.getGetmemNo()));


        }

        //要用的UI宣告在這裡
        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView tvImg;
            TextView tvWhoSend;

            MyViewHolder(View itemView) {
                super(itemView);
                tvWhoSend = (TextView) itemView.findViewById(R.id.tvWhoSend);

            }

        }

    }
}
