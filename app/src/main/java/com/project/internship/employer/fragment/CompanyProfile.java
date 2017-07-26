package com.project.internship.employer.fragment;


import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.project.internship.R;
import com.project.internship.connection.Const;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 */
public class CompanyProfile extends Fragment {
    private Bitmap bitmap;
    private final int SELECT_PHOTO = 1;
    private Uri filePath;
    Button upload;

    public CompanyProfile() {
        // Required empty public constructor
    }

    ImageView logo;
    EditText address, desc;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_company_profile2, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Company Profile");
        logo = (ImageView) getView().findViewById(R.id.imglogo);
        address = (EditText) getView().findViewById(R.id.address);
        desc = (EditText) getView().findViewById(R.id.description);
        upload=(Button)getView().findViewById(R.id.upload);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, SELECT_PHOTO);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadMultipart();
            }
        });

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SELECT_PHOTO:
                if (resultCode == RESULT_OK) {
                    // imABoolean=true;
                    try {
                        filePath = data.getData();
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);

                        logo.setImageBitmap(bitmap);


                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                }
        }


    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getActivity().getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();
        cursor = getActivity().getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();
        return path;
    }

    public void uploadMultipart() {
        //getting name for the image


        //getting the actual path of the image
        String path = getPath(filePath);
        //  Toast.makeText(getContext(),path,Toast.LENGTH_LONG).show();

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();


            //Creating a multi part request
            new MultipartUploadRequest(getContext(), uploadId, Const.CompanyProfile)
                    .addFileToUpload(path, "logo") //Adding file
                    .addParameter("companyname", "BGn") //Adding text parameter to the request
                    .addParameter("companyemail", "r@r.com")
                    .addParameter("description", desc.getText().toString())
                    .addParameter("address", address.getText().toString())
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload
           // startActivity(new Intent(getContext(), HOME.class));
            Toast.makeText(getContext(), "Thanks for Submit your Property..", Toast.LENGTH_LONG).show();
            Fragment fragment = new EmployerHomeFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.newview, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } catch (Exception exc) {
            Toast.makeText(getContext(), exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //finish();
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}
