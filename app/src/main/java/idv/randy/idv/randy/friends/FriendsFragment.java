package idv.randy.idv.randy.friends;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.java.iPet.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import idv.randy.me.MembersVO;
import idv.randy.petwall.PwVO;
import idv.randy.ut.AsyncAdapter;
import idv.randy.ut.AsyncObjTask;
import idv.randy.ut.Me;

public class FriendsFragment extends Fragment {
    private static final String TAG = "FriendsFragment";
    private static final String ARG_COLUMN_COUNT = "column-count";
    private int pwNo;
    private int memNo;
    View view;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pwNo = 10000;
            memNo = getArguments().getInt("memNo");
            Log.d(TAG, "onCreate: " + memNo);
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
//        view = inflater.inflate(R.layout.r_fragment_friend_detail_list, container, false);
        view = inflater.inflate(R.layout.r_fragment_pw_detail_list, container, false);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "getFriendsVO");
        jsonObject.addProperty("memNo", memNo);
        new AsyncObjTask(new AsyncAdapter() {
            @Override
            public void onFinish(String result) {
                super.onFinish(result);

                Gson gson = new Gson();
                JsonObject jsonObject = new Gson().fromJson(result, JsonObject.class);
//
                String stringMembersVOs = jsonObject.get("membersVOs").getAsString();
                List<MembersVO> membersVOs = MembersVO.decodeToList(stringMembersVOs);
                String stringStatus = jsonObject.get("status").getAsString();
                List<String> status = gson.fromJson(stringStatus, new TypeToken<List<String>>() {
                }.getType());
                if (membersVOs.size() != 0) {
                    Context context = view.getContext();
                    RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.list);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(new MyFriendsRecyclerViewAdapter(membersVOs, status));
                } else {
                    Toast.makeText(Me.gc(), "沒有朋友", Toast.LENGTH_SHORT).show();
                }
            }
        }, jsonObject).execute(Me.addFriends);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }


}