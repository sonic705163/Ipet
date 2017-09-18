package com.example.java.mybottomnav;

import android.app.ProgressDialog;
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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PetWallMiddle extends AppCompatActivity implements View.OnClickListener {
    @Override
    public void onClick(View v) {

    }
//    private static final String TAG = "RetrieveActivity";
//    private static final String URL = "http://10.0.2.2:8081/AccessServer/petServlet";
//    int resCode;
//    private ProgressDialog progressDialog;
//    private List<PetWallVO> petWallVO;
//    private AsyncTask getDataTask;
//    private ImageView ivSearch;
//    private EditText etSearch;
//    private RecyclerView rv;
//    MyVOAdapter myVOAdapter;
//
//    AsyncListener asyncListener = new AsyncListener() {
//        @Override
//        public void onError() {
//            Log.d(TAG, "onError: " + resCode);
//        }
//
//        @Override
//        public void onGoing(int progress) {
//            progressDialog.setMessage("Loading..." + progress + "%");
//            Log.d(TAG, "onGoing: " + progress);
//        }
//
//        @Override
//        public void onFinish(List<PetWallVO> petWallVO) {
//            updateRv(petWallVO);
//        }
//
//    };


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Log.d(TAG, "onCreate: ");
//        setContentView(R.layout.activity_retrieve);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
//        TextView tvDog = (TextView) findViewById(R.id.tvDog);
//        TextView tvCat = (TextView) findViewById(R.id.tvCat);
//        etSearch = (EditText) findViewById(R.id.etSearch);
//        ivSearch = (ImageView) findViewById(R.id.ivSearch);
//        tvDog.setOnClickListener(this);
//        tvCat.setOnClickListener(this);
//        ivSearch.setOnClickListener(this);
//        if (savedInstanceState != null) {
//            myVOAdapter = (MyVOAdapter) savedInstanceState.getSerializable("adapter");
//            petWallVO = savedInstanceState.getParcelableArrayList("vo");
//        }
//        if (myVOAdapter != null) {
//            updateRv(petWallVO);
//        }
//    }
//
//    @Override
//    public void onClick(View v) {
//        hideKeyPad();
//        switch (v.getId()) {
//            case R.id.tvDog:
//                getDataTask = new GetVOTask(asyncListener, "dog", this).execute(URL);
//
//                break;
//            case R.id.tvCat:
//                getDataTask = new GetVOTask(asyncListener, "cat", this).execute(URL);
//                break;
//            case R.id.ivSearch:
//                String keyword = etSearch.getText().toString();
//                getDataTask = new GetVOTask(asyncListener, keyword, this).execute(URL);
//            default:
//                break;
//        }
//    }
//
//    void hideKeyPad() {
//        etSearch.clearFocus();
//        ((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
//                .hideSoftInputFromWindow(this
//                                .getCurrentFocus().getWindowToken(),
//                        InputMethodManager.HIDE_NOT_ALWAYS);
//    }
//
//    public void updateRv(List<PetWallVO> petWallVO) {
//        Log.d(TAG, "updateRv: ");
//        this.petWallVO = petWallVO;
//        rv = (RecyclerView) findViewById(R.id.rv);
//        rv.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
//        myVOAdapter = new MyVOAdapter(petWallVO);
//        rv.setAdapter(myVOAdapter);
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        hideKeyPad();
//        Log.d(TAG, "onOptionsItemSelected: ");
//        switch (item.getItemId()) {
//            case R.id.menuItem2:
//                getDataTask = new GetVOTask(asyncListener, "cat", this).execute(URL);
//                break;
//            case R.id.menuItem3:
//                getDataTask = new GetVOTask(asyncListener, "all", this).execute(URL);
//                break;
//            default:
//                break;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    public class MyVOAdapter extends RecyclerView.Adapter<MyVOAdapter.MyViewHolder> implements Serializable {
//        private List<PetWallVO> petWallVO;
//        Set<Integer> set = new HashSet<>();
//
//        public MyVOAdapter(List<PetWallVO> petWallVO) {
//            this.petWallVO = petWallVO;
//        }
//
//        @Override
//        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//            LayoutInflater layoutInflater = LayoutInflater.from(MyApp.gc());
//            View v = layoutInflater.inflate(R.layout.rv_row_item, parent, false);
//            final MyViewHolder myViewHolder = new MyViewHolder(v);
//            myViewHolder.itemView.setOnClickListener(v1 -> {
//                int position = myViewHolder.getAdapterPosition();
//                final PetWallVO pw = petWallVO.get(position);
//            });
//            return myViewHolder;
//        }
//
//        @Override
//        public void onBindViewHolder(final MyViewHolder holder, int position) {
//            ByteListener byteListener = bitmap -> {
//                if (bitmap != null) {
//                    holder.ivPet.setImageBitmap(bitmap);
//                } else {
//                    holder.ivPet.setImageResource(R.drawable.ic_search_black_24dp);
//                }
//            };
//            final PetWallVO pw = petWallVO.get(position);
//            holder.tvRight.setText(pw.getPwContent());
//            int id = pw.getPwNo();
//            if (pw.getPwPicture() == null) {
//                if (!set.contains(id)) {
//                    set.add(id);
//                    new GetByteTask(byteListener, RetrieveActivity.this, id).execute(URL);
//                }
//            } else {
//                byte[] imgByte = pw.getPwPicture();
//                String imgString = Base64.encodeToString(imgByte, Base64.DEFAULT);
//                byte[] decodeString = Base64.decode(imgString, Base64.DEFAULT);
//                Bitmap bitmap = BitmapFactory.decodeByteArray(decodeString, 0, decodeString.length);
//                holder.ivPet.setImageBitmap(bitmap);
//            }
//        }
//
//        @Override
//        public int getItemCount() {
//            return petWallVO.size();
//        }
//
//        class MyViewHolder extends RecyclerView.ViewHolder {
//            TextView tvLeft;
//            TextView tvRight;
//            ImageView ivPet;
//
//            public MyViewHolder(View itemView) {
//                super(itemView);
//                tvLeft = (TextView) itemView.findViewById(R.id.tvLeft);
//                tvRight = (TextView) itemView.findViewById(R.id.tvRight);
//                ivPet = (ImageView) itemView.findViewById((R.id.ivPet));
//            }
//        }
//    }

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putParcelableArrayList("vo", (ArrayList<? extends Parcelable>) petWallVO);
//        outState.putSerializable("adapter", myVOAdapter);
    }
}
