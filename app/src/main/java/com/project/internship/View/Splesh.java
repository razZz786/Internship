package com.project.internship.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.project.internship.R;
import com.project.internship.Session;
import com.project.internship.employer.view.EmployerHome;
import com.project.internship.student.view.StudentHome;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterCore;

public class Splesh extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefs";
    public static final String resule = "result";
    public static final String logintype = "type";
    Session session;
    String type=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splesh);
        session=(Session)getApplicationContext();
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        session.setLoginresult(settings.getString(resule, ""));
        type=settings.getString(logintype, "");
        Thread timer = new Thread() {
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    if(type.equals("")){
                    startActivity(new Intent(getApplicationContext(), Login.class));
                    finish();
                    }else if(type.equals("student")){
                        startActivity(new Intent(getApplicationContext(), StudentHome.class));
                        finish();

                    }else {
                        startActivity(new Intent(getApplicationContext(), EmployerHome.class));
                        finish();
                    }
                }
            }
        };
        timer.start();
    }
}
