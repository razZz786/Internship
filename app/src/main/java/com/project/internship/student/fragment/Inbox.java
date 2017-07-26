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
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.project.internship.R;
import com.project.internship.Session;
import com.project.internship.connection.AsyncResponse;
import com.project.internship.connection.BackgroundWorker;
import com.project.internship.connection.Const;
import com.project.internship.employer.customadapter.ContactAdapter;
import com.project.internship.employer.fragment.ReadMessage;
import com.project.internship.employer.model.Contacts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class Inbox extends Fragment implements AsyncResponse {

    Session session;
    JSONObject jsonObject;
    JSONArray jsonArray;

    ListView listView;
    public Inbox() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_inbox, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        session=(Session)getActivity().getApplicationContext();
        listView=(ListView)getView().findViewById(R.id.studentinbox);
        if(session.getInboxtypeemp().equals("emp")){
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Applied Internships");
            final BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
            ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                String url = Const.GetApplyed;
                backgroundWorker.delegate = (AsyncResponse) Inbox.this;
                HashMap<String, String> parms = new HashMap<>();
                parms.put("reciverid", session.getEmail());
                backgroundWorker.data(parms);
                backgroundWorker.execute(url);
            } else {
                Toast.makeText(getContext(), "Not Connected to Internet", Toast.LENGTH_SHORT).show();
            }
        }else {
            ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Inbox");
            final BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
            ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                String url = Const.INBOX;
                backgroundWorker.delegate = (AsyncResponse) Inbox.this;
                HashMap<String, String> parms = new HashMap<>();
                parms.put("reciverid", session.getEmail());
                backgroundWorker.data(parms);
                backgroundWorker.execute(url);
            } else {
                Toast.makeText(getContext(), "Not Connected to Internet", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void processFinish(String output) {
        session.setInboxoutput(output);
        ContactAdapter contactAdapter=new ContactAdapter(getContext(),R.layout.employerinbox);
        try {
            jsonObject = new JSONObject(output);
            jsonArray = jsonObject.getJSONArray("responce");
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject jo = jsonArray.getJSONObject(count);
                Contacts contacts = new Contacts(jo.getString("reciverid"), jo.getString("senderid"), jo.getString("message"), jo.getString("messagedate"));
                contactAdapter.add(contacts);
                count++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        listView.setAdapter(contactAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                session.setInboxpos(i);
                if(session.getInboxtypeemp().equals("emp")){
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Applied Internship");
                    Fragment fragment = new ReadMessage();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.newview, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }else {
                    ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("INBOX");
                    Fragment fragment = new ReadMessage();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.studentframe, fragment);
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
