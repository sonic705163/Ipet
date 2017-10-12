package idv.jack;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
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
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ApdotionActivity extends AppCompatActivity {

    private final static String TAG = "ApdotionActivity";
    private MyTask caseTask;
    List<Case> csLists;
    private SpotGetImageTask spotGetImageTask;
    private FragmentManager petinformation = getSupportFragmentManager();

    private FloatingActionButton fabtn;
    private SwipeRefreshLayout swipeRefreshLayout;
    private CaseAdapter caseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apdotion);

        swipeRefreshLayout =
                (SwipeRefreshLayout) findViewById(R.id.swipeRefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                getpetList();
                caseAdapter.notifyDataSetChanged();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        RecyclerView csRecycleView = (RecyclerView) findViewById(R.id.lvPet);
        csRecycleView.setLayoutManager(new LinearLayoutManager(this));
        getpetList();
        caseAdapter = new CaseAdapter(this);
        csRecycleView.setAdapter(caseAdapter);
        fabtn = (FloatingActionButton) findViewById(R.id.btnAdd);


    }

    private void getpetList() {
        if (Common.networkConnected(this)) {
            String url = Common.URL;
            List<Case> cases = null;
            try {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("param", "getAll");
                String jsonOut = jsonObject.toString();
                caseTask = new MyTask(url, jsonOut);
                String jsonIn = caseTask.execute().get();
                Log.d(TAG, jsonIn);
//                Gson gson = new Gson();
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
                Type listType = new TypeToken<List<Case>>() {
                }.getType();
                csLists = gson.fromJson(jsonIn, listType);
                Log.e(TAG, "csLists.size = " + csLists.size());
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (cases == null || cases.isEmpty()) {
                Common.showToast(this, R.string.msg_NoSpotsFound);
            } else {
//                csListView.setAdapter(new CaseAdapter(this, cases));
            }
        } else {
            Common.showToast(this, R.string.msg_NoNetwork);
        }

    }

    @Override
    public void onStart() {
        super.onStart();
        getpetList();
        caseAdapter.notifyDataSetChanged();
    }

    private class CaseAdapter extends RecyclerView.Adapter<CaseAdapter.MyViewHolder> {
        private LayoutInflater layoutInflater;
        private int imageSize;

        CaseAdapter(Context context) {
            layoutInflater = LayoutInflater.from(context);
            /* 螢幕寬度除以4當作將圖的尺寸 */
            imageSize = getResources().getDisplayMetrics().widthPixels / 4;
        }

        @Override
        public int getItemCount() {
            return csLists.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.item_view, parent, false);

            return new MyViewHolder(itemView);
        }


        //要抓值設定UI顯示的寫在這裡
        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
            final Case cs = csLists.get(position);
            spotGetImageTask = new SpotGetImageTask(Common.URL, cs.getPetNo(), imageSize, myViewHolder.tvImg);
            spotGetImageTask.execute();
            myViewHolder.tvSituation.setText(cs.getSituation());


            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    petInformation fragment = new petInformation();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("cs", cs);
                    fragment.setArguments(bundle);
                    petinformation.beginTransaction().replace(R.id.flMainActivity, fragment).addToBackStack(null).commit();

                }
            });

            fabtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(),ApdoInsertActivity.class);
                    startActivity(intent);
//                    ApdoInsert fai = new ApdoInsert();
//                    ApdoInsert.beginTransaction().replace(R.id.flapdoinsert, fai).addToBackStack(null).commit();
                }
            });

        }

        //要用的UI宣告在這裡
        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView tvImg;
            TextView tvSituation;

            MyViewHolder(View itemView) {
                super(itemView);
                tvSituation = (TextView) itemView.findViewById(R.id.tvSituation);
                tvImg = (ImageView) itemView.findViewById(R.id.tvImg);

            }

        }

    }

}


