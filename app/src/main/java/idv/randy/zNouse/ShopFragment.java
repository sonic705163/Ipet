package idv.randy.zNouse;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.java.iPet.R;

import java.util.ArrayList;
import java.util.List;


public class ShopFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private List<Catalog> catalog;

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public ShopFragment() {
    }


    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        catalog = getCatalogs();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.r_znouse_fragment_shop, container, false);
        RecyclerView rvShop = (RecyclerView) v.findViewById(R.id.rvShop);
        rvShop.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rvShop.setAdapter(new ShopAdapter(getContext(), catalog));
        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
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
        mListener = null;
    }

    List<Catalog> getCatalogs() {
        List<Catalog> catalog = new ArrayList<>();
        catalog.add(new Catalog(R.drawable.fake));
        catalog.add(new Catalog(R.drawable.fake));
        catalog.add(new Catalog(R.drawable.fake));
        catalog.add(new Catalog(R.drawable.fake));
        catalog.add(new Catalog(R.drawable.fake));
        catalog.add(new Catalog(R.drawable.fake));
        catalog.add(new Catalog(R.drawable.fake));
        catalog.add(new Catalog(R.drawable.fake));
        catalog.add(new Catalog(R.drawable.fake));
        catalog.add(new Catalog(R.drawable.fake));
        return catalog;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.MyHolder> {
        private Context context;
        private List<Catalog> catalog;

        public ShopAdapter(Context context, List<Catalog> catalog) {
            this.context = context;
            this.catalog = catalog;
        }


        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inf = LayoutInflater.from(context);
            View v = inf.inflate(R.layout.r_znouse_shop_row_item, parent, false);
            return new MyHolder(v);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            Catalog ca = catalog.get(position);


        }

        @Override
        public int getItemCount() {
            return catalog.size();
        }

        public class MyHolder extends RecyclerView.ViewHolder {
            ImageView imageView;

            public MyHolder(View itemView) {
                super(itemView);
            }
        }
    }
}
