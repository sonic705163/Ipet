package idv.randy.petwall;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.java.iPet.R;

import java.util.List;

import idv.randy.me.MembersVO;
import idv.randy.member.MemberActivity;
import idv.randy.petwall.PwDetailFragment.OnListFragmentInteractionListener;
import idv.randy.ut.AsyncImageTask;
import idv.randy.ut.Me;


public class MyPwDetailRecyclerViewAdapter extends RecyclerView.Adapter<MyPwDetailRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private final List<PwrVO> mPwrVOs;
    private final List<MembersVO> mMembersVOs;
    private final OnListFragmentInteractionListener mListener;

    public MyPwDetailRecyclerViewAdapter(List<PwrVO> mPwrVOs, List<MembersVO> mMembersVOs, OnListFragmentInteractionListener listener) {
        this.mPwrVOs = mPwrVOs;
        mListener = listener;
        this.mMembersVOs = mMembersVOs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.r_fragment_pw_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mPwrVOs.get(position);
        holder.tvMemID.setText(mMembersVOs.get(position).getMemName());
        holder.tvPwrContent.setText(mPwrVOs.get(position).getPwrcontent());
        int memNo = mMembersVOs.get(position).getMemNo();
        new AsyncImageTask(memNo, holder.ivMemImg, R.drawable.person).execute(Me.MembersServlet);

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) holder.tvMemID.getContext();
                MemberActivity.start(activity, memNo);
            }
        };
        holder.tvMemID.setOnClickListener(onClickListener);
        holder.ivMemImg.setOnClickListener(onClickListener);
    }

    @Override
    public int getItemCount() {
        return mPwrVOs.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvMemID:

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvPwrContent;
        public final TextView tvMemID;
        public final ImageView ivMemImg;
        public PwrVO mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivMemImg = (ImageView) view.findViewById(R.id.ivMemImg);
            tvMemID = (TextView) view.findViewById(R.id.tvMemID);
            tvPwrContent = (TextView) view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + tvPwrContent.getText() + "'";
        }
    }
}
