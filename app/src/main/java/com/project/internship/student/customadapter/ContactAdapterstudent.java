package com.project.internship.student.customadapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.internship.R;
import com.project.internship.Session;
import com.project.internship.employer.model.Contacts;
import com.project.internship.student.fragment.Apply;
import com.project.internship.student.model.Contactsstudent;
import com.project.internship.student.view.StudentHome;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Rahul Bhardwaj on 5/3/2016.
 */
public class ContactAdapterstudent extends ArrayAdapter {
    List list=new ArrayList();
    Context context;
    Session session;
    public ContactAdapterstudent(Context context, int resource) {
        super(context, resource);
        this.context=context;
    }


    public void add(Contactsstudent object) {
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
        row=convertView;
        ContactHolderstudent contactHolder;
        if(row==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.studenthomeadapter,parent,false);
            contactHolder =new ContactHolderstudent();
            contactHolder.company_name=(TextView)row.findViewById(R.id.company_name);
            contactHolder.com_title=(TextView)row.findViewById(R.id.com_title);
            contactHolder.date_com=(TextView)row.findViewById(R.id.date_com);
            contactHolder.apply=(LinearLayout) row.findViewById(R.id.apply);
            contactHolder.maps=(LinearLayout) row.findViewById(R.id.maps);
           // contactHolder.imageView=(ImageView) row.findViewById(R.id.iv_icon);

          row.setTag(contactHolder);
        }else {
            contactHolder =(ContactHolderstudent)row.getTag();
        }
        final Contactsstudent contacts=(Contactsstudent)this.getItem(position);
        contactHolder.company_name.setText(contacts.getName());
        contactHolder.com_title.setText(contacts.getTitle());
        contactHolder.date_com.setText(contacts.getDate());
        contactHolder.apply.setClickable(true);
        contactHolder.apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getContext(),"Apply",Toast.LENGTH_LONG).show();
                StudentHome myActivity = (StudentHome) context;
                session=(Session)myActivity.getApplicationContext();
                session.setApplyposition(position);
                Fragment fragment = new Apply();
                FragmentManager fragmentManager =myActivity.getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.studentframe, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }
        });
        contactHolder.maps.setClickable(true);
        contactHolder.maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(android.content.Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q="+contacts.getLocation()));
               // String uri = "http://maps.google.com/maps?saddr=" + "9982878"+","+"76285774"+"&daddr="+"9992084"+","+"76286455";
              //  Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
             //   intent.setClassName("com.google.android.apps.maps", "com.google.android.maps.MapsActivity");
                context.startActivity(intent);
               // Toast.makeText(getContext(),"Apply",Toast.LENGTH_LONG).show();
            }
        });

        return row;
    }

    static class ContactHolderstudent
    {
        TextView company_name,com_title,date_com;
        LinearLayout apply,maps;
        //ImageView imageView;
    }







}
