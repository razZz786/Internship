package com.example.hacker.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class settingstudent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingstudent);
    }
    public void sprofile (View view)
    {
        Intent intent=new Intent(this,studentprofile.class);
        startActivity(intent);
    }
}
