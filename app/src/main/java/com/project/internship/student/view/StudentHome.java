package com.project.internship.student.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.project.internship.R;
import com.project.internship.Session;
import com.project.internship.View.Login;
import com.project.internship.employer.fragment.EmployerHomeFragment;
import com.project.internship.student.fragment.ChangePassword;
import com.project.internship.student.fragment.Inbox;
import com.project.internship.student.fragment.LocationFragment;
import com.project.internship.student.fragment.MinmumDurationFragment;
import com.project.internship.student.fragment.Outbox;
import com.project.internship.student.fragment.SearchByField;
import com.project.internship.student.fragment.SearchbyCompanyFragment;
import com.project.internship.student.fragment.StudentHomeFragment;
import com.project.internship.student.fragment.StudentProfile;

import org.json.JSONException;
import org.json.JSONObject;

public class StudentHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    View header;
    Session session;
    FragmentTransaction fragmentTransaction;
    public static final String PREFS_NAME = "MyPrefs";
    public static final String resule = "result";
    public static final String logintype = "type";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_home);
        session=(Session)getApplicationContext();
        session.setInboxtype("student");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        header = LayoutInflater.from(this).inflate(R.layout.nav_header_employer_home, null);
        navigationView.addHeaderView(header);
        JSONObject jsonObject;
        try {
            jsonObject=new JSONObject(session.getLoginresult());
            TextView text = (TextView) header.findViewById(R.id.companyname);
            text.setText(("Welcome "+jsonObject.getString("fname")+" "+jsonObject.getString("lname")).toUpperCase() );
            TextView text1 = (TextView) header.findViewById(R.id.companyemail);
            text1.setText(""+jsonObject.getString("email"));
            session.setEmail(jsonObject.getString("email"));
            session.setSocialid(jsonObject.getString("student_id"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        session.setSearchtype("Student Home");
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.studentframe, new StudentHomeFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.nav_location) {
            session.setSearchtype("Search By Location");
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.studentframe, new StudentHomeFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            // Handle the camera action
        } else if (id == R.id.nav_min) {
            session.setSearchtype("Search By Duraction");
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.studentframe, new StudentHomeFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_search_company) {
            session.setSearchtype("Search By Company");
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.studentframe, new StudentHomeFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else if (id == R.id.nav_search_field) {
            session.setSearchtype("Search By Field");
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.studentframe, new StudentHomeFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }

            return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_schange) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.studentframe, new ChangePassword());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else
        if (id == R.id.nav_profile) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.studentframe, new StudentProfile());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else
        if (id == R.id.nav_sapplied) {
            session.setOutboxtype("Applied Internships");
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.studentframe, new Outbox());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else
        if (id == R.id.nav_soutbox) {
            session.setOutboxtype("Outbox");
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.studentframe, new Outbox());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else
        if (id == R.id.nav_studenthome) {
            session.setSearchtype("Student Home");
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.studentframe, new StudentHomeFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else  if (id == R.id.nav_sinbox) {
            session.setInboxtypeemp("in");
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.studentframe, new Inbox());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }else if (id == R.id.nav_signout) {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(resule,"");
            editor.putString(logintype,"");
            editor.commit();
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
