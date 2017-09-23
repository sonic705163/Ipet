package idv.randy.me;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.java.iPet.R;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.concurrent.ExecutionException;

import idv.randy.ut.AsyncAdapter;
import idv.randy.ut.AsyncObjTask;
import idv.randy.ut.Me;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment {
    private static final String TAG = "LoginFragment";
    Button btLogin;
    View v;
    JsonObject jsonObject;
    private EditText etID;
    private EditText etPD;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.r_fragment_login, container, false);
        findViews();
        btLogin.setOnClickListener(v -> {
            String id = etID.getText().toString().trim();
            String pd = etPD.getText().toString().trim();
            if (isValid(id, pd)) {
                int no = jsonObject.get("id").getAsInt();
                SharedPreferences.Editor editor = getActivity().getSharedPreferences("UserData", MODE_PRIVATE).edit();
                editor.putString("id", id);
                editor.putString("pd", pd);
                editor.putBoolean("login", true);
                editor.apply();

                FragmentManager fragmentManager = getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.forMainFragment, MeFragment.newInstance(String.valueOf(no), ""), "").commit();

            } else {
                Toast.makeText(Me.gc(), "Account or Password is invalid", Toast.LENGTH_SHORT).show();
            }
        });
        return v;
    }


    private void findViews() {
        etID = (EditText) v.findViewById(R.id.etID);
        etPD = (EditText) v.findViewById(R.id.etPD);
        btLogin = (Button) v.findViewById(R.id.btLogin);
    }

    private boolean isValid(String id, String pd) {
        jsonObject = new JsonObject();
        jsonObject.addProperty("id", id);
        jsonObject.addProperty("pd", pd);
        boolean isValid = false;
        try {
            String s = new AsyncObjTask(new AsyncAdapter(), jsonObject, Me.gc()).execute(Me.MembersServlet).get();
            jsonObject = new Gson().fromJson(s, JsonObject.class);
            isValid = jsonObject.get("param").getAsBoolean();
            JsonArray jsonArray = new Gson().fromJson(s, JsonArray.class);
            


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return isValid;
    }
}
