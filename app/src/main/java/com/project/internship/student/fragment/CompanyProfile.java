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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.project.internship.R;
import com.project.internship.Session;
import com.project.internship.connection.AsyncResponse;
import com.project.internship.connection.BackgroundWorker;
import com.project.internship.connection.Const;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyProfile extends Fragment implements AsyncResponse{

    Session session;
    JSONObject jsonObject;
    JSONArray jsonArray;
    TextView address,details;
    ImageView logo;
    ProgressBar pbar;

    public CompanyProfile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_company_profile, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        session=(Session)getActivity().getApplicationContext();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Company Profile");
        address=(TextView)getView().findViewById(R.id.address);
        details=(TextView)getView().findViewById(R.id.details);
        logo=(ImageView)getView().findViewById(R.id.logo);
        pbar=(ProgressBar)getView().findViewById(R.id.pbar);
        final BackgroundWorker backgroundWorker = new BackgroundWorker(getContext());
        ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            String url = Const.GetCompanyProfile;
            backgroundWorker.delegate = (AsyncResponse) CompanyProfile.this;
            HashMap<String, String> parms = new HashMap<>();
            parms.put("companyname",session.getCompanyname() );
            backgroundWorker.data(parms);
            backgroundWorker.execute(url);
        } else {
            Toast.makeText(getContext(), "Not Connected to Internet", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void processFinish(String output) {

        try {
            jsonObject = new JSONObject(output);
            jsonArray = jsonObject.getJSONArray("responce");
            JSONObject jo = jsonArray.getJSONObject(0);
            address.setText("Address:- "+jo.getString("ADDRESS"));
            details.setText("DESCRIPTION:- "+jo.getString("DESCRIPTION"));
            Picasso.with(getContext()).load(Const.image+jo.getString("LOGO"))
                    .resize(300, 200)
                    .into(logo, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            if (pbar != null) {
                                pbar.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError() {

                        }
                    });


        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }


    }
    @Override
    public void onResume() {
        super.onResume();
    }
}
