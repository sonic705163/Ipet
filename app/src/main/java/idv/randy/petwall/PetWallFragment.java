package idv.randy.petwall;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.java.iPet.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import idv.randy.me.MembersVO;
import idv.randy.ut.AsyncAdapter;
import idv.randy.ut.AsyncObjTask;
import idv.randy.ut.Me;
import idv.randy.zNouse.DogPicVO;
import idv.randy.zNouse.wall_m;

public class PetWallFragment extends Fragment {
    private static final String TAG = "PetWallFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public List<DogPicVO> mDogPicVOList = getDogPicVOList();

    private View view;
    private String mParam1;
    private String mParam2;
    private int memNo;

    private OnFragmentInteractionListener mListener;

    public PetWallFragment() {
    }


    public static PetWallFragment newInstance(String param1, String param2) {
        PetWallFragment fragment = new PetWallFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        if (getArguments() != null) {
            memNo = getArguments().getInt("memNo");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Log.d(TAG, "onCreateView: ");
        view = inflater.inflate(R.layout.r_fragment_petwall_s, container, false);
        RecyclerView rcView = (RecyclerView) view.findViewById(R.id.rcView);
        rcView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        rcView.setAdapter(new MyAdapter(getContext(), mDogPicVOList));
//        refreshRecyclerView();
        return view;
    }

    private void refreshRecyclerView() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "getPwVO");
        jsonObject.addProperty("memNo", memNo);
        new AsyncObjTask(asyncAdapter, jsonObject).execute(Me.PetServlet);
    }

    AsyncAdapter asyncAdapter = new AsyncAdapter() {
        @Override
        public void onFinish(String result) {
            super.onFinish(result);
            JsonObject jsonObject = new Gson().fromJson(result, JsonObject.class);
            String petWallReplyVOs = jsonObject.get("petWallReplyVOs").getAsString();
            List<PwrVO> pwrVOs = PwrVO.decodeToList(petWallReplyVOs);
            String stringMembersVOs = jsonObject.get("membersVOs").getAsString();
            List<MembersVO> membersVOs = MembersVO.decodeToList(stringMembersVOs);

            if (pwrVOs.size() != 0) {
                Context context = view.getContext();
                RecyclerView recyclerView = (RecyclerView) view;
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                recyclerView.setLayoutManager(linearLayoutManager);
//                recyclerView.setAdapter(new MyPwDetailRecyclerViewAdapter(pwrVOs, membersVOs, mListener));
            }
        }
    };


    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MeFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
        mListener = null;
    }

    public List<DogPicVO> getDogPicVOList() {
        List<DogPicVO> zzz = new ArrayList<>();
        zzz.add(new DogPicVO(6, R.drawable.p06));
        zzz.add(new DogPicVO(8, R.drawable.p08));
        zzz.add(new DogPicVO(19, R.drawable.p19));
        zzz.add(new DogPicVO(20, R.drawable.p30));
        return zzz;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }


    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

        private Context context;
        private List<DogPicVO> xxx;

        public MyAdapter(Context context, List<DogPicVO> xxx) {
            this.context = context;
            this.xxx = xxx;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.r_fragment_petwall_s_rowitem, parent, false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            final DogPicVO dp = xxx.get(position);
            Random random = new Random();
//            holder.imageView.setImageResource(xxx.get(random.nextInt(xxx.size())).getImg());

            holder.imageView.setImageResource(xxx.get(position).getImg());
            holder.txView.setText("Title" + "\n" + "Description");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                                                   public void onClick(View v) {
                                                       ImageView imageView = new ImageView(context);
                                                       imageView.setImageResource(dp.getImg());
                                                       Toast toast = new Toast(context);
                                                       toast.setView(imageView);
                                                       toast.setDuration(Toast.LENGTH_SHORT);
//                                                       toast.show();
                                                       Intent intent = new Intent(context, wall_m.class);
                                                       startActivity(intent);
                                                   }
                                               }
            );
        }

        @Override
        public int getItemCount() {
            return xxx.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView txView;

            public MyViewHolder(View itemView) {
                super(itemView);
                imageView = (ImageView) itemView.findViewById(R.id.imgView);
                txView = (TextView) itemView.findViewById(R.id.txView);
                txView.getBackground().setAlpha(75);
            }
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
