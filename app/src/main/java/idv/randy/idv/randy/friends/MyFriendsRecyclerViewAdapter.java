package idv.randy.idv.randy.friends;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.java.iPet.R;
import com.google.gson.JsonObject;

import java.util.List;

import idv.randy.me.MembersVO;
import idv.randy.member.MemberActivity;
import idv.randy.petwall.PwDetailFragment.OnListFragmentInteractionListener;
import idv.randy.petwall.PwrVO;
import idv.randy.ut.AsyncAdapter;
import idv.randy.ut.AsyncImageTask;
import idv.randy.ut.AsyncObjTask;
import idv.randy.ut.Me;


public class MyFriendsRecyclerViewAdapter extends RecyclerView.Adapter<MyFriendsRecyclerViewAdapter.ViewHolder> implements View.OnClickListener {

    private final List<MembersVO> mMembersVOs;
    private final List<String> status;


    public MyFriendsRecyclerViewAdapter(List<MembersVO> mMembersVOs, List<String> status) {
        this.mMembersVOs = mMembersVOs;
        this.status = status;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.r_fragment_friends, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.tvMemID.setText(mMembersVOs.get(position).getMemName());
        int memNo = mMembersVOs.get(position).getMemNo();
        if(status.get(position).equals("N")){
            holder.btnConfirm.setVisibility(View.VISIBLE);
        }
        holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "acceptStatus");
                jsonObject.addProperty("memNo1", FriendsActivity.memNo);
                jsonObject.addProperty("memNo2", memNo);
                new AsyncObjTask(new AsyncAdapter(){
                    @Override
                    public void onFinish(String result) {
                        super.onFinish(result);
                        holder.btnConfirm.setVisibility(View.GONE);
                    }
                }, jsonObject).execute(Me.addFriends);
            }
        });
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
        return mMembersVOs.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvMemID:

        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvMemID;
        public final ImageView ivMemImg;
        public final Button btnConfirm;
        public PwrVO mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            ivMemImg = (ImageView) view.findViewById(R.id.ivMemImg);
            tvMemID = (TextView) view.findViewById(R.id.tvMemID);
            btnConfirm = (Button) view.findViewById(R.id.btnConfirm);

        }


    }
    public interface FriendsListener{
        void accept();
    }
}
