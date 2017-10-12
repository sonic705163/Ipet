//package idv.jack;
//
//
//import android.content.Context;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v7.widget.LinearLayoutManager;
//import android.support.v7.widget.RecyclerView;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.java.iPet.R;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
//import com.google.gson.reflect.TypeToken;
//
//import java.lang.reflect.Type;
//import java.util.List;
//
//import static android.content.Context.MODE_PRIVATE;
//
//public class webmail extends Fragment {
//    private final static String TAG = "webmail";
//    private RecyclerView rlwebmail;
//    List<WebmailVO> webmaillist;
//    private MyTask webmailtask;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
////        View view = inflater.inflate(R.layout.fragment_webmail, container, false);
//        getWebMail();
//        rlwebmail = (RecyclerView) view.findViewById(R.id.rlwebmail);
//        rlwebmail.setLayoutManager(new LinearLayoutManager(getActivity()));
//        return view;
//    }
//
//    private void getWebMail() {
//        SharedPreferences pref = getActivity().getSharedPreferences("UserData" ,MODE_PRIVATE);
//        Integer memNo = pref.getInt("memNo", 0);
//        if (Common.networkConnected(getActivity())) {
//            String url = Common.URL1;
//            JsonObject jsonObject2 = new JsonObject();
//            jsonObject2.addProperty("action", "getAll");
//            jsonObject2.addProperty("memNo", memNo);
//            String jsonOut2 = jsonObject2.toString();
//            webmailtask = new MyTask(url, jsonOut2);
//            try {
//                String jsonIn2 = webmailtask.execute().get();
//                Log.d(TAG, jsonIn2);
//                Gson gson = new Gson();
//                Type listType = new TypeToken<List<WebmailVO>>() {
//                }.getType();
//                webmaillist = gson.fromJson(jsonIn2, listType);
//            } catch (Exception e) {
//                Log.e(TAG, e.toString());
//            }
//
//
//        } else {
//            Common.showToast(getActivity(), "失敗");
//        }
//
//    }
//
//
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        rlwebmail.setAdapter(new webmailSendAdapter(getContext(), webmaillist));
//    }
//
//    private class webmailSendAdapter extends
//            RecyclerView.Adapter<webmailSendAdapter.MyViewHolder> {
//        private Context context;
//        private List<WebmailVO> webmaillist;
//
//        webmailSendAdapter(Context context, List<WebmailVO> webmaillist) {
//            this.context = context;
//            this.webmaillist = webmaillist;
//        }
//
//        class MyViewHolder extends RecyclerView.ViewHolder {
//            TextView tvMessageTitle, tvWhoSend, tvSendDate, tvWhoStateList;
//
//            MyViewHolder(View itemView) {
//                super(itemView);
////                tvMessageTitle = (TextView) itemView.findViewById(R.id.tvMessageTitle);
//                tvWhoSend = (TextView) itemView.findViewById(R.id.tvWhoSend);
////                tvSendDate = (TextView) itemView.findViewById(R.id.tvSendDate);
////                tvWhoStateList = (TextView) itemView.findViewById(R.id.tvWhoStateList);
//            }
//        }
//
//        @Override
//        public int getItemCount() {
//            return webmaillist.size();
//        }
//
//        @Override
//        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//            LayoutInflater layoutInflater = LayoutInflater.from(context);
//            View itemView = layoutInflater.inflate(R.layout.fragment_webmail_list, viewGroup, false);
//            return new MyViewHolder(itemView);
//        }
//
//        @Override
//        public void onBindViewHolder(MyViewHolder viewHolder, int position) {
//            final WebmailVO webmailvo = webmaillist.get(position);
////            viewHolder.tvMessageTitle.setTextColor(getResources().getColor(R.color.colorGray));
////            viewHolder.tvMessageTitle.setText(messageVO);
//            viewHolder.tvWhoSend.setText(String.valueOf(webmailvo.getTomemNo()));
////            viewHolder.tvSendDate.setText(webmailvo.getGetmemNo());
////            viewHolder.tvWhoStateList.setText(R.string.receiveMb);
//
////            viewHolder.itemView.setOnClickListener(new View.OnClickListener(){
////                @Override
////                public void onClick(View view) {
////                    Fragment messageDetailFragment = new MessageDetailFragment();
////                    Bundle bundle
////            //Onclick= new Bundle();
////                    bundle.putSerializable("messageVO", messageVO);
////                    bundle.putString("State","Send");
////                    messageDetailFragment.setArguments(bundle);
////                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
////                    fragmentTransaction.replace(R.id.flThirdFragment, messageDetailFragment);
////                    fragmentTransaction.addToBackStack(null);
////                    fragmentTransaction.commit();
////                }
////            });
////
////            viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
////                @Override
////                public boolean onLongClick(View view) {
////                    PopupMenu popupMenu = new PopupMenu(getActivity(), view, Gravity.END);
////                    popupMenu.inflate(R.menu.menu_longclick);
////                    popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
////                        @Override
////                        public boolean onMenuItemClick(MenuItem item) {
////                            switch (item.getItemId()){
////                                case R.id.delete:
////                                    Integer count = deleteMessage(messageVO.getMesgNo());
////                                    getMessages();
////                                    messageSendRV.setAdapter(new MessageSendAdapter(getContext(), messageSendList));
////                                    Toast.makeText(getActivity(), "已刪除"+count+"筆信件", Toast.LENGTH_SHORT).show();
////                                    break;
////                            }
////                            return true;
////                        }
////                    });
////                    popupMenu.show();
////                    return true;
////                }
////            });
//        }
//    }
//    }
//
