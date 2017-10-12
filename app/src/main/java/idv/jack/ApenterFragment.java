package idv.jack;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.java.iPet.R;


public class ApenterFragment extends Fragment {
    private  ApenterFragmentListener mListener;
    public ApenterFragment() {

    }
    public static ApenterFragment newInstance(String param1, String param2) {
        idv.jack.ApenterFragment fragment = new idv.jack.ApenterFragment();
     return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_apenter, container, false);
        CardView cardViewap = (CardView) view.findViewById(R.id.cardViewap);
        cardViewap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(view.getContext(),ApdotionActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    public interface ApenterFragmentListener {
        void selectEnter();
    }
}
