package com.example.hacker.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class applicants extends AppCompatActivity {

    String ttl=" ";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        */
        setContentView(R.layout.activity_applicants);
        getActionBar().setTitle(ttl);
    }
    public void two (View view)
    {
        Intent intent=new Intent(this,com.example.hacker.myapplication.resume.class);
        startActivity(intent);
    }
}
