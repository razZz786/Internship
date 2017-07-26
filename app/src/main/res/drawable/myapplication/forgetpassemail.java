package com.example.hacker.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class forgetpassemail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpassemail);
    }
    public void sendf (View view)
    {
        Intent intent=new Intent(this,com.example.hacker.myapplication.forgetpasscomp.class);
        startActivity(intent);
    }
}
