package idv.randy.member;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.java.iPet.R;

import java.util.List;

import idv.randy.petwall.PwDetailActivity;
import idv.randy.petwall.PwVO;
import idv.randy.ut.AsyncImageTask;
import idv.randy.ut.Me;

public class MyMemberPwRecyclerViewAdapter extends RecyclerView.Adapter<MyMemberPwRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "MyMemberPwRecyclerViewA";
    private final List<PwVO> pwVOs;
    Context mContext;

    public MyMemberPwRecyclerViewAdapter(List<PwVO> items) {
        pwVOs = items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.r_fragment_memberpw, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        PwVO pw = pwVOs.get(position);
        String year = pw.getPwDate().toString().substring(0, 4);
        String month = pw.getPwDate().toString().substring(5, 7);
        String date = pw.getPwDate().toString().substring(8, 10);
        holder.tvPWdate.setText(date);
        holder.tvPwMonth.setText(month + "月");
        holder.tvPwYear.setText(year + "年");

        holder.tvPwContent.setText(pw.getPwContent());
        int pwNo = pw.getPwNo();
        new AsyncImageTask(pwNo, holder.ivPwPicture, R.drawable.empty).execute(Me.PetServlet);

        View.OnClickListener toPwrListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) holder.tvPwContent.getContext();
                PwDetailActivity.start(activity, pwNo, pw.getMemno());
            }
        };
        holder.tvPwContent.setOnClickListener(toPwrListener);
        holder.ivPwPicture.setOnClickListener(toPwrListener);

    }

    @Override
    public int getItemCount() {
        return pwVOs.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView tvPwContent;
        public final ImageView ivPwPicture;
        public final TextView tvPWdate;
        public final TextView tvPwMonth;
        public final TextView tvPwYear;


        public ViewHolder(View view) {
            super(view);
            tvPwContent = (TextView) view.findViewById(R.id.tvPwContent);
            ivPwPicture = (ImageView) view.findViewById((R.id.ivPet));
            tvPWdate = (TextView) view.findViewById(R.id.tvPWdate);
            tvPwMonth = (TextView) view.findViewById(R.id.tvPwMonth);
            tvPwYear = (TextView) view.findViewById(R.id.tvPwYear);
        }
    }
}
