package idv.jack;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java.iPet.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import idv.randy.me.MembersVO;

public class MailReceive extends AppCompatActivity {
    private final static String TAG = "MailReceive";
    private RecyclerView rlwebmail;
    List<WebmailVO> webmaillist;
    private MyTask webmailtask, sendtask;
    private MembersVO mbVO;


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
        SharedPreferences pref = this.getSharedPreferences("UserData", MODE_PRIVATE);
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
                if (jsonIn2 != null) {
                    webmaillist = gson.fromJson(jsonIn2, listType);
                    Log.d("aaa", webmaillist.size() + "");
                } else {
                    Toast.makeText(this, "伺服器延遲", Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }

        } else {
            Common.showToast(this, "失敗");
        }
    }

    private Integer deleteMessage(String mailNo) {
//        SharedPreferences pref = this.getSharedPreferences("UserData" ,MODE_PRIVATE);
//        Integer memNo = pref.getInt("memNo", 0);
        Integer count = 0;
        if (Common.networkConnected(this)) {
            String url = Common.URL1;
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "deleteWebMailRecv");
//            jsonObject.addProperty("memNo", memNo);
            jsonObject.addProperty("mailNo", mailNo);
//            jsonObject.addProperty("WebmailVO",new Gson().toJson(mailNo));
            String jsonOut = jsonObject.toString();
            webmailtask = new MyTask(url, jsonOut);
            try {
                String jsonIn = webmailtask.execute().get();
                Log.d(TAG, jsonIn);
                Gson gson = new Gson();
                jsonObject = gson.fromJson(jsonIn, JsonObject.class);
                count = jsonObject.get("count").getAsInt();
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }


        } else {
            Common.showToast(this, "刪除失敗");
        }
        return count;
    }

    private void getMbName(Integer memNo) {
        if (Common.networkConnected(this)) {
            String url = null;
            url = Common.URL2;
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "getMbInfo");
            jsonObject.addProperty("memNo", memNo);
            String jsonOut = jsonObject.toString();
            sendtask = new MyTask(url, jsonOut);
            try {
                String jsonIn = sendtask.execute().get();
                Log.d(TAG, jsonIn);
                Type listType = new TypeToken<MembersVO>() {
                }.getType();
                mbVO = gson.fromJson(jsonIn, listType);

            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }

        } else {
            Common.showToast(this, "錯誤");
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
            getMbName(wv.getTomemNo());
//            myViewHolder.tvWhoSend.setText(String.valueOf(wv.getTomemNo()));
            myViewHolder.tvWhoSend.setText(mbVO.getMemName());
            myViewHolder.tvSendDate.setText(wv.getMailDate().toString().substring(0, 10));
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    petInformation fragment = new petInformation();
                    Intent intent = new Intent(MailReceive.this, WebMail_Detail_Activity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("wv", wv);
                    intent.putExtras(bundle);
                    startActivity(intent);
//                    fragment.setArguments(bundle);
//                    petinformation.beginTransaction().replace(R.id.flMainActivity, fragment).addToBackStack(null).commit();

                }
            });
            myViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    PopupMenu popupMenu = new PopupMenu(MailReceive.this, view, Gravity.END);
                    popupMenu.inflate(R.menu.webmail_delete);
                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(MenuItem item) {
                            switch (item.getItemId()) {
                                case R.id.delete:
//                                   Integer count = deleteMessage(String.valueOf(wv.getMailNo()));
                                    getWebMail();
                                    rlwebmail.setAdapter(new WebmailSendAdapter(MailReceive.this));
                                    Toast.makeText(MailReceive.this, "已刪除1筆信件", Toast.LENGTH_SHORT).show();
                                    break;
                                case R.id.insertmail:
                                    Intent intent = new Intent(MailReceive.this, ReplyMail.class);
                                    Bundle bundle = new Bundle();
                                    bundle.putSerializable("wv", wv.getTomemNo());
                                    intent.putExtras(bundle);
                                    startActivity(intent);
                                    break;
                            }
                            return true;
                        }
                    });
                    popupMenu.show();
                    return true;
                }
            });


        }

        //要用的UI宣告在這裡
        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView tvImg;
            TextView tvWhoSend, tvSendDate;

            MyViewHolder(View itemView) {
                super(itemView);
                tvWhoSend = (TextView) itemView.findViewById(R.id.tvWhoSend);
                tvSendDate = (TextView) itemView.findViewById(R.id.tvSendDate);
            }

        }

    }
}
