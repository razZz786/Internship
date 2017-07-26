package com.example.hacker.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class companyLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_login);
    }
    public void next (View view)
    {
        Intent intent=new Intent(this,com.example.hacker.myapplication.comapanyhome.class);
        startActivity(intent);
    }
    public void one (View view)
    {
        Intent intent=new Intent(this,forgetpassemail.class);
        startActivity(intent);
    }
    public void signup (View view)
    {
        Intent intent=new Intent(this,signup.class);
        startActivity(intent);
    }

}
