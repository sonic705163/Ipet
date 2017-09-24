package idv.randy.petwall;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.java.iPet.R;

public class PwEnterFragment extends Fragment {


    private PwEnterFragmentListener mListener;

    public PwEnterFragment() {
        // Required empty public constructor
    }

    public static idv.randy.petwall.PwEnterFragment newInstance(String param1, String param2) {
        idv.randy.petwall.PwEnterFragment fragment = new idv.randy.petwall.PwEnterFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.r_fragment_pw_enter, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (PwEnterFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface PwEnterFragmentListener {
        void selectEnter();
    }
}
