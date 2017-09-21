package idv.jack;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.java.iPet.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class ApdotionActivity extends AppCompatActivity {

    private final static String TAG = "ApdotionActivity";
    private MyTask caseTask;
    List<Case> csLists;
    ListView csListView;
    private SpotGetImageTask spotGetImageTask;
    private FragmentManager petinformation = getSupportFragmentManager();
    private FragmentManager ApdoInsert = getSupportFragmentManager();
    private FloatingActionButton fabtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apdotion);
        RecyclerView csRecycleView = (RecyclerView) findViewById(R.id.lvPet);
        csRecycleView.setLayoutManager(new LinearLayoutManager(this));
        getpetList();
        csRecycleView.setAdapter(new CaseAdapter(this, csLists));
        fabtn = (FloatingActionButton) findViewById(R.id.btnAdd);
        fabtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("aaa", "aaaaaaa");
                ApdoInsert fai = new ApdoInsert();
                ApdoInsert.beginTransaction().replace(R.id.flMainActivity, fai).addToBackStack(null).commit();
            }
        });
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
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Case>>() {
                }.getType();
                csLists = gson.fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (cases == null || cases.isEmpty()) {
//                Common.showToast(this, R.string.msg_NoSpotsFound);
            } else {
//                csListView.setAdapter(new CaseAdapter(this, cases));
            }
        } else {
            Common.showToast(this, R.string.msg_NoNetwork);
        }
    }
    private class CaseAdapter extends RecyclerView.Adapter<CaseAdapter.MyViewHolder> {
        private LayoutInflater layoutInflater;
        List<Case> csList;
        private int imageSize;
        CaseAdapter(Context context, List<Case> csList) {
            layoutInflater = LayoutInflater.from(context);
            this.csList = csList;
            /* 螢幕寬度除以4當作將圖的尺寸 */
            imageSize = getResources().getDisplayMetrics().widthPixels / 4;
        }

        @Override
        public int getItemCount() {
            return csList.size();
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = layoutInflater.inflate(R.layout.item_view, parent, false);
            return new MyViewHolder(itemView);
        }



        //要抓值設定UI顯示的寫在這裡
        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int position) {
            final Case cs = csList.get(position);
            String url = Common.URL;
            spotGetImageTask = new SpotGetImageTask(Common.URL, cs.getPetNo(), imageSize, myViewHolder.tvImg);
            spotGetImageTask.execute();
            myViewHolder.tvName.setText(cs.getPetName());
            myViewHolder.tvPetcolor.setText(cs.getPetColor());
            myViewHolder.tvAge.setText(cs.getPetAge());
            myViewHolder.tvSize.setText(cs.getPetSize());
            myViewHolder.tvStatus.setText(cs.getStatus());
            myViewHolder.tvSituation.setText(cs.getSituation());
            myViewHolder.tvPetsex.setText(cs.getPetSize());
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    petInformation fragment = new petInformation();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("cs", cs);
                    fragment.setArguments(bundle);
//                    FragmentTransaction fragmentTransaction =
//                            getFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.flMainActivity, fragment);
//                    fragmentTransaction.addToBackStack(null);
//                    fragmentTransaction.commit();
                    petinformation.beginTransaction().replace(R.id.flMainActivity, fragment).addToBackStack(null).commit();
                }
            });
        }

        //要用的UI宣告在這裡
        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView tvImg;
            TextView tvName, tvAge,tvSize, tvStatus, tvPetcolor, tvPetposition, tvSituation ,tvPetsex;
            MyViewHolder(View itemView) {
                super(itemView);
                tvName = (TextView) itemView.findViewById(R.id.tvName);
                tvAge = (TextView) itemView.findViewById(R.id.tvAge);
                tvSize = (TextView) itemView.findViewById(R.id.tvSize);
                tvStatus = (TextView) itemView.findViewById(R.id.tvStatus);
                tvPetcolor = (TextView) itemView.findViewById(R.id.tvPetcolor);
                tvPetposition = (TextView) itemView.findViewById(R.id.tvPetposition);
                tvSituation = (TextView) itemView.findViewById(R.id.tvSituation);
                tvPetsex = (TextView) itemView.findViewById(R.id.tvPetsex);
                tvImg = (ImageView) itemView.findViewById(R.id.tvImg);

            }

        }

    }

}


