package com.project.internship.employer.fragment;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.project.internship.R;
import com.project.internship.Session;
import com.project.internship.connection.AsyncResponse;
import com.project.internship.connection.BackgroundWorker;
import com.project.internship.connection.Const;
import com.project.internship.employer.customadapter.ContactAdapter;
import com.project.internship.employer.model.Contacts;
import com.project.internship.student.fragment.Enquiry;
import com.project.internship.student.fragment.Inbox;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmployerHomeFragment extends Fragment implements AsyncResponse {

    ListView listView;
    Session session;
    JSONObject jsonObject;
    JSONArray jsonArray;
    boolean load=true;
    int networking=0,mobile=0,ip=0;
    TextView mob,ipa,net,tx_mobile,tx_ip,tx_networking;
  //  SearchView search;

    public EmployerHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employer_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Employer Home");
        listView = (ListView) getView().findViewById(R.id.employerinboxlist);
       // search=(SearchView)getView().findViewById(R.id.search);
        session = (Session) getActivity().getApplicationContext();
        tx_mobile=(TextView)getView().findViewById(R.id.tx_mobile);
        tx_ip=(TextView)getView().findViewById(R.id.tx_ip);
        tx_networking=(TextView)getView().findViewById(R.id.tx_networking);
        mob=(TextView)getView().findViewById(R.id.mob);
        ipa=(TextView)getView().findViewById(R.id.ipa);
        net=(TextView)getView().findViewById(R.id.net);
        tx_mobile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.setApplytype("Applied for MOBILE");
                Fragment fragment = new AppliedInbox();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.newview, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        tx_networking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.setApplytype("Applied for NETWORKING");
                Fragment fragment = new AppliedInbox();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.newview, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        tx_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                session.setApplytype("Applied for IP ADDEESSING");
                Fragment fragment = new AppliedInbox();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.newview, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });
        final BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String url = Const.GetApplyed;
            backgroundWorker.delegate = (AsyncResponse) EmployerHomeFragment.this;
            HashMap<String, String> parms = new HashMap<>();
            parms.put("reciverid", session.getEmail());
            backgroundWorker.data(parms);
            backgroundWorker.execute(url);
        } else {
            Toast.makeText(getContext(), "Not Connected to Internet", Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public void processFinish(String output) {
        if(load){
            load=false;

            try {
                jsonObject = new JSONObject(output);
                jsonArray = jsonObject.getJSONArray("responce");
                int count = 0;
                while (count < jsonArray.length()) {
                JSONObject jo = jsonArray.getJSONObject(count);
              //  Contacts contacts = new Contacts(jo.getString("reciverid"), jo.getString("senderid"), jo.getString("message"), jo.getString("messagedate"));

               if(jo.getString("message").equals("Applied for NETWORKING")){
                   networking=networking+1;
               }else if(jo.getString("message").equals("Applied for MOBILE")){
                   mobile=mobile+1;
               }else{
                   ip=ip+1;
               }
                    count++;
                }
                mob.setText(""+mobile);
                net.setText(""+networking);
                ipa.setText(""+ip);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }


            final BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
            ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                String url = Const.INBOX;
                backgroundWorker.delegate = (AsyncResponse) EmployerHomeFragment.this;
                HashMap<String, String> parms = new HashMap<>();
                parms.put("reciverid", session.getEmail());
                backgroundWorker.data(parms);
                backgroundWorker.execute(url);
            } else {
                Toast.makeText(getContext(), "Not Connected to Internet", Toast.LENGTH_SHORT).show();
            }
        }else {
            session.setInboxoutput(output);
            ContactAdapter contactAdapter = new ContactAdapter(getContext(), R.layout.employerinbox);
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

                    Fragment fragment = new ReadMessage();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.newview, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
