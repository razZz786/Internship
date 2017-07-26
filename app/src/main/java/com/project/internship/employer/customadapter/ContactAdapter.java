package com.project.internship.employer.customadapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.internship.R;
import com.project.internship.employer.model.Contacts;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul Bhardwaj on 5/3/2016.
 */
public class ContactAdapter extends ArrayAdapter {
    List list=new ArrayList();
    public ContactAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(Contacts object) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row=convertView;
        ContactHolder contactHolder;
        if(row==null)
        {
            LayoutInflater layoutInflater=(LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row=layoutInflater.inflate(R.layout.employerinbox,parent,false);
            contactHolder =new ContactHolder();
            contactHolder.reciverid=(TextView)row.findViewById(R.id.reciverid);
            contactHolder.message=(TextView)row.findViewById(R.id.message);
            contactHolder.date=(TextView)row.findViewById(R.id.date);
           // contactHolder.imageView=(ImageView) row.findViewById(R.id.iv_icon);

          row.setTag(contactHolder);
        }else {
            contactHolder =(ContactHolder)row.getTag();
        }
         Contacts contacts=(Contacts)this.getItem(position);
        contactHolder.reciverid.setText(contacts.getReciverid());
        contactHolder.message.setText(contacts.getMessage());
        contactHolder.date.setText(contacts.getDate());
     /*   Picasso.with(getContext())
                .load(contacts.getimg())
                .resize(180, 180) // here you resize your image to whatever width and height you like
                .into(contactHolder.imageView);
        */
        return row;
    }

    static class ContactHolder
    {
        TextView reciverid,message,date;
        ImageView imageView;
    }







}
