package com.project.internship.employer.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.internship.R;
import com.project.internship.employer.customadapter.ContactAdapter;
import com.project.internship.employer.customadapter.SearchNameAdapter;
import com.project.internship.employer.model.Contacts;
import com.project.internship.employer.model.SearchNameContacts;

/**
 * A simple {@link Fragment} subclass.
 */
public class NameFragment extends Fragment {

    ListView listView;
    public NameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_name, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Name");
      /*  listView=(ListView)getView().findViewById(R.id.serachname);
        SearchNameAdapter contactAdapter=new SearchNameAdapter(getContext(),R.layout.namesearchadapter);
        for(int i=0;i<=5;i++) {
            SearchNameContacts contacts = new SearchNameContacts("", "", "", "");
            contactAdapter.add(contacts);
        }
        listView.setAdapter(contactAdapter);
    */}
    @Override
    public void onResume() {
        super.onResume();
    }
}
