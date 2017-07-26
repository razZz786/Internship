package com.project.internship.employer.view;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.project.internship.R;
import com.project.internship.Session;
import com.project.internship.View.Login;
import com.project.internship.connection.AsyncResponse;
import com.project.internship.connection.BackgroundWorker;
import com.project.internship.connection.Const;

import java.util.HashMap;

public class EmployeSignUp extends AppCompatActivity implements AsyncResponse {

    Context ctx=this;
    Session session;
    EditText comanyname,fname,lname,address,email,contact,password,cpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employe_sign_up);
        session=(Session)getApplicationContext();
        initializeIds();
    }

    public void initializeIds()
    {
        comanyname=(EditText) findViewById(R.id.comanyname);
        fname=(EditText) findViewById(R.id.fname);
        lname=(EditText) findViewById(R.id.lname);
        address=(EditText) findViewById(R.id.address);
        email=(EditText) findViewById(R.id.email);
        contact=(EditText) findViewById(R.id.contact);
        password=(EditText) findViewById(R.id.password);
        cpassword=(EditText)findViewById(R.id.cpassword);
        if(session.getSocialemail().equals("")){

        }else {
            email.setText(session.getSocialemail());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        session.setSocialid("");
        session.setSocialemail("");
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

    public void registerCompany(View view)
    {
        if(comanyname.getText().toString().equals("")){
            comanyname.setError("Can't Blank");
            comanyname.requestFocus();
        }else  if(fname.getText().toString().equals("")){
            fname.setError("Can't Blank");
            fname.requestFocus();
        }else if(lname.getText().toString().equals("")){
            lname.setError("Can't Blank");
            lname.requestFocus();
        }else if(address.getText().toString().equals("")){
            address.setError("Can't Blank");
            address.requestFocus();
        }else if(email.getText().toString().equals("")){
            email.setError("Can't Blank");
            email.requestFocus();
        }else if(contact.getText().toString().equals("")){
            contact.setError("Can't Blank");
            contact.requestFocus();
        }else if(password.getText().toString().equals("")){
            password.setError("Can't Blank");
            password.requestFocus();
        } else if(cpassword.getText().toString().equals(password.getText().toString())){

            BackgroundWorker backgroundWorker = new BackgroundWorker(ctx);
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = connectivityManager.getActiveNetworkInfo();
            if (nInfo != null && nInfo.isConnected()) {

                String url = Const.REGISTERURL;
                backgroundWorker.delegate = (AsyncResponse) ctx;
                HashMap<String, String> parms = new HashMap<>();
                parms.put("companyname", comanyname.getText().toString());
                parms.put("fname", fname.getText().toString());
                parms.put("lname", lname.getText().toString());
                parms.put("address", address.getText().toString());
                parms.put("email", email.getText().toString());
                parms.put("contact", contact.getText().toString());

                if (session.getSocialid().equals("")) {
                    parms.put("account_type", "Personal");
                    parms.put("social_id", "Personal");
                } else {
                    parms.put("account_type", session.getSocialactype());
                    parms.put("social_id", session.getSocialid());
                }
                parms.put("password", password.getText().toString());
                backgroundWorker.data(parms);
                backgroundWorker.execute(url);

            } else {
                Toast.makeText(this, "Not Connected to Internet", Toast.LENGTH_SHORT).show();
            }
        }else {
            cpassword.setError("Password can't matched");
            cpassword.requestFocus();
        }
    }

    @Override
    public void processFinish(String output) {
        if (output.equals("registered successfully")) {
            Toast.makeText(ctx, output, Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }
    }
}
