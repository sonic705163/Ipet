package idv.randy.member;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.java.iPet.R;
import com.google.gson.JsonObject;

import java.util.List;

import idv.randy.petwall.PwVO;
import idv.randy.ut.AsyncAdapter;
import idv.randy.ut.AsyncObjTask;
import idv.randy.ut.Me;

public class MemberPwFragment extends Fragment {
    private static final String TAG = "MemberPwFragment";
    private int memNo;
    private List<PwVO> PwVOs;
    private View view;


    public MemberPwFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        if (getArguments() != null) {
            memNo = getArguments().getInt("memNo");
            Log.d(TAG, "MemberPwFragment memNo in " + memNo);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        view = inflater.inflate(R.layout.r_fragment_memberpw_list, container, false);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "getMemberPw");
        jsonObject.addProperty("memNo", memNo);
        new AsyncObjTask(getPwAdapter, jsonObject).execute(Me.PetServlet);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    AsyncAdapter getPwAdapter = new AsyncAdapter() {
        @Override
        public void onGoing(int progress) {
            Log.d(TAG, "onGoing: " + progress);
        }

        @Override
        public void onFinish(String result) {
            if (result != null) {
                PwVOs = PwVO.decodeToList(result);
                if (PwVOs.size()>0) {
                    Context context = view.getContext();
                    RecyclerView recyclerView = (RecyclerView) view;
                    recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
                    recyclerView.setAdapter(new MyMemberPwRecyclerViewAdapter(PwVOs));
                }else {
                    Toast.makeText(Me.gc(), "會員的寵物牆是空的", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

}
