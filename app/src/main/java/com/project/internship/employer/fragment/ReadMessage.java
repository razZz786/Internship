package com.project.internship.employer.fragment;


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
import com.project.internship.employer.model.Contacts;
import com.project.internship.student.fragment.Enquiry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReadMessage extends Fragment {
    Session session;
    TextView readsender,readmessage,readdate;
    Button replay;
    JSONObject jsonObject;
    JSONArray jsonArray;
    public ReadMessage() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_read_message, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        session=(Session)getActivity().getApplicationContext();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Read Message");
        readsender=(TextView)getView().findViewById(R.id.readsender);
        readmessage=(TextView)getView().findViewById(R.id.readmessage);
        readdate=(TextView)getView().findViewById(R.id.readdate);
        replay=(Button)getView().findViewById(R.id.readreplay);
        try {
            jsonObject = new JSONObject(session.getInboxoutput());
            jsonArray = jsonObject.getJSONArray("responce");
            JSONObject jo = jsonArray.getJSONObject(session.getInboxpos());
                Contacts contacts = new Contacts(jo.getString("reciverid"), jo.getString("senderid"), jo.getString("message"), jo.getString("messagedate"));
            readsender.setText("From:- "+jo.getString("senderid"));
            readmessage.setText("Message:-  \n"+ jo.getString("message"));
            readdate.setText("Dated:-"+jo.getString("messagedate"));
            session.setCompanymessageemail(jo.getString("senderid"));
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        replay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(session.getInboxtype().equals("student")){
                    Fragment fragment = new Enquiry();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.studentframe, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else {
                    Fragment fragment = new Enquiry();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.newview, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            }
        });



    }
    @Override
    public void onResume() {
        super.onResume();
    }
}
