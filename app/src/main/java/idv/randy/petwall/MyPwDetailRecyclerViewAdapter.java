package idv.randy.petwall;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.java.iPet.R;

import java.util.List;

import idv.randy.me.MembersVO;
import idv.randy.petwall.PwDetailFragment.OnListFragmentInteractionListener;
import idv.randy.ut.AsyncImageTask;
import idv.randy.ut.Me;


public class MyPwDetailRecyclerViewAdapter extends RecyclerView.Adapter<MyPwDetailRecyclerViewAdapter.ViewHolder> {

    private final List<PwrVO> mPwrVOs;
    private final List<MembersVO> mMembersVOs;
    private final OnListFragmentInteractionListener mListener;

    public MyPwDetailRecyclerViewAdapter(List<PwrVO> items, List<MembersVO> mMembersVOs, OnListFragmentInteractionListener listener) {
        mPwrVOs = items;
        mListener = listener;
        this.mMembersVOs = mMembersVOs;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_pwdetail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mPwrVOs.get(position);
//        holder.mIdView.setText(mPwrVOs.get(position).);
        holder.tvMemID.setText(mMembersVOs.get(position).getMemName());
        holder.mContentView.setText(mPwrVOs.get(position).getPwrcontent());
        int memNo = mMembersVOs.get(position).getMemNo();
        new AsyncImageTask(memNo, holder.ivMemImg).execute(Me.MembersServlet);
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mPwrVOs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public final TextView tvMemID;
        public final ImageView ivMemImg;
        public PwrVO mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivMemImg = (ImageView) view.findViewById(R.id.ivMemImg);
            tvMemID = (TextView) view.findViewById(R.id.tvMemID);
            mIdView = (TextView) view.findViewById(R.id.pwrContent);
            mContentView = (TextView) view.findViewById(R.id.content);

        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }
    }
}
