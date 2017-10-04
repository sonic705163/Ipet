package idv.randy.petwall;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.java.iPet.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import idv.randy.me.MembersVO;
import idv.randy.ut.AsyncAdapter;
import idv.randy.ut.AsyncImageTask;
import idv.randy.ut.AsyncObjTask;
import idv.randy.ut.GetVOTask;
import idv.randy.ut.Me;

public class PwActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RetrieveActivity";
    private static final String URL = Me.PetServlet;
    MyVOAdapter myVOAdapter;
    Toolbar toolbar;
    TextView tvDog;
    TextView tvCat;
    private List<PwVO> mPwVO;
    AsyncAdapter asyncAdapter = new AsyncAdapter() {
        @Override
        public void onGoing(int progress) {
            Log.d(TAG, "onGoing: " + progress);
        }

        @Override
        public void onFinish(String result) {
            List petWallVO = PwVO.decodeToList(result);
            updateRv(petWallVO);
        }
    };
    private AsyncTask getDataTask;
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
        new GetVOTask(asyncAdapter, param, this).execute(URL);
        setSupportActionBar(toolbar);
        tvDog.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        if (savedInstanceState != null) {
            mPwVO = savedInstanceState.getParcelableArrayList("mPwVO");
        }
        if (myVOAdapter != null) {
            updateRv(mPwVO);
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
        tvDog = (TextView) findViewById(R.id.tvDog);
        etSearch = (EditText) findViewById(R.id.etSearch);
        ivSearch = (ImageView) findViewById(R.id.ivSearch);
    }

    @Override
    public void onClick(View v) {
        hideKeyPad();
        switch (v.getId()) {
            case R.id.tvDog:
                getDataTask = new GetVOTask(asyncAdapter, "all", this).execute(URL);
                break;
            case R.id.ivSearch:
                String keyword = etSearch.getText().toString();
                getDataTask = new GetVOTask(asyncAdapter, keyword, this).execute(URL);
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

    public void updateRv(List<PwVO> pwVO) {
        Log.d(TAG, "updateRv: ");
        this.mPwVO = pwVO;
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);
        myVOAdapter = new MyVOAdapter(pwVO);
        recyclerView.setAdapter(myVOAdapter);
        Log.d(TAG, "updateRv: " + pwVO.size());
//        staggeredGridLayoutManager.scrollToPositionWithOffset(pwVO.size()-1, 0);
//        recyclerView.postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                recyclerView.smoothScrollToPosition(pwVO.size()-1);
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
        Log.d(TAG, "onOptionsItemSelected: ");
        switch (item.getItemId()) {
            case R.id.menuItem2:
                getDataTask = new GetVOTask(asyncAdapter, "cat", this).execute(URL);
                break;
            case R.id.menuItem3:
                getDataTask = new GetVOTask(asyncAdapter, "dog", this).execute(URL);
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

    public class MyVOAdapter extends RecyclerView.Adapter<MyVOAdapter.MyViewHolder> {
        private List<PwVO> mPwVO;

        public MyVOAdapter(List<PwVO> pwVO) {
            this.mPwVO = pwVO;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(Me.gc());
            View v = layoutInflater.inflate(R.layout.r_activity_pw_item, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(v);
            myViewHolder.itemView.setOnClickListener(v1 -> {
                int position = myViewHolder.getAdapterPosition();
                PwVO pw = mPwVO.get(position);
                int pwNo = pw.getPwNo();
                PwDetailActivity.start(PwActivity.this, pwNo);
            });
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, int position) {
            PwVO pw = mPwVO.get(position);
            holder.tvPwContent.setText(pw.getPwContent());

            int pwNo = pw.getPwNo();
            new AsyncImageTask(pwNo,holder.ivPet).execute(Me.PetServlet);

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
        }

        @Override
        public int getItemCount() {
            return mPwVO.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvLeft;
            TextView tvPwContent;
            ImageView ivPet;
            ImageView ivMemImg;
            TextView tvMemID;

            public MyViewHolder(View itemView) {
                super(itemView);
                tvLeft = (TextView) itemView.findViewById(R.id.tvLeft);
                tvPwContent = (TextView) itemView.findViewById(R.id.tvPwContent);
                ivPet = (ImageView) itemView.findViewById((R.id.ivPet));
                ivMemImg = (ImageView) itemView.findViewById((R.id.ivMemImg));
                tvMemID = (TextView) itemView.findViewById(R.id.tvMemID);
            }
        }
    }

}
