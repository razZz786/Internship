package com.project.internship.employer.fragment;


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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.project.internship.R;
import com.project.internship.Session;
import com.project.internship.connection.AsyncResponse;
import com.project.internship.connection.BackgroundWorker;
import com.project.internship.connection.Const;
import com.project.internship.employer.customadapter.SearchNameAdapter;
import com.project.internship.employer.model.Contacts;
import com.project.internship.employer.model.SearchNameContacts;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentIdFragment extends Fragment implements AsyncResponse{

    ListView listView;
    JSONObject jsonObject;
    JSONArray jsonArray;
    EditText search_et;
    Button bt_search;
    Session session;
    public StudentIdFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_id, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        session=(Session)getActivity().getApplicationContext();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Student Search");
        listView=(ListView)getView().findViewById(R.id.serachid);
        bt_search=(Button)getView().findViewById(R.id.bt_search);
        search_et=(EditText)getView().findViewById(R.id.search_et);
        bt_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url ="";
                final BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
                ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    if(session.getCompanysearchtype().equals("name")) {
                         url=Const.SearchStudentByName;
                    }else  if(session.getCompanysearchtype().equals("id")) {
                        url=Const.SearchByid;
                    }else  if(session.getCompanysearchtype().equals("cgpa")) {
                        url=Const.SearchBycgpa;
                    }else {
                        url=Const.SearchBycourse;
                    }
                        backgroundWorker.delegate = (AsyncResponse) StudentIdFragment.this;
                    HashMap<String, String> parms = new HashMap<>();
                    parms.put("data", search_et.getText().toString());
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
         SearchNameAdapter contactAdapter=new SearchNameAdapter(getContext(),R.layout.namesearchadapter);


        try {
            jsonObject = new JSONObject(output);
            jsonArray = jsonObject.getJSONArray("responce");
            int count = 0;
            while (count < jsonArray.length()) {
                JSONObject jo = jsonArray.getJSONObject(count);
                SearchNameContacts contacts = new SearchNameContacts(jo.getString("fname").toUpperCase()+" "+jo.getString("lname").toUpperCase(), "D.O.B "+jo.getString("dob"),jo.getString("student_id"), jo.getString("contact_no"), jo.getString("email"),jo.getString("address"),jo.getString("cgpa"),output);
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
