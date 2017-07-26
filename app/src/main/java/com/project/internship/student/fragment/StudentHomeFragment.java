package com.project.internship.student.fragment;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
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
import com.project.internship.employer.customadapter.ContactAdapter;
import com.project.internship.employer.fragment.PostInternship;
import com.project.internship.employer.model.Contacts;
import com.project.internship.student.customadapter.ContactAdapterstudent;
import com.project.internship.student.model.Contactsstudent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class StudentHomeFragment extends Fragment implements AsyncResponse {

    ListView listView;
    SwipeRefreshLayout mSwipeRefreshLayout;
    String Json_String;
    JSONObject jsonObject;
    JSONArray jsonArray;
    Session session;
    EditText search_et;
    Button search_bt;

    public StudentHomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_student_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        session = (Session) getActivity().getApplication();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(session.getSearchtype());

        search_et = (EditText) getView().findViewById(R.id.search_et);
        search_bt = (Button) getView().findViewById(R.id.bt_search);
        search_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
                ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isConnected()) {
                    String url=null;
                    if(session.getSearchtype().equals("Search By Location")){
                        url = Const.SearchByLocation;
                    }else
                    if(session.getSearchtype().equals("Search By Duraction")){
                        url = Const.SearchbyDuriction;
                    }else if(session.getSearchtype().equals("Search By Field")){
                        url = Const.Searchbyfiled;
                    }else {

                        url = Const.Getinternshipbyname;
                    }
                    backgroundWorker.delegate = (AsyncResponse) StudentHomeFragment.this;
                    HashMap<String, String> parms = new HashMap<>();
                    parms.put("name", search_et.getText().toString().toUpperCase());
                    backgroundWorker.data(parms);
                    backgroundWorker.execute(url);
                } else {
                    Toast.makeText(getContext(), "Not Connected to Internet", Toast.LENGTH_SHORT).show();
                }
                //  Toast.makeText(getContext(),,Toast.LENGTH_LONG).show();
            }
        });
        mSwipeRefreshLayout = (SwipeRefreshLayout) getView().findViewById(R.id.swipeToRefresh);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loaddata();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        loaddata();


    }

    public void loaddata() {
        final BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String url = Const.GetInternship;
            backgroundWorker.delegate = (AsyncResponse) StudentHomeFragment.this;
            HashMap<String, String> parms = new HashMap<>();
            parms.put("", "");
            backgroundWorker.data(parms);
            backgroundWorker.execute(url);
        } else {
            Toast.makeText(getContext(), "Not Connected to Internet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void processFinish(String output) {
        if (output.trim().equals("1")) {

        } else {

            session.setStudentlistdata(output);
            listView = (ListView) getView().findViewById(R.id.studenthomelist);
            ContactAdapterstudent contactAdapter = new ContactAdapterstudent(getContext(), R.layout.studenthomeadapter);


            listView.setAdapter(contactAdapter);

            try {
                jsonObject = new JSONObject(output);
                jsonArray = jsonObject.getJSONArray("responce");
                int count = 0;
                String name, address, details, image, description, purpose;
                while (count < jsonArray.length()) {
                    JSONObject jo = jsonArray.getJSONObject(count);
                    Contactsstudent contacts = new Contactsstudent(jo.getString("company_name"), jo.getString("title"), jo.getString("post_date"), jo.getString("location"));
                    contactAdapter.add(contacts);
                    count++;
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }

        }

    }
    @Override
    public void onResume() {
        super.onResume();
    }
}
