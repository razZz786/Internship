package com.project.internship.employer.fragment;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.project.internship.R;
import com.project.internship.Session;
import com.project.internship.connection.AsyncResponse;
import com.project.internship.connection.BackgroundWorker;
import com.project.internship.connection.Const;
import com.project.internship.student.fragment.ChangePassword;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployerChangePwd extends Fragment implements AsyncResponse{

    EditText et_old_pwd, et_new_pwd, et_cnew_pwd;
    Button btn;
    Session session;
    public EmployerChangePwd() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employer_change_pwd, container, false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        session=(Session)getActivity().getApplicationContext();
        et_old_pwd = (EditText) getView().findViewById(R.id.old_pwd);
        et_new_pwd = (EditText) getView().findViewById(R.id.new_pwd);
        et_cnew_pwd = (EditText) getView().findViewById(R.id.cnew_pwd);
        btn=(Button)getView().findViewById(R.id.stuUpdatePwd);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_old_pwd.getText().toString().equals(""))
                {
                    et_old_pwd.setError("Cannot Empty");
                    et_old_pwd.requestFocus();
                }
                else if(et_new_pwd.getText().toString().equals(""))
                {
                    et_new_pwd.setError("Cannot Empty");
                    et_new_pwd.requestFocus();
                }
                else if(et_cnew_pwd.getText().toString().equals(""))
                {
                    et_cnew_pwd.setError("Cannot Empty");
                    et_cnew_pwd.requestFocus();
                }
                else if(!et_new_pwd.getText().toString().equals(et_cnew_pwd.getText().toString()))
                {
                    et_new_pwd.setError("Password & Confirm Password Should Be Same");
                    et_cnew_pwd.setError("Password & Confirm Password Should Be Same");
                }
                else
                {
                    final BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
                    ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        String url = Const.EmpChangePwd;
                        backgroundWorker.delegate = (AsyncResponse) EmployerChangePwd.this;
                        HashMap<String, String> parms = new HashMap<>();
                        parms.put("old_pwd", et_old_pwd.getText().toString());
                        parms.put("new_pwd", et_new_pwd.getText().toString());
                        parms.put("email", session.getEmail());
                        backgroundWorker.data(parms);
                        backgroundWorker.execute(url);
                    } else {
                        Toast.makeText(getContext(), "Not Connected to Internet", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void processFinish(String output) {

        if(output.equals("Password updated successfully"))
        {
            et_old_pwd.setText("");
            et_new_pwd.setText("");
            et_cnew_pwd.setText("");
            Toast.makeText(getContext(),"Password updated successfully",Toast.LENGTH_LONG).show();
        }
        else
        {
            et_old_pwd.setError("Invalid Old Password");
        }
    }

}
