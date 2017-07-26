package com.project.internship.employer.customadapter;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.project.internship.R;
import com.project.internship.Session;
import com.project.internship.employer.model.SearchNameContacts;
import com.project.internship.employer.view.EmployerHome;
import com.project.internship.student.fragment.Enquiry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Bhardwaj on 5/3/2016.
 */
public class SearchNameAdapter extends ArrayAdapter {
    List list = new ArrayList();
    JSONObject jsonObject;
    JSONArray jsonArray;
    Context ctx;
    Session session;

    public SearchNameAdapter(Context context, int resource) {
        super(context, resource);
        this.ctx = context;
    }


    public void add(SearchNameContacts object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        ContactHoldername contactHolder;
        if (row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.namesearchadapter, parent, false);
            contactHolder = new ContactHoldername();
            contactHolder.et_name = (TextView) row.findViewById(R.id.et_name);
            contactHolder.et_dob = (TextView) row.findViewById(R.id.et_dob);
            contactHolder.et_student_id = (TextView) row.findViewById(R.id.et_student_id);
            contactHolder.et_contact_number = (TextView) row.findViewById(R.id.et_contact_number);
            contactHolder.et_email = (TextView) row.findViewById(R.id.et_email);
            contactHolder.et_address = (TextView) row.findViewById(R.id.et_address);
            contactHolder.ed_cgpa = (TextView) row.findViewById(R.id.ed_cgpa);
            contactHolder.bt_call = (Button) row.findViewById(R.id.bt_call);
            contactHolder.bt_message = (Button) row.findViewById(R.id.bt_message);
            row.setTag(contactHolder);
        } else {
            contactHolder = (ContactHoldername) row.getTag();
        }
        final SearchNameContacts contacts = (SearchNameContacts) this.getItem(position);
        contactHolder.et_name.setText(contacts.getName());
        contactHolder.et_dob.setText(contacts.getDob());
        contactHolder.et_student_id.setText(contacts.getStudentid());
        contactHolder.et_contact_number.setText(contacts.getContactnumber());
        contactHolder.et_email.setText(contacts.getEmail());
        contactHolder.et_address.setText(contacts.getAddress());
        contactHolder.ed_cgpa.setText(contacts.getCgpa());
        contactHolder.bt_call.setClickable(true);
        contactHolder.bt_message.setClickable(true);
        EmployerHome myActivity = (EmployerHome) ctx;
        session = (Session) myActivity.getApplicationContext();
        contactHolder.bt_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    jsonObject = new JSONObject(contacts.getJson());
                    jsonArray = jsonObject.getJSONArray("responce");

                    JSONObject jo = jsonArray.getJSONObject(position);
                    Toast.makeText(ctx, jo.getString("contact_no"), Toast.LENGTH_LONG).show();
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + jo.getString("contact_no")));//change the number
                    if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    ctx.startActivity(callIntent);
                    //SearchNameContacts contacts = new SearchNameContacts(jo.getString("fname").toUpperCase()+" "+jo.getString("lname").toUpperCase(), "D.O.B "+jo.getString("dob"),jo.getString("student_id"), jo.getString("contact_no"), jo.getString("email"),jo.getString("address"),jo.getString("cgpa"),output);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }
        });
        contactHolder.bt_message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    jsonObject = new JSONObject(contacts.getJson());
                    jsonArray = jsonObject.getJSONArray("responce");

                    JSONObject jo = jsonArray.getJSONObject(position);
                    Toast.makeText(ctx,jo.getString("email"),Toast.LENGTH_LONG).show();
                    session.setCompanymessageemail(jo.getString("email"));
                    EmployerHome myActivity = (EmployerHome) ctx;
                    Fragment fragment = new Enquiry();
                    FragmentManager fragmentManager =myActivity.getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace(R.id.newview, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    //SearchNameContacts contacts = new SearchNameContacts(jo.getString("fname").toUpperCase()+" "+jo.getString("lname").toUpperCase(), "D.O.B "+jo.getString("dob"),jo.getString("student_id"), jo.getString("contact_no"), jo.getString("email"),jo.getString("address"),jo.getString("cgpa"),output);


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
        return row;
    }

    static class ContactHoldername
    {
        TextView et_name,et_dob,et_student_id,et_contact_number,et_email,et_address,ed_cgpa;
        Button bt_call,bt_message;
    }







}
