package idv.randy.me;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.java.iPet.R;
import com.google.gson.JsonObject;

import idv.randy.ut.AsyncAdapter;
import idv.randy.ut.AsyncImageTask;
import idv.randy.ut.AsyncObjTask;
import idv.randy.ut.Me;

import static android.content.Context.MODE_PRIVATE;


public class MeFragment extends Fragment {
    private static final String TAG = "MeFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String id;
    private String mParam1;
    private String mParam2;
    private TextView tvMemName;
    private TextView tvMemID;
    private MeFragmentListener mListener;
    private String memName;
    private ImageView ivMemImg;
    private ImageView ivLogOut;
    int memNo;
    private View v;
    MembersVO membersVO;
    JsonObject jsonObject;

    public MeFragment() {
    }

    AsyncAdapter asyncAdapeter = new AsyncAdapter() {
        @Override
        public void onFinish(String result) {
            super.onFinish(result);
            membersVO = MembersVO.decodeToVO(result);
            tvMemName.setText(membersVO.getMemName());
            tvMemID.setText(membersVO.getMenId());
        }
    };

    public static MeFragment newInstance(String param1, String param2) {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
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
        if (context instanceof MeFragmentListener) {
            mListener = (MeFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement MeFragmentListener");
        }

        SharedPreferences pref = getActivity().getSharedPreferences("UserData", MODE_PRIVATE);
        memNo = pref.getInt("memNo", 1);
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
        Log.d(TAG, "onCreateView: ");
        v = inflater.inflate(R.layout.r_fragment_me, container, false);
        findViews();
        new AsyncImageTask(memNo, ivMemImg).execute(Me.MembersServlet);
        jsonObject = new JsonObject();
        jsonObject.addProperty("action", "getVO");
        jsonObject.addProperty("memNo", memNo);
        new AsyncObjTask(asyncAdapeter, jsonObject).execute(Me.MembersServlet);
        ivLogOut.setOnClickListener(v1 -> {
            SharedPreferences.Editor editor = getActivity().getSharedPreferences("UserData", MODE_PRIVATE).edit();
            editor.putBoolean("login", false);
            editor.putString("memName", "none");
            editor.apply();
            mListener.logOut();
        });

        return v;
    }

    private void findViews() {
        ivLogOut = (ImageView) v.findViewById(R.id.ivLogOut);
        ivMemImg = (ImageView) v.findViewById(R.id.ivMemImg);
        tvMemName = (TextView) v.findViewById(R.id.tvMemName);
        tvMemID = (TextView) v.findViewById(R.id.tvMemId);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
        mListener = null;
    }

    public interface MeFragmentListener {
        void onFragmentInteraction(Uri uri);

        void logOut();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
        } else {
        }
    }


}
