package idv.randy.petwall;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import idv.randy.zNouse.DogPicVO;
import idv.randy.zNouse.wall_m;

public class PetWallFragmentS extends Fragment {
    private static final String TAG = "PetWallFragmentS";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public List<DogPicVO> mDogPicVOList = getDogPicVOList();
    private RecyclerView rcView;
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public PetWallFragmentS() {
    }


    public static PetWallFragmentS newInstance(String param1, String param2) {
        PetWallFragmentS fragment = new PetWallFragmentS();
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
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.r_fragment_petwall_s, container, false);
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "Fab clicked", Toast.LENGTH_LONG).show();
            }
        });
        RecyclerView rcView = (RecyclerView) view.findViewById(R.id.rcView);


        rcView.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        rcView.setAdapter(new MyAdapter(getContext(), mDogPicVOList));

        return view;
    }

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
        zzz.add(new DogPicVO(1, R.drawable.p01));
        zzz.add(new DogPicVO(2, R.drawable.p02));
        zzz.add(new DogPicVO(3, R.drawable.p03));
        zzz.add(new DogPicVO(4, R.drawable.p04));
        zzz.add(new DogPicVO(5, R.drawable.p05));
        zzz.add(new DogPicVO(6, R.drawable.p06));
        zzz.add(new DogPicVO(7, R.drawable.p07));
        zzz.add(new DogPicVO(8, R.drawable.p08));
        zzz.add(new DogPicVO(9, R.drawable.p09));
        zzz.add(new DogPicVO(10, R.drawable.p10));
        zzz.add(new DogPicVO(11, R.drawable.p11));
        zzz.add(new DogPicVO(12, R.drawable.p12));
        zzz.add(new DogPicVO(13, R.drawable.p13));
        zzz.add(new DogPicVO(14, R.drawable.p14));
        zzz.add(new DogPicVO(15, R.drawable.p15));
        zzz.add(new DogPicVO(16, R.drawable.p16));
        zzz.add(new DogPicVO(17, R.drawable.p17));
        zzz.add(new DogPicVO(18, R.drawable.p18));
        zzz.add(new DogPicVO(19, R.drawable.p19));
        zzz.add(new DogPicVO(20, R.drawable.p20));
        zzz.add(new DogPicVO(20, R.drawable.p21));
        zzz.add(new DogPicVO(20, R.drawable.p22));
        zzz.add(new DogPicVO(20, R.drawable.p23));
        zzz.add(new DogPicVO(20, R.drawable.p24));
        zzz.add(new DogPicVO(20, R.drawable.p25));
        zzz.add(new DogPicVO(20, R.drawable.p26));
        zzz.add(new DogPicVO(20, R.drawable.p27));
        zzz.add(new DogPicVO(20, R.drawable.p28));
        zzz.add(new DogPicVO(20, R.drawable.p29));
        zzz.add(new DogPicVO(20, R.drawable.p30));
        zzz.add(new DogPicVO(20, R.drawable.p31));
        zzz.add(new DogPicVO(20, R.drawable.p32));
        zzz.add(new DogPicVO(20, R.drawable.p33));
        zzz.add(new DogPicVO(20, R.drawable.p34));
        zzz.add(new DogPicVO(20, R.drawable.p35));


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
