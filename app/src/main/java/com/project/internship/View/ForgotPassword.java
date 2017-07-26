package com.project.internship.View;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.internship.R;
import com.project.internship.connection.AsyncResponse;
import com.project.internship.connection.BackgroundWorker;
import com.project.internship.connection.Const;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ForgotPassword extends AppCompatActivity implements AsyncResponse{

    EditText et_email;
    Context ctx=this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        et_email=(EditText)findViewById(R.id.et_email);
    }
    public void forgotpass(View view){
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(et_email.getText().toString().equals("")){
            et_email.setError("Email can't Blank");
            et_email.requestFocus();
        }else if(!et_email.getText().toString().matches(emailPattern)){
            et_email.setError("Enter Valid Email");
            et_email.requestFocus();
        }else {
            BackgroundWorker backgroundWorker = new BackgroundWorker(ctx);
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = connectivityManager.getActiveNetworkInfo();
            if (nInfo != null && nInfo.isConnected()) {
                String url= Const.ForgotPassword;
                backgroundWorker.delegate = (AsyncResponse) ctx;
                HashMap<String, String> parms = new HashMap<>();
                parms.put("email", et_email.getText().toString());
                backgroundWorker.data(parms);
                backgroundWorker.execute(url);

            } else {
                Toast.makeText(this, "Not Connected to Internet", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void processFinish(String output) {
        if (output.equals("Invalid email")) {
            Toast.makeText(getApplicationContext(), "Invalid email id", Toast.LENGTH_LONG).show();
        }else {
            JSONObject jsonObject;
            try {
                jsonObject = new JSONObject(output);
                Toast.makeText(getApplicationContext(),jsonObject.getString("contact"),Toast.LENGTH_LONG).show();
                Intent intent=new Intent(getApplicationContext(),Login.class);
                PendingIntent pi=PendingIntent.getActivity(getApplicationContext(), 0, intent,0);

                //Get the SmsManager instance and call the sendTextMessage method to send message
                SmsManager sms=SmsManager.getDefault();
                sms.sendTextMessage(jsonObject.getString("contact"), null,"Your Password for Internship Application is "+jsonObject.getString("password"), pi,null);
                finish();
                Toast.makeText(getApplicationContext(), "Message Sent successfully!", Toast.LENGTH_LONG).show();

              //

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }

        }
        }
}
