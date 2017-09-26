package idv.randy.petwall;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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

import com.example.java.iPet.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import idv.randy.me.MembersVO;
import idv.randy.ut.AsyncAdapter;
import idv.randy.ut.AsyncByteTask;
import idv.randy.ut.AsyncImageTask;
import idv.randy.ut.AsyncObjTask;
import idv.randy.ut.ByteListener;
import idv.randy.ut.GetVOTask;
import idv.randy.ut.Me;

public class PwActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "RetrieveActivity";
    private static final String URL = Me.PetServlet;
    private List<PwVO> mPwVO;
    private AsyncTask getDataTask;
    private ImageView ivSearch;
    private EditText etSearch;
    private RecyclerView rv;
    MyVOAdapter myVOAdapter;
    String id;
    Toolbar toolbar;
    TextView tvDog;
    TextView tvCat;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        setContentView(R.layout.r_activity_pwitem_list);
        findViews();
        Intent intent = getIntent();
        String param = intent.getExtras().getString("param");
        new GetVOTask(asyncAdapter, param, this).execute(URL);
        setSupportActionBar(toolbar);
        tvDog.setOnClickListener(this);
        tvCat.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        if (savedInstanceState != null) {
            mPwVO = savedInstanceState.getParcelableArrayList("vo");
        }
        if (myVOAdapter != null) {
            updateRv(mPwVO);
        }
    }

    private void findViews() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvDog = (TextView) findViewById(R.id.tvDog);
        tvCat = (TextView) findViewById(R.id.tvCat);
        etSearch = (EditText) findViewById(R.id.etSearch);
        ivSearch = (ImageView) findViewById(R.id.ivSearch);
    }

    @Override
    public void onClick(View v) {
        hideKeyPad();
        switch (v.getId()) {
            case R.id.tvDog:
                getDataTask = new GetVOTask(asyncAdapter, "dog", this).execute(URL);

                break;
            case R.id.tvCat:
                getDataTask = new GetVOTask(asyncAdapter, "cat", this).execute(URL);
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
        rv = (RecyclerView) findViewById(R.id.rv);
        rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        myVOAdapter = new MyVOAdapter(pwVO);
        rv.setAdapter(myVOAdapter);
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
                getDataTask = new GetVOTask(asyncAdapter, "all", this).execute(URL);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public class MyVOAdapter extends RecyclerView.Adapter<MyVOAdapter.MyViewHolder> {
        private List<PwVO> mPwVO;

        public MyVOAdapter(List<PwVO> pwVO) {
            this.mPwVO = pwVO;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(Me.gc());
            View v = layoutInflater.inflate(R.layout.r_activity_pwitem, parent, false);
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
            ByteListener byteListener = bitmap -> {
                if (bitmap != null) {
                    holder.ivPet.setImageBitmap(bitmap);
                } else {
                    holder.ivPet.setImageResource(R.drawable.ic_search_black_24dp);
                }
            };
            PwVO pw = mPwVO.get(position);
            holder.tvPwContent.setText(pw.getPwContent());

            int pwNo = pw.getPwNo();
            if (pw.getPwPicture() == null) {
                new AsyncByteTask(byteListener, PwActivity.this, pwNo).execute(URL);
            } else {
                byte[] imgByte = pw.getPwPicture();
                String imgString = Base64.encodeToString(imgByte, Base64.DEFAULT);
                byte[] decodeString = Base64.decode(imgString, Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
                holder.ivPet.setImageBitmap(bitmap);
            }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList("vo", (ArrayList<? extends Parcelable>) mPwVO);
    }

    public static void start(Activity context, String param) {
        Intent intent = new Intent(context, PwActivity.class);
        intent.putExtra("param", param);
        context.startActivity(intent);
    }

}
