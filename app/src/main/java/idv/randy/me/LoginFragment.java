package idv.randy.me;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.java.iPet.R;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;

import java.util.concurrent.ExecutionException;

import idv.randy.ut.AsyncAdapter;
import idv.randy.ut.AsyncObjTask;
import idv.randy.ut.Me;

import static android.content.Context.MODE_PRIVATE;

public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    private Button btLogin;
    private View view;
    private JsonObject jsonObject;
    private Button btSet1;
    private Button btSet2;
    private Button btSet3;
    private Button btnRegister;
    private Integer memNo;
    private String memName;
    private LoginFragmentListener mLoginFragmentListener;
    private EditText etID;
    private EditText etPD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: ");
        view = inflater.inflate(R.layout.r_fragment_login, container, false);
        findViews();
        btSet1.setOnClickListener(v1 -> {
            etID.setText("jack8124");
            etPD.setText("123456");
        });
        btSet2.setOnClickListener(v1 -> {
            etID.setText("j43343");
            etPD.setText("841284");
        });
        btSet3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etID.setText("randy00");
                etPD.setText("123456");
            }
        });
        btLogin.setOnClickListener(v -> {
            String id = etID.getText().toString().trim();
            String pd = etPD.getText().toString().trim();
            if (id.length() == 0 || pd.length() == 0) {
                Toast.makeText(Me.gc(), "帳號或密碼不可以空白", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("UserData", MODE_PRIVATE).edit();
                    if (isValid(id, pd)) {
                        memNo = jsonObject.get("memNo").getAsInt();
//                        editor.putString("id", id);
                        editor.putInt("memNo", memNo);
                        editor.putBoolean("login", true);
//                        editor.putString("memName", memName);
                        editor.apply();
                        mLoginFragmentListener.login();
                    } else {
                        editor.putBoolean("login", false);
                        editor.apply();
                        Toast.makeText(Me.gc(), "帳號或密碼錯誤", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            etPD.setText("");
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLoginFragmentListener.toRegister();
            }
        });
        return view;
    }

    private void findViews() {
        etID = (EditText) view.findViewById(R.id.etID);
        etPD = (EditText) view.findViewById(R.id.etPD);
        btLogin = (Button) view.findViewById(R.id.btLogin);
        btnRegister = (Button) view.findViewById(R.id.btnRegister);
        btSet1 = (Button) view.findViewById(R.id.btSet1);
        btSet2 = (Button) view.findViewById(R.id.btSet2);
        btSet3 = (Button) view.findViewById(R.id.btSet3);
    }

    private boolean isValid(String id, String pd) throws JSONException {
        jsonObject = new JsonObject();
        jsonObject.addProperty("action", "checkValid");
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("pd", pd);
        boolean isValid = false;
        try {
            String inputString = new AsyncObjTask(new AsyncAdapter(), jsonObject).execute(Me.MembersServlet).get();
            jsonObject = new Gson().fromJson(inputString, JsonObject.class);
            isValid = jsonObject.get("isValid").getAsBoolean();
//            memNo = jsonObject.get("memNo").getAsInt();
//            memName = jsonObject.get("memName").getAsString();
//            mLoginFragmentListener.login();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return isValid;
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");
        try {
            mLoginFragmentListener = (LoginFragmentListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
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
    }

    public interface LoginFragmentListener {
        void login();

        void toRegister();
    }
}
