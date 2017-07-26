package com.project.internship.student.fragment;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.project.internship.R;
import com.project.internship.Session;
import com.project.internship.connection.AsyncResponse;
import com.project.internship.connection.BackgroundWorker;
import com.project.internship.connection.Const;
import com.project.internship.employer.customadapter.ContactAdapter;
import com.project.internship.employer.model.Contacts;
import com.project.internship.student.model.Contactsstudent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class Outbox extends Fragment implements AsyncResponse {

    ListView listView;
    Session session;
    JSONObject jsonObject;
    JSONArray jsonArray;

    public Outbox() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_outbox, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        listView = (ListView) getView().findViewById(R.id.studetoutbox);
        session = (Session) getActivity().getApplicationContext();
        if (session.getOutboxtype().equals("Applied Internships")) {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Applied Internships");
            final BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
            ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                String url = Const.AppliedInternship;
                backgroundWorker.delegate = (AsyncResponse) Outbox.this;
                HashMap<String, String> parms = new HashMap<>();
                parms.put("senderid", session.getEmail());
                backgroundWorker.data(parms);
                backgroundWorker.execute(url);
            } else {
                Toast.makeText(getContext(), "Not Connected to Internet", Toast.LENGTH_SHORT).show();
            }
        } else {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Outbox");
            final BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
            ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                String url = Const.Outbox;
                backgroundWorker.delegate = (AsyncResponse) Outbox.this;
                HashMap<String, String> parms = new HashMap<>();
                parms.put("senderid", session.getEmail());
                backgroundWorker.data(parms);
                backgroundWorker.execute(url);
            } else {
                Toast.makeText(getContext(), "Not Connected to Internet", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void processFinish(String output) {
        ContactAdapter contactAdapter = new ContactAdapter(getContext(), R.layout.employerinbox);
        try {
            jsonObject = new JSONObject(output);
            jsonArray = jsonObject.getJSONArray("responce");
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject jo = jsonArray.getJSONObject(count);
                Contacts contacts = new Contacts(jo.getString("senderid"), jo.getString("reciverid"), jo.getString("message"), jo.getString("messagedate"));
                contactAdapter.add(contacts);
                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        listView.setAdapter(contactAdapter);
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}
