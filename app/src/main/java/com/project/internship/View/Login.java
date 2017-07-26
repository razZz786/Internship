package com.project.internship.View;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.project.internship.R;
import com.project.internship.Session;
import com.project.internship.connection.AsyncResponse;
import com.project.internship.connection.BackgroundWorker;
import com.project.internship.connection.Const;
import com.project.internship.employer.view.EmployeSignUp;
import com.project.internship.employer.view.EmployerHome;
import com.project.internship.student.view.StudentHome;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterAuthClient;

import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;

import io.ghyeok.stickyswitch.widget.StickySwitch;

public class Login extends AppCompatActivity implements AsyncResponse {

    LinearLayout company;
    LinearLayout student;
    EditText studentid, password;
    Context ctx = this;
    Session session;
    Boolean studentac = true;
    EditText empusername,emppassword;
    private GoogleApiClient mGoogleApiClient;
    ImageView googlelogin,fbbutton,twitterbt;
    private CallbackManager mFacebookCallbackManager;
    com.facebook.login.LoginManager fbLoginManager;
    boolean facbooklogin=false;
    TwitterAuthClient client;
    public static final String PREFS_NAME = "MyPrefs";
    public static final String resule = "result";
    public static final String logintype = "type";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        company = (LinearLayout) findViewById(R.id.company);
        StickySwitch stickySwitch = (StickySwitch) findViewById(R.id.sticky_switch);
        student = (LinearLayout) findViewById(R.id.student);
        session = (Session) getApplicationContext();
        password = (EditText) findViewById(R.id.password);
        studentid = (EditText) findViewById(R.id.studentid);
        stickySwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(@NotNull StickySwitch.Direction direction, @NotNull String text) {
                // Toast.makeText(getApplicationContext(),direction.name()+"",Toast.LENGTH_LONG).show();
                if (direction.name().equals("RIGHT")) {
                    studentac = false;
                    company.setVisibility(View.VISIBLE);
                    student.setVisibility(View.GONE);
                } else {
                    studentac = true;
                    student.setVisibility(View.VISIBLE);
                    company.setVisibility(View.GONE);
                }
            }
        });


        ///////////////////////Company login

        FacebookSdk.sdkInitialize(getApplicationContext());

        Twitter.initialize(this);
        TwitterConfig config = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(new TwitterAuthConfig("PCaGkhonv1YX2IQgWyJ3OWNRL", "VkGlZzSkomxWkIpAGcUgNHYrtptqOKaT8jfL4l0IWubibxE111"))
                .debug(true)
                .build();
        Twitter.initialize(config);

        emppassword=(EditText)findViewById(R.id.emppassword);
        empusername=(EditText)findViewById(R.id.empusername);
        googlelogin=(ImageView)findViewById(R.id.googlelogin);
        twitterbt=(ImageView)findViewById(R.id.twitterbt);
        fbbutton=(ImageView)findViewById(R.id.fbbutton);
        googlelogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInWithGoogle();
            }
        });
        fbLoginManager = com.facebook.login.LoginManager.getInstance();
        mFacebookCallbackManager = CallbackManager.Factory.create();
        fbLoginManager.registerCallback(mFacebookCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(final LoginResult loginResult) {

                        final AccessToken accessToken = loginResult.getAccessToken();

                        GraphRequest request = GraphRequest.newMeRequest(
                                accessToken,
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(
                                            JSONObject object,
                                            GraphResponse response) {
                                        sociallogin(object.optString("email"),object.optString("id"),"Facebook");
                                        //    Toast.makeText(getApplicationContext(),object.optString("id"),Toast.LENGTH_LONG).show();
                                        //   sociallogin(object.optString("email"));

                                        // Application code
                                    }
                                });
                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email");
                        request.setParameters(parameters);
                        request.executeAsync();

                        //  Toast.makeText(getApplicationContext(),accessToken+"",Toast.LENGTH_LONG).show();
/*
                        //TODO: Use the Profile class to get information about the current user.
                        handleSignInResult(new Callable<Void>() {
                            @Override
                            public Void call() throws Exception {
                                LoginManager.getInstance().logOut();
                                return null;
                            }
                        });

  */                  }

                    @Override
                    public void onCancel() {
                        handleSignInResult(null);
                        Toast.makeText(getApplicationContext(),"Cancel",Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d(Login.class.getCanonicalName(), error.getMessage());
                        Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                        handleSignInResult(null);
                    }
                }

        );
        // facebook Custom Button
        fbbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                facbooklogin=true;
                fbLoginManager.logInWithReadPermissions(Login.this, Arrays.asList("email", "public_profile"));

            }
        });

        twitterbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logintotwitter();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    public void studentlogin(View view) {
        if (studentid.getText().toString().equals("")) {
            studentid.setError("Student id can't Blank");
            studentid.requestFocus();
        } else if (password.getText().toString().equals("")) {
            password.setError("Password can't Blank");
            password.requestFocus();
        } else {
            BackgroundWorker backgroundWorker = new BackgroundWorker(ctx);
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = connectivityManager.getActiveNetworkInfo();
            if (nInfo != null && nInfo.isConnected()) {
                String url = Const.Student_login;
                backgroundWorker.delegate = (AsyncResponse) ctx;
                HashMap<String, String> parms = new HashMap<>();
                parms.put("studentid", studentid.getText().toString());
                parms.put("password", password.getText().toString());
                backgroundWorker.data(parms);
                backgroundWorker.execute(url);

            } else {
                Toast.makeText(this, "Not Connected to Internet", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void logintotwitter() {
        client = new TwitterAuthClient();
        //make the call to login
        client.authorize(this, new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                //feedback
                sociallogin(result.data.getUserName(),result.data.getUserId()+"","Twitter");

                //   Toast.makeText(getApplicationContext(), "Login worked"+result.data.getUserName()+""+result.data.getUserId()+"", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(TwitterException e) {
                //feedback
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });


    }

    public void empsignup(View view){
        if(empusername.getText().toString().equals("")){
            empusername.setError("Email Can't Blank");
            empusername.requestFocus();

        }else if(emppassword.getText().toString().equals("")){
            emppassword.setError("Password can't Blank");
            emppassword.requestFocus();
        }else {
            BackgroundWorker backgroundWorker = new BackgroundWorker(ctx);
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = connectivityManager.getActiveNetworkInfo();
            if (nInfo != null && nInfo.isConnected()) {

                String url= Const.CompanyLogin;
                backgroundWorker.delegate = (AsyncResponse) ctx;
                HashMap<String, String> parms = new HashMap<>();
                parms.put("email", empusername.getText().toString());
                parms.put("password", emppassword.getText().toString());
                backgroundWorker.data(parms);
                backgroundWorker.execute(url);

            } else {
                Toast.makeText(this, "Not Connected to Internet", Toast.LENGTH_SHORT).show();
            }

        }

    }
    public void forgot(View view){
        startActivity(new Intent(getApplicationContext(),ForgotPassword.class));
        finish();
    }
    public void signup(View view){
        startActivity(new Intent(getApplicationContext(),EmployeSignUp.class));
        finish();
    }

    private void handleSignInResult(Object o) {
        //  o.getString("email");
        // Toast.makeText(getApplicationContext(),o+"",Toast.LENGTH_LONG).show();
//Facebook login code here
    }
    // google login code
    private static final int RC_SIGN_IN = 9001;

    private void signInWithGoogle() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        final Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    /// on activity Result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            GoogleSignInAccount acct = result.getSignInAccount();
            String googleemail = acct.getEmail();
            String googleid=acct.getId();
            sociallogin(googleemail,googleid,"Google");
            // Toast.makeText(getApplicationContext(),googleemail+" "+googleid,Toast.LENGTH_LONG).show();
            logout();
        }else
        if(facbooklogin){
            facbooklogin=false;
            mFacebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
        else {
            client.onActivityResult(requestCode, resultCode, data);
        }
        // Handle other values for requestCode

    }

    ////google logout code
    public void logout() {
        mGoogleApiClient.connect();
        mGoogleApiClient.registerConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(@Nullable Bundle bundle) {

                //FirebaseAuth.getInstance().signOut();
                if(mGoogleApiClient.isConnected()) {
                    Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(new ResultCallback<Status>() {
                        @Override
                        public void onResult(@NonNull Status status) {
                            if (status.isSuccess()) {

                                // Toast.makeText(getApplicationContext(),"Working",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onConnectionSuspended(int i) {
                //Log.d(TAG, "Google API Client Connection Suspended");
            }
        });
    }
    public void sociallogin(String email,String socialid,String type){
        session.setSocialemail(email);
        session.setSocialid(socialid);
        session.setSocialactype(type);
        BackgroundWorker backgroundWorker = new BackgroundWorker(ctx);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = connectivityManager.getActiveNetworkInfo();
        if (nInfo != null && nInfo.isConnected()) {

            String url= Const.CompanySocialLogin;
            backgroundWorker.delegate = (AsyncResponse) ctx;
            HashMap<String, String> parms = new HashMap<>();
            parms.put("email", email);
            parms.put("social_id", socialid);
            backgroundWorker.data(parms);
            backgroundWorker.execute(url);

        } else {
            Toast.makeText(this, "Not Connected to Internet", Toast.LENGTH_SHORT).show();
        }

    }



    @Override
    public void processFinish(String output) {
        if (studentac) {
            if (output.equals("Invalid student id and password")) {
                Toast.makeText(getApplicationContext(), output, Toast.LENGTH_LONG).show();
            } else {
                session.setLoginresult(output);
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                     // Writing data to SharedPreferences
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(resule, session.getLoginresult());
                    editor.putString(logintype,"student");
                    editor.commit();

                Toast.makeText(getApplicationContext(), "Welcome Student", Toast.LENGTH_LONG).show();
                 startActivity(new Intent(getApplicationContext(),StudentHome.class));
                 finish();
            }
        }else {
            if(output.trim().equals("1")){
                Showmessage();
            }else if(output.equals("Invalid email and password")) {
                Toast.makeText(getApplicationContext(), output, Toast.LENGTH_LONG).show();
            }else {
                session.setLoginresult(output);
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
                // Writing data to SharedPreferences
                SharedPreferences.Editor editor = settings.edit();
                editor.putString(resule, session.getLoginresult());
                editor.putString(logintype,"company");
                editor.commit();

                  startActivity(new Intent(getApplicationContext(),EmployerHome.class));
                  finish();
            }
        }
    }
    public void Showmessage(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //Uncomment the below code to Set the message and title from the strings.xml file
        //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

        //Setting message manually and performing action on button click
        builder.setMessage("Company profile not found do you want to Register new company ?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                         startActivity(new Intent(getApplicationContext(),EmployeSignUp.class));
                         finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                    }
                });

        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("Profile not found");
        alert.show();
    }
}
