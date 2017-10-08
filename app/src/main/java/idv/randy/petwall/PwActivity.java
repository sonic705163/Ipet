package idv.randy.petwall;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java.iPet.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import idv.randy.me.MembersVO;
import idv.randy.member.MemberActivity;
import idv.randy.ut.AsyncAdapter;
import idv.randy.ut.AsyncImageTask;
import idv.randy.ut.AsyncObjTask;
import idv.randy.ut.Me;

public class PwActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "PwActivity";
    private static final String URL = Me.PetServlet;
    MyVOAdapter myVOAdapter;
    Toolbar toolbar;
    TextView tvDog;
    TextView tvCat;
    private List<PwVO> mPwVO;

    AsyncAdapter getPwAdapter = new AsyncAdapter() {
        @Override
        public void onGoing(int progress) {
            Log.d(TAG, "onGoing: " + progress);
        }

        @Override
        public void onFinish(String result) {
            JsonObject jsonObject = new Gson().fromJson(result, JsonObject.class);
            String petWallVO = jsonObject.get("petWallVO").getAsString();
            List<PwVO> PwVOs = PwVO.decodeToList(petWallVO);
            String count = jsonObject.get("count").getAsString();
            Gson gson = new Gson();
            List<Integer> counts = gson.fromJson(count, new TypeToken<List<Integer>>() {
            }.getType());
            updateRv(PwVOs, counts);
        }
    };

    private ImageView ivSearch;
    private EditText etSearch;

    public static void start(Activity context, String param) {
        Intent intent = new Intent(context, PwActivity.class);
        intent.putExtra("param", param);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.r_activity_pw);
        findViews();
        Intent intent = getIntent();
        String param = intent.getExtras().getString("param");
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "getPw");
        jsonObject.addProperty("keyword", param);
        Log.d(TAG, "onCreate: param " + param);
        new AsyncObjTask(getPwAdapter, jsonObject).execute(URL);
        setSupportActionBar(toolbar);

        tvDog.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        if (savedInstanceState != null) {
            mPwVO = savedInstanceState.getParcelableArrayList("mPwVO");
        }
        if (myVOAdapter != null) {
            updateRv(mPwVO, null);
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.btnFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("UserData", MODE_PRIVATE);
                if (!pref.getBoolean("login", false)) {
                    Toast.makeText(Me.gc(), "請先登入", Toast.LENGTH_SHORT).show();
                    return;
                }
                Intent intent = new Intent(PwActivity.this, PwInsertActivity.class);
                startActivity(intent);
            }
        });
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvDog = (TextView) findViewById(R.id.tvAll);
        etSearch = (EditText) findViewById(R.id.etSearch);
        ivSearch = (ImageView) findViewById(R.id.ivSearch);
    }

    @Override
    public void onClick(View v) {
        hideKeyPad();
        JsonObject jsonObject = new JsonObject();
        switch (v.getId()) {
            case R.id.tvAll:
                jsonObject.addProperty("action", "getPw");
                jsonObject.addProperty("keyword", "");
                new AsyncObjTask(getPwAdapter, jsonObject).execute(URL);
                break;
            case R.id.ivSearch:
                String keyword = etSearch.getText().toString();
                jsonObject.addProperty("action", "getPw");
                jsonObject.addProperty("keyword", keyword);
                new AsyncObjTask(getPwAdapter, jsonObject).execute(URL);
            default:
                break;
        }
    }

    void hideKeyPad() {
        etSearch.clearFocus();
        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
                .hideSoftInputFromWindow(this
                                .getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
    }

    public void updateRv(List<PwVO> pwVO, @Nullable List<Integer> counts) {
        Log.d(TAG, "updateRv: ");
        this.mPwVO = pwVO;
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        if (counts != null) {
            myVOAdapter = new MyVOAdapter(pwVO, counts);
        } else {
            myVOAdapter = new MyVOAdapter(pwVO);
        }
        recyclerView.setAdapter(myVOAdapter);
        Log.d(TAG, "updateRv: " + pwVO.size());
//        staggeredGridLayoutManager.scrollToPositionWithOffset(pwVO.size()-1, 0);
//        recyclerView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                recyclerView.smoothScrollToPosition(pwVO.size()-1);
////                recyclerView.scr(pwVO.size()-1);
//            }
//        }, 500);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        hideKeyPad();
        JsonObject jsonObject = new JsonObject();
        switch (item.getItemId()) {
            case R.id.menuItem2:
                jsonObject.addProperty("action", "getPw");
                jsonObject.addProperty("keyword", "貓");
                new AsyncObjTask(getPwAdapter, jsonObject).execute(URL);
                break;
            case R.id.menuItem3:
                jsonObject.addProperty("action", "getPw");
                jsonObject.addProperty("keyword", "狗");
                new AsyncObjTask(getPwAdapter, jsonObject).execute(URL);
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    boolean used = false;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("mPwVO", (ArrayList<? extends Parcelable>) mPwVO);
    }

    public class MyVOAdapter extends RecyclerView.Adapter<MyVOAdapter.MyViewHolder> {
        private List<PwVO> mPwVO;
        private List<Integer> counts;

        public MyVOAdapter(List<PwVO> pwVO, List<Integer> counts) {
            mPwVO = pwVO;
            this.counts = counts;
        }

        public MyVOAdapter(List<PwVO> pwVO) {
            this.mPwVO = pwVO;
        }

        Set<Integer> praised = new HashSet<>();

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(Me.gc());
            View v = layoutInflater.inflate(R.layout.r_activity_pw_item, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(v);

            View.OnClickListener onReplyClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = myViewHolder.getAdapterPosition();
                    PwVO pw = mPwVO.get(position);
                    int pwNo = pw.getPwNo();
                    PwDetailActivity.start(PwActivity.this, pwNo);

                }
            };
            myViewHolder.llPwReply.setOnClickListener(onReplyClickListener);
            myViewHolder.ivPwPicture.setOnClickListener(onReplyClickListener);

            View.OnClickListener onMemberClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = myViewHolder.getAdapterPosition();
                    PwVO pw = mPwVO.get(position);
                    int memNo = pw.getMemno();
                    MemberActivity.start(PwActivity.this, memNo);
                }
            };
            myViewHolder.ivMemImg.setOnClickListener(onMemberClickListener);
            myViewHolder.tvMemID.setOnClickListener(onMemberClickListener);

            View.OnClickListener onPwPraiseClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = myViewHolder.getAdapterPosition();
                    PwVO pw = mPwVO.get(position);
                    int pwPraise = Integer.valueOf(pw.getPwPraise());
                    if (!praised.contains(position)) {
                        praised.add(position);
                        pwPraise += 1;
                    } else {
                        praised.remove(position);
                        pwPraise -= 1;

                    }
                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("action", "updatePraise");
                    jsonObject.addProperty("pwNo", pw.getPwNo());
                    jsonObject.addProperty("praise", String.valueOf(pwPraise));
                    new AsyncObjTask(null, jsonObject).execute(Me.PetServlet);
                    pw.setPwPraise(String.valueOf(Integer.valueOf(pwPraise)));
                    myVOAdapter.notifyItemChanged(position, 0);
                }
            };
            myViewHolder.llPwPraise.setOnClickListener(onPwPraiseClickListener);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position, List<Object> payloads) {
            PwVO pw = mPwVO.get(position);
            if (payloads.isEmpty()) {
                Log.d(TAG, "payloads : " + "Not Exist " + position);
                if (counts != null) {
                    int count = counts.get(position);
                    if (count > 0) {
                        holder.tvPwrCount.setText(String.valueOf(count));
                    } else {
                        holder.tvPwrCount.setText("留言");
                    }
                }
                holder.tvPwContent.setText(pw.getPwContent());
                if (Integer.valueOf(pw.getPwPraise()) > 0) {
                    holder.tvPwPraise.setText(pw.getPwPraise());
                } else {
                    holder.tvPwPraise.setText("讚");
                }


                Date current = new Date(System.currentTimeMillis());
                long past = (current.getTime() - pw.getPwDate().getTime());
                int day = (int) (past / (1000 * 60 * 60 * 24));
                if (day < 1) {
                    holder.tvPWdate.setText("今天");
                } else if (day < 2) {
                    holder.tvPWdate.setText("昨天");
                } else if (day < 7) {
                    holder.tvPWdate.setText(String.valueOf(day) + "天前");
                } else if (pw.getPwDate().toString().substring(0, 4).equals(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)))) {
                    holder.tvPWdate.setText(pw.getPwDate().toString().substring(5));
                } else {
                    holder.tvPWdate.setText(pw.getPwDate().toString());
                }


                int pwNo = pw.getPwNo();
                new AsyncImageTask(pwNo, holder.ivPwPicture).execute(Me.PetServlet);

                int memNo = pw.getMemno();
                new AsyncImageTask(memNo, holder.ivMemImg).execute(Me.MembersServlet);

                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "getVO");
                jsonObject.addProperty("memNo", memNo);

                new AsyncObjTask(new AsyncAdapter() {
                    @Override
                    public void onFinish(String result) {
                        super.onFinish(result);

                        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                        MembersVO membersVO = gson.fromJson(result, MembersVO.class);
                        holder.tvMemID.setText(membersVO.getMemName());
                    }
                }, jsonObject).execute(Me.MembersServlet);
            } else {
                int type = (int) payloads.get(0);
                Log.d(TAG, "payloads : " + "Exist " + position);
                switch (type) {
                    case 0:
                        Log.d(TAG, "updatePwPraise: ");
                        holder.tvPwPraise.setText(String.valueOf(Integer.valueOf(pw.getPwPraise())));

                }
            }
        }


        @Override
        public int getItemCount() {
            return mPwVO.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvLeft;
            TextView tvPwContent;
            ImageView ivPwPicture;
            ImageView ivMemImg;
            TextView tvMemID;
            TextView tvPwPraise;
            TextView tvPWdate;
            TextView tvPwrCount;
            LinearLayout llPwReply;
            LinearLayout llPwPraise;


            public MyViewHolder(View itemView) {
                super(itemView);
                tvLeft = (TextView) itemView.findViewById(R.id.tvLeft);
                tvPwContent = (TextView) itemView.findViewById(R.id.tvPwContent);
                ivPwPicture = (ImageView) itemView.findViewById((R.id.ivPet));
                ivMemImg = (ImageView) itemView.findViewById((R.id.ivMemImg));
                tvMemID = (TextView) itemView.findViewById(R.id.tvMemID);
                tvPwPraise = (TextView) itemView.findViewById(R.id.tvPwPraise);
                tvPWdate = (TextView) itemView.findViewById(R.id.tvPWdate);
                tvPwrCount = (TextView) itemView.findViewById(R.id.tvPwrCount);
                llPwReply = (LinearLayout) itemView.findViewById(R.id.llPwReply);
                llPwPraise = (LinearLayout) itemView.findViewById(R.id.llPwPraise);
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "getPw");
        jsonObject.addProperty("keyword", "");
        new AsyncObjTask(getPwAdapter, jsonObject).execute(URL);
    }
}
