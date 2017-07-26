package com.project.internship.employer.fragment;


import android.app.DatePickerDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.project.internship.R;
import com.project.internship.Session;
import com.project.internship.connection.AsyncResponse;
import com.project.internship.connection.BackgroundWorker;
import com.project.internship.connection.Const;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class PostInternship extends Fragment implements AsyncResponse{


    EditText post_company_name,post_title,post_location, post_date,post_details,post_duration;
    Button post_submit;
    Calendar myCalendar;
    Session session;
    Spinner type;
    public PostInternship() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_post_internship, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        session=(Session)getActivity().getApplication();
        myCalendar = Calendar.getInstance();
        type=(Spinner)getView().findViewById(R.id.type);
        post_company_name=(EditText)getView().findViewById(R.id.post_company_name);
        post_title=(EditText)getView().findViewById(R.id.post_title);
        post_location=(EditText)getView().findViewById(R.id.post_location);
        post_details=(EditText)getView().findViewById(R.id.post_details);
        post_date=(EditText)getView().findViewById(R.id.post_date);
        post_duration=(EditText)getView().findViewById(R.id.post_duration);
        post_submit=(Button)getView().findViewById(R.id.post_submit);
        post_company_name.setText(session.getCompanyname());
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Post Internship");
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };
        post_date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                new DatePickerDialog(getContext(), date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                return false;
            }
        });
        post_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(post_company_name.getText().toString().equals("")){
                    post_company_name.setError("Can't Blank");
                    post_company_name.requestFocus();
                }else if(post_title.getText().toString().equals("")){
                    post_title.setError("Can't Blank");
                    post_title.requestFocus();
                }else if(post_location.getText().toString().equals("")){
                    post_location.setError("Can't Blank");
                    post_location.requestFocus();
                }else if(post_details.getText().toString().equals("")){
                    post_details.setError("Can't Blank");
                    post_details.requestFocus();
                }else if(post_date.getText().toString().equals("")){
                    post_date.setError("Can't Blank");
                    post_date.requestFocus();
                }else if(post_duration.getText().toString().equals("")){
                    post_duration.setError("Can't Blank");
                    post_duration.requestFocus();
                }else{
                    final BackgroundWorker backgroundWorker=new BackgroundWorker(getContext());

                    ConnectivityManager connMgr = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        String url= Const.CompanyPost;
                        backgroundWorker.delegate = (AsyncResponse)PostInternship.this;
                        HashMap<String, String> parms = new HashMap<>();
                        parms.put("companyname", post_company_name.getText().toString());
                        parms.put("title", post_title.getText().toString().toUpperCase());
                        parms.put("date", post_date.getText().toString().toUpperCase());
                        parms.put("location", post_location.getText().toString().toUpperCase());
                        parms.put("details", post_details.getText().toString());
                        parms.put("companyemail", session.getEmail());
                        parms.put("type", type.getSelectedItem().toString().toUpperCase());
                        parms.put("duration", post_duration.getText().toString().toUpperCase());
                        backgroundWorker.data(parms);
                        backgroundWorker.execute(url);
                    } else {
                        Toast.makeText(getContext(), "Not Connected to Internet", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });



    }
    private void updateLabel() {

        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        post_date.setText(sdf.format(myCalendar.getTime()));
    }

    @Override
    public void processFinish(String output) {
        post_title.setText("");
        post_location.setText("");
        post_date.setText("");
        post_details.setText("");
        Toast.makeText(getContext(),output,Toast.LENGTH_SHORT).show();
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}
