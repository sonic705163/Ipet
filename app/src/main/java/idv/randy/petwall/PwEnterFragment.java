package idv.randy.petwall;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.java.iPet.R;

public class PwEnterFragment extends Fragment {


    private PwEnterFragmentListener mListener;

    public PwEnterFragment() {
    }

    public static PwEnterFragment newInstance(String param1, String param2) {
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
        FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.btnFab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(view, "Fab clicked", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        CardView cardViewDog = (CardView) view.findViewById(R.id.cardViewDog);
        cardViewDog.setOnClickListener(v -> PwActivity.start(getActivity(), "dog"));
        CardView carViewCat = (CardView) view.findViewById(R.id.carViewCat);
        carViewCat.setOnClickListener(v -> PwActivity.start(getActivity(), "cat"));


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
