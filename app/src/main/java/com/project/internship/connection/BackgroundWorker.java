package com.project.internship.connection;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by BDMPL1 on 14-Oct-16.
 */

public class BackgroundWorker  extends AsyncTask<String,Void,String> {

    ProgressDialog progressDialog ;
    Context context;

    Boolean type=false;
    HashMap<String, String> post_data;
    public AsyncResponse delegate = null;

    public void data( HashMap<String, String> parms){
        this.post_data=parms;
    }
    public BackgroundWorker(Context ctx) {
        context = ctx;
    }
    public void settype(Boolean type){
        this.type=type;
    }

    @Override
    protected String doInBackground(String... params) {



        String data= null;
        try {

            data = getPostDataString(post_data);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (Exception e){};


        String register_url = params[0];

        try {
            URL url = new URL(register_url);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            OutputStream outputStream = httpURLConnection.getOutputStream();
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
            bufferedWriter.write(data);
            bufferedWriter.flush();
            bufferedWriter.close();
            outputStream.close();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,"iso-8859-1"));
            String result="";
            String line="";
            while((line = bufferedReader.readLine())!= null) {
                result += line;
            }
            bufferedReader.close();
            inputStream.close();
            httpURLConnection.disconnect();
            return result;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        if(type){}else {
            progressDialog = new ProgressDialog(context);
            if (progressDialog != null) {
                progressDialog.setMessage("Please wait...");
                progressDialog.show();
                progressDialog.setCanceledOnTouchOutside(false);
            }
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if(type){
            delegate.processFinish(result);
        }else {
            progressDialog.hide();
            delegate.processFinish(result);
        }
    }

    @Override
    protected void onProgressUpdate(Void... values) {

        super.onProgressUpdate(values);
    }

    private String getPostDataString(HashMap<String, String> params) throws UnsupportedEncodingException
    {
        StringBuilder result = new StringBuilder();
        Iterator myVeryOwnIterator = params.keySet().iterator();
        int i=0;
        while(myVeryOwnIterator.hasNext()) {
            String key=(String)myVeryOwnIterator.next();
            String value=(String)params.get(key);
            if(i==0) {
                result.append(URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8"));
                i=1;
            }else{
                result.append("&"+ URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(value, "UTF-8"));
            }
        }

        return result.toString();
    }

}

