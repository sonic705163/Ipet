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
import java.sql.Timestamp;
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

public class PwActivity extends AppCompatActivity implements View.OnClickListener, PwDetailActivity.PwrListener {
    private static final int UPDATE_PRAISE = 0;
    private static final int UPDATE_PWRCOUNT = 1;
    private static final String TAG = "PwActivity";
    private static final String URL = Me.PetServlet;
    private MyVOAdapter myVOAdapter;
    private Toolbar toolbar;
    private TextView tvAll;
    private int position;
    private int pwNo;


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

        tvAll.setOnClickListener(this);
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
        tvAll = (TextView) findViewById(R.id.tvAll);
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
                String keyword = etSearch.getText().toString().trim();
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
//        ((SimpleItemAnimator)recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
//        recyclerView.getItemAnimator().setChangeDuration(0);
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("mPwVO", (ArrayList<? extends Parcelable>) mPwVO);
    }

    @Override
    public void onPwrSend() {
        myVOAdapter.notifyItemChanged(position, UPDATE_PWRCOUNT);
    }

    @Override
    public void onDelete() {
        refresh();
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
                    position = myViewHolder.getAdapterPosition();
                    PwVO pw = mPwVO.get(position);
                    pwNo = pw.getPwNo();
//                    count = counts.get(position);
                    PwDetailActivity.start(PwActivity.this, pwNo, pw.getMemno());
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
                    myVOAdapter.notifyItemChanged(position, UPDATE_PRAISE);
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
                holder.tvPWdate.setText(getPwDateString(pw.getPwDate()));
                int pwNo = pw.getPwNo();
                new AsyncImageTask(pwNo, holder.ivPwPicture, R.drawable.empty).execute(Me.PetServlet);

                int memNo = pw.getMemno();
                new AsyncImageTask(memNo, holder.ivMemImg, R.drawable.person).execute(Me.MembersServlet);

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
                    case UPDATE_PRAISE:
                        Log.d(TAG, "updatePwPraise: case 0");
                        if (Integer.valueOf(pw.getPwPraise()) > 0) {
                            holder.tvPwPraise.setText(pw.getPwPraise());
                        } else {
                            holder.tvPwPraise.setText("讚");
                        }
                        break;
                    case UPDATE_PWRCOUNT:
                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("action", "getCount");
                        jsonObject.addProperty("pwNo", pwNo);
                        new AsyncObjTask(new AsyncAdapter() {
                            @Override
                            public void onFinish(String result) {
                                super.onFinish(result);
                                JsonObject jsonObject = new Gson().fromJson(result, JsonObject.class);
                                int count = jsonObject.get("count").getAsInt();
                                holder.tvPwrCount.setText(String.valueOf(count));
                            }
                        }, jsonObject).execute(Me.PetServlet);
                }
            }
        }

        private String getPwDateString(Timestamp tsPwDate) {
            String text;
            Calendar calendar = Calendar.getInstance();
            Timestamp current = new Timestamp(calendar.getTimeInMillis());
            long past = ((current.getTime()) - (tsPwDate.getTime()));
            String currentYear = String.valueOf(calendar.get(Calendar.YEAR));
            String currentData = String.valueOf(calendar.get(Calendar.DATE));
            String pwDay = tsPwDate.toString();
            String pwYear = pwDay.substring(0, 4);
            String pwMonth = pwDay.substring(5, 6).equals("0") ? pwDay.substring(6, 7) : pwDay.substring(5, 7);
            String pwDate = pwDay.substring(8, 9).equals("0") ? pwDay.substring(9, 10) : pwDay.substring(8, 10);
            int intPwHour = Integer.valueOf(pwDay.substring(11, 13));
            String pwHour;
            if (intPwHour < 12) {
                pwHour = "上午" + intPwHour;
            } else if (intPwHour == 12) {
                pwHour = "下午" + intPwHour;
            } else {
                pwHour = "下午" + (intPwHour - 12);
            }
            String pwSecond = pwDay.substring(14, 16);
            int day = (int) (past / (1000 * 60 * 60 * 24));
            int hour = (int) (past / (1000 * 60 * 60));
            int minute = (int) (past / (1000 * 60));
            int second = (int) (past / (1000));
            if (past < 0) {
                text = "剛才";
            } else if (minute < 1) {
                text = (second + "秒前");
            } else if (hour < 1) {
                text = (minute + "分鐘前");
            } else if (day < 1 && pwHour.equals(currentData)) {
                text = (hour + "小時前");
            } else if (day < 2) {
                text = ("昨天" + pwHour + ":" + pwSecond);
            } else if (day < 7) {
                text = (day + "天前");
            } else if (pwYear.equals(currentYear)) {
                text = (pwMonth + "月" + pwDate + "日");
            } else {
                text = (pwYear + "年" + pwMonth + "月" + pwDate + "日");
            }
            return text;
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
            ImageView ivPwPraise;
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
                ivPwPraise = (ImageView) itemView.findViewById((R.id.ivPwPraise));
                tvMemID = (TextView) itemView.findViewById(R.id.tvMemID);
                tvPwPraise = (TextView) itemView.findViewById(R.id.tvPwPraise);
                tvPWdate = (TextView) itemView.findViewById(R.id.tvPWdate);
                tvPwrCount = (TextView) itemView.findViewById(R.id.tvPwrCount);
                llPwReply = (LinearLayout) itemView.findViewById(R.id.llPwReply);
                llPwPraise = (LinearLayout) itemView.findViewById(R.id.llPwPraise);
            }
        }
    }

    public void refresh() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "getPw");
        jsonObject.addProperty("keyword", "");
        new AsyncObjTask(getPwAdapter, jsonObject).execute(URL);
    }
}
