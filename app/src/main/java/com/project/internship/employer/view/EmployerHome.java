package com.project.internship.employer.view;

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
import android.widget.Toast;

import com.project.internship.R;
import com.project.internship.Session;
import com.project.internship.View.Login;
import com.project.internship.employer.fragment.CGPAFragment;
import com.project.internship.employer.fragment.CompanyProfile;
import com.project.internship.employer.fragment.EmployerChangePwd;
import com.project.internship.employer.fragment.EmployerHomeFragment;
import com.project.internship.employer.fragment.NameFragment;
import com.project.internship.employer.fragment.PostInternship;
import com.project.internship.employer.fragment.SearchByCourseFragment;
import com.project.internship.employer.fragment.StudentIdFragment;
import com.project.internship.student.fragment.Inbox;
import com.project.internship.student.fragment.Outbox;

import org.json.JSONException;
import org.json.JSONObject;

public class EmployerHome extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FragmentTransaction fragmentTransaction;
    Session session;
    View header;
    public static final String PREFS_NAME = "MyPrefs";
    public static final String resule = "result";
    public static final String logintype = "type";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_home);
        session = (Session) getApplicationContext();
        session.setInboxtype("company");
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
            jsonObject = new JSONObject(session.getLoginresult());
            TextView text = (TextView) header.findViewById(R.id.companyname);
            text.setText("Welcome " + jsonObject.getString("company_name"));
            session.setCompanyname(jsonObject.getString("company_name"));
            TextView text1 = (TextView) header.findViewById(R.id.companyemail);
            text1.setText("" + jsonObject.getString("email"));
            session.setEmail(jsonObject.getString("email"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.newview, new EmployerHomeFragment());
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
        getMenuInflater().inflate(R.menu.employer_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        //noinspection SimplifiableIfStatement

        if (id == R.id.nav_name) {
            session.setCompanysearchtype("name");
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.newview, new StudentIdFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            // Handle the camera action
        } else if (id == R.id.nav_studentid) {
            session.setCompanysearchtype("id");
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.newview, new StudentIdFragment());
            fragmentTransaction.commit();

        } else if (id == R.id.nav_cgpa) {
            session.setCompanysearchtype("cgpa");
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.newview, new StudentIdFragment());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else if (id == R.id.nav_searchbycourse) {
            session.setCompanysearchtype("course");
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.newview, new StudentIdFragment());
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

        if (id == R.id.nav_echangepassword) {

            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.newview, new EmployerChangePwd());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            // Handle the camera action
        } else
        if (id == R.id.nav_eprofile) {

            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.newview, new CompanyProfile());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            // Handle the camera action
        } else
        if (id == R.id.nav_eapply) {
            session.setInboxtypeemp("emp");
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.newview, new Inbox());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            // Handle the camera action
        } else
        if (id == R.id.post_home) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.newview, new EmployerHomeFragment());
            fragmentTransaction.commit();
            // Handle the camera action
        } else if (id == R.id.post_int) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.newview, new PostInternship());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            // Handle the camera action
        } else  if (id == R.id.nav_eoutbox) {
            fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.newview, new Outbox());
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
            // Handle the camera action
        } else if (id == R.id.nav_signout) {
            SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString(resule,"");
            editor.putString(logintype,"");
            startActivity(new Intent(getApplicationContext(), Login.class));
            finish();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
