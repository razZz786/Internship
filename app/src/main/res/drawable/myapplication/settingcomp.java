package com.example.hacker.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class settingcomp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settingcomp);
    }
    public void cprofile (View view)
    {
        Intent intent=new Intent(this,companyprofile.class);
        startActivity(intent);
    }
}
