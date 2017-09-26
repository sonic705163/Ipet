package idv.randy.petwall;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.java.iPet.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.List;

import idv.randy.me.MembersVO;
import idv.randy.ut.AsyncAdapter;
import idv.randy.ut.AsyncObjTask;
import idv.randy.ut.Me;

public class PwDetailFragment extends Fragment {
    private static final String TAG = "PwDetailFragment";
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int pwNo;
    private OnListFragmentInteractionListener mListener;

    public PwDetailFragment() {
    }

    public static PwDetailFragment newInstance(int columnCount) {
        PwDetailFragment fragment = new PwDetailFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            pwNo = getArguments().getInt("pwNo");
            Log.d(TAG, "onCreate: " + pwNo);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        View view = inflater.inflate(R.layout.r_fragment_pw_detail_list, container, false);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "getPwrVO");
        jsonObject.addProperty("pwNo", pwNo);
        new AsyncObjTask(new AsyncAdapter() {
            @Override
            public void onFinish(String result) {
                super.onFinish(result);
                JsonObject jsonObject = new Gson().fromJson(result, JsonObject.class);
                String petWallReplyVOs = jsonObject.get("petWallReplyVOs").getAsString();
                List<PwrVO> pwrVOs = PwrVO.decodeToList(petWallReplyVOs);
                String stringMembersVOs = jsonObject.get("membersVOs").getAsString();
                List<MembersVO> membersVOs = MembersVO.decodeToList(stringMembersVOs);
                if (view instanceof RecyclerView) {
                    Context context = view.getContext();
                    RecyclerView recyclerView = (RecyclerView) view;
                    recyclerView.setLayoutManager(new LinearLayoutManager(context));
                    recyclerView.setAdapter(new MyPwDetailRecyclerViewAdapter(pwrVOs, membersVOs, mListener));
                }
            }
        }, jsonObject).execute(Me.PetServlet);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(PwrVO item);
    }
}
