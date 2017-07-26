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
import android.widget.EditText;
import android.widget.Toast;

import com.project.internship.R;
import com.project.internship.Session;
import com.project.internship.connection.AsyncResponse;
import com.project.internship.connection.BackgroundWorker;
import com.project.internship.connection.Const;
import com.project.internship.employer.fragment.EmployerHomeFragment;

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class Enquiry extends Fragment implements AsyncResponse {

    EditText message;
    Button enqsend;
    Session session;

    public Enquiry() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_enquiry, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        session = (Session) getActivity().getApplicationContext();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Message");
        message = (EditText) getView().findViewById(R.id.message);
        enqsend = (Button) getView().findViewById(R.id.enqsend);
        enqsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (message.getText().toString().equals("")) {
                    message.setError("Message can't blank");
                    message.requestFocus();

                }else
                {
                    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

// textView is the TextView view that should display it

                    final BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
                    ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        String url = Const.SendMessage;
                        backgroundWorker.delegate = (AsyncResponse) Enquiry.this;
                        HashMap<String, String> parms = new HashMap<>();
                        parms.put("senderid", session.getEmail());
                        parms.put("reciverid", session.getCompanymessageemail());
                        parms.put("message", message.getText().toString());
                        parms.put("messagedate", currentDateTimeString);
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
