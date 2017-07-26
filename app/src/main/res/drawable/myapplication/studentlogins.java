package com.example.hacker.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class studentlogins extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentlogins);
    }
    public void next (View view)
    {
        Intent intent=new Intent(this,forgotpassword.class);
        startActivity(intent);
    }
    public void frgpass (View view)
    {
        Intent intent=new Intent(this,forgotpassword.class);
        startActivity(intent);
    }
    public void studentlogin (View view)
    {
        Intent intent=new Intent(this,studenthome.class);
        startActivity(intent);
    }
}
