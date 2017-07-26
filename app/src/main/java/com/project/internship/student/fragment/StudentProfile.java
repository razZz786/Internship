package com.project.internship.student.fragment;


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
import android.widget.TextView;
import android.widget.Toast;

import com.project.internship.R;
import com.project.internship.Session;
import com.project.internship.connection.AsyncResponse;
import com.project.internship.connection.BackgroundWorker;
import com.project.internship.connection.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentProfile extends Fragment implements AsyncResponse {

    TextView et_name,et_dob,ed_cgpa,et_student_id,ed_course;
    EditText et_contact_number,et_email,et_address;
    Button bt_mobile,bt_upemail,bt_upaddress;
    Session session;

    public StudentProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        session=(Session)getActivity().getApplicationContext();
        et_name=(TextView)getView().findViewById(R.id.et_name);
        et_dob=(TextView)getView().findViewById(R.id.et_dob);
        ed_cgpa=(TextView)getView().findViewById(R.id.ed_cgpa);
        et_student_id=(TextView)getView().findViewById(R.id.et_student_id);
        ed_course=(TextView)getView().findViewById(R.id.ed_course);
        et_contact_number=(EditText)getView().findViewById(R.id.et_contact_number);
        et_email=(EditText)getView().findViewById(R.id.et_email);
        et_address=(EditText)getView().findViewById(R.id.et_address);
        bt_mobile=(Button)getView().findViewById(R.id.bt_mobile);
        JSONObject jsonObject;
        try {
            jsonObject=new JSONObject(session.getLoginresult());

            et_name.setText(jsonObject.getString("fname").toUpperCase()+" "+jsonObject.getString("lname").toUpperCase());
            et_dob.setText(jsonObject.getString("dob"));
            ed_cgpa.setText(jsonObject.getString("cgpa"));
            et_student_id.setText(jsonObject.getString("student_id"));
            ed_course.setText(jsonObject.getString("course"));
            et_contact_number.setText(jsonObject.getString("contact_no"));
            et_email.setText(jsonObject.getString("email"));
            et_address.setText(jsonObject.getString("address"));


        } catch (JSONException e) {
            e.printStackTrace();
        }

        bt_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(et_address.getText().toString().trim().equals(""))
                {
                    et_address.setError("Cannot Be Empty");
                }
                else if(et_email.getText().toString().trim().equals(""))
                {
                    et_email.setError("Cannot Be Empty");
                }
                else if(et_contact_number.getText().toString().trim().equals(""))
                {
                    et_contact_number.setError("Cannot Be Empty");
                }
                else
                {
                    final BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
                    ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        String url = Const.UpdateProfile;
                        backgroundWorker.delegate = (AsyncResponse) StudentProfile.this;
                        HashMap<String, String> parms = new HashMap<>();
                        parms.put("address", et_address.getText().toString());
                        parms.put("email", et_email.getText().toString());
                        parms.put("contact_number", et_contact_number.getText().toString());
                        parms.put("student_id", et_student_id.getText().toString());
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
        if(output.equals("updation failed"))
        {
            Toast.makeText(getContext(), "updation failed",Toast.LENGTH_LONG).show();
        }
        else
        {
            session.setLoginresult(output);
        }
    }
}
