package idv.randy.me;

import android.content.Context;
import android.content.Intent;
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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.java.iPet.R;
import com.google.gson.JsonObject;

import idv.jack.MailReceive;
import idv.randy.member.MemberPwActivity;
import idv.randy.ut.AsyncAdapter;
import idv.randy.ut.AsyncImageTask;
import idv.randy.ut.AsyncObjTask;
import idv.randy.ut.Me;

import static android.content.Context.MODE_PRIVATE;


public class MeFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "MeFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private int memNo;
    private MembersVO membersVO;
    private JsonObject jsonObject;
    private LinearLayout llFeedback;
    private LinearLayout llMyPw;
    private LinearLayout llLogOut;
    private String mParam1;
    private String mParam2;
    private TextView tvMemName;
    private TextView tvMemID;
    private AsyncAdapter asyncAdapeter = new AsyncAdapter() {
        @Override
        public void onFinish(String result) {
            super.onFinish(result);
            membersVO = MembersVO.decodeToVO(result);
            tvMemName.setText(membersVO.getMemName());
            tvMemID.setText(membersVO.getMenId());
        }
    };
    private ImageView ivMemImg, ivmail;
    private MeFragmentListener mListener;
    private View view;

    public MeFragment() {
    }

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
            throw new RuntimeException(context.toString());
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
        view = inflater.inflate(R.layout.r_fragment_me, container, false);
        findViews();
        new AsyncImageTask(memNo, ivMemImg, R.drawable.person).execute(Me.MembersServlet);
        new AsyncObjTask(asyncAdapeter, getMemVO()).execute(Me.MembersServlet);
        llFeedback.setOnClickListener(this);
        llMyPw.setOnClickListener(this);
        llLogOut.setOnClickListener(this);
        ivmail.setOnClickListener(this);
        return view;
    }

    private JsonObject getMemVO() {
        jsonObject = new JsonObject();
        jsonObject.addProperty("action", "getVO");
        jsonObject.addProperty("memNo", memNo);
        return jsonObject;
    }

    private void findViews() {
        ivMemImg = (ImageView) view.findViewById(R.id.ivMemImg);
        tvMemName = (TextView) view.findViewById(R.id.tvMemName);
        tvMemID = (TextView) view.findViewById(R.id.tvMemId);
        llFeedback = (LinearLayout) view.findViewById(R.id.llFeedback);
        llMyPw = (LinearLayout) view.findViewById(R.id.llMyPw);
        llLogOut = (LinearLayout) view.findViewById(R.id.llLogOut);
        ivmail = (ImageView) view.findViewById(R.id.ivmail);
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

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
        } else {
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.llFeedback:
                Intent intent = new Intent(getActivity(), FeedbackActivity.class);
                startActivity(intent);
                break;
            case R.id.ivmail:
                Intent intent1 = new Intent(getActivity(), MailReceive.class);
                startActivity(intent1);
                break;
            case R.id.llMyPw:
                MemberPwActivity.start(getActivity(), memNo);
                break;
            case R.id.llLogOut:
                Log.d(TAG, "onClick: llLogOut");
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("UserData", MODE_PRIVATE).edit();
                editor.putBoolean("login", false);
                editor.putString("memName", "none");
                editor.apply();
                mListener.logOut();
                break;
            default:
                break;
        }
    }

    public interface MeFragmentListener {
        void onFragmentInteraction(Uri uri);
        void logOut();
    }

}
