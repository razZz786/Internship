package com.example.hacker.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class divide extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_divide);
    }
    public void comp (View view)
    {
        Intent intent=new Intent(this,com.example.hacker.myapplication.companyLogin.class);
        startActivity(intent);
    }
    public void student (View view)
    {
        Intent intent=new Intent(this,studentlogins.class);
        startActivity(intent);
    }
}
