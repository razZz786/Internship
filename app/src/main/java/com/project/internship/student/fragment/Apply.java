package com.project.internship.student.fragment;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.project.internship.R;
import com.project.internship.Session;
import com.project.internship.connection.AsyncResponse;
import com.project.internship.connection.BackgroundWorker;
import com.project.internship.connection.Const;
import com.project.internship.employer.fragment.EmployerHomeFragment;
import com.project.internship.student.model.Contactsstudent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class Apply extends Fragment implements AsyncResponse {

    JSONObject jsonObject;
    JSONArray jsonArray;
    Session session;
    TextView cname, ctitle, cdate, clocation, cdetails,ctype,cduriction;
    Button enquiry,apply;
    String type="";

    public Apply() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_apply, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        session = (Session) getActivity().getApplication();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Apply");
        apply=(Button)getView().findViewById(R.id.apply);
        ctype = (TextView) getView().findViewById(R.id.ctype);
        cduriction = (TextView) getView().findViewById(R.id.cduriction);
        cname = (TextView) getView().findViewById(R.id.cname);
        ctitle = (TextView) getView().findViewById(R.id.ctitle);
        cdate = (TextView) getView().findViewById(R.id.cdate);
        clocation = (TextView) getView().findViewById(R.id.clocation);
        cdetails = (TextView) getView().findViewById(R.id.cdetails);
        enquiry=(Button)getView().findViewById(R.id.enquiry);
        try {
            jsonObject = new JSONObject(session.getStudentlistdata());
            jsonArray = jsonObject.getJSONArray("responce");
            JSONObject jo = jsonArray.getJSONObject(session.getApplyposition());
            cname.setText(jo.getString("company_name"));
            ctitle.setText(jo.getString("title"));
            session.setCompanymessageemail(jo.getString("companyemail"));
            cdate.setText("Last Date :- "+jo.getString("post_date"));
            clocation.setText("Location :- "+jo.getString("location"));
            type=jo.getString("type");
            ctype.setText("Type :- "+jo.getString("type"));
            cduriction.setText("Duration :- "+jo.getString("duration"));
            cdetails.setText(jo.getString("details"));
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }

        enquiry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Fragment fragment = new Enquiry();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.studentframe, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        cname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.setCompanyname(cname.getText().toString());
                //Toast.makeText(getContext(),cname.getText().toString(),Toast.LENGTH_LONG).show();
                Fragment fragment = new CompanyProfile();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.studentframe, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
                final BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
                ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    String url = Const.Apply;
                    backgroundWorker.delegate = (AsyncResponse) Apply.this;
                    HashMap<String, String> parms = new HashMap<>();
                    parms.put("senderid", session.getEmail());
                    parms.put("reciverid", session.getCompanymessageemail());
                    parms.put("message", "Applied for "+type);
                    parms.put("messagedate", currentDateTimeString);
                    backgroundWorker.data(parms);
                    backgroundWorker.execute(url);
                } else {
                    Toast.makeText(getContext(), "Not Connected to Internet", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void processFinish(String output) {
        Toast.makeText(getContext(),output,Toast.LENGTH_LONG).show();
        if(session.getInboxtype().equals("student")){
            Fragment fragment = new StudentHomeFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.studentframe, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else {
            Fragment fragment = new EmployerHomeFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.newview, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}
