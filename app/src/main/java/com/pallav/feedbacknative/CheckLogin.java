package com.pallav.feedbacknative;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.CursorJoiner;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.pallav.feedbacknative.Util.Constant;
import com.pallav.feedbacknative.Util.MyFirebaseMessagingService;
import com.pallav.feedbacknative.Util.NetworkUtil;
import com.pallav.feedbacknative.Util.SetSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;

import java.net.URL;

public class CheckLogin extends AppCompatActivity implements View.OnClickListener {

    static boolean errored = false;
    Button b;
    TextView statusTV;
    EditText userNameET, passWordET, inputEmail;
    ProgressBar webservicePG;
    boolean loginStatus;
    String loginWSResponse;
    String deviceToken = null;
    String editTextPassword;
    int WScount, newFeedbacCount = 0;
    boolean forgotpasswordOTP, forgotpasswordverifyOTP;

    Button btn_create_an_account, btn_forgot_password;
    MyFirebaseMessagingService firebaseService;
    private void initSetup() {
        btn_create_an_account = (Button) findViewById(R.id.btn_create_an_account);
        btn_forgot_password = (Button) findViewById(R.id.btn_forgot_password);
        userNameET = (EditText) findViewById(R.id.editText1);
        passWordET = (EditText) findViewById(R.id.editText2);
        webservicePG = (ProgressBar) findViewById(R.id.progressBar1);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);


        getSupportActionBar().setTitle("");

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayUseLogoEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setHomeButtonEnabled(true);

        actionbar.setLogo(R.drawable.actionbar_logo);
        actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));


        initSetup();
        IBAction();

        setUserPreferences();
       deviceToken = MyFirebaseMessagingService.getDeviceToken();


        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        //Name Text control

        //Display Text control
        statusTV = (TextView) findViewById(R.id.tv_result);
        //Button to trigger web service invocation
        b = (Button) findViewById(R.id.button1);


        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //Check if text controls are not empty
                if (userNameET.getText().length() != 0 && userNameET.getText().toString() != "") {
                    if (passWordET.getText().length() != 0 && passWordET.getText().toString() != "") {

                        statusTV.setText("");
                        //Create instance for AsyncCallWS
                        AsyncCallWS task = new AsyncCallWS();
                        //Call execute
                        task.execute();
                    }
                    //If Password text control is empty
                    else {
                        statusTV.setText("Please enter Password");
                    }
                    //If Username text control is empty
                } else {
                    statusTV.setText("Please enter Username");
                }
            }
        });
    }


    private void setUserPreferences() {
        SetSharedPreferences setSharedPreferences = new SetSharedPreferences();
        if (setSharedPreferences.getBool(CheckLogin.this, "isLoggedIn")) {
            userNameET.setText(setSharedPreferences.getValue(CheckLogin.this, "Username"));
            passWordET.setText(setSharedPreferences.getValue(CheckLogin.this, "Password"));

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    /* Create an Intent that will start the Menu-Activity. */
                    AsyncCallWS task = new AsyncCallWS();
                    //Call execute
                    task.execute();
                }
            }, 300);

        }
    }

    private void IBAction() {
        btn_create_an_account.setOnClickListener(this);
        btn_forgot_password.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        if (v == btn_create_an_account) {
            Intent i = new Intent(CheckLogin.this, RegistrationActivity.class);
            startActivity(i);
        } else if (v == btn_forgot_password) {
            alertForgotPassword();
        }
    }

    public void alertForgotPassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Forgot Password");
        builder.setMessage("After click on send button we will send forgot link to your email.");
        builder.setCancelable(false);

// Set up the input
        inputEmail = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        inputEmail.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        builder.setView(inputEmail);

// Set up the buttons
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                forgotpasswordOTP = LoginWebservice.forgorPasswordWS(inputEmail.getText().toString(), "ForgotPassword");

                Log.d("Forgot Password Response Coming inn", Boolean.toString(forgotpasswordOTP));

                if (forgotpasswordOTP) {
                    Toast.makeText(getApplicationContext(), "Access Code sent successfully ", Toast.LENGTH_LONG).show();
                    alertConfirmOTP();
                } else {
                    Toast.makeText(getApplicationContext(), "Sorry, Invalid Email Address", Toast.LENGTH_LONG).show();
                }


                dialog.cancel();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }

    public void alertConfirmOTP() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter OTP");
        builder.setMessage("Please enter the Access code Received in your EMail inbox.");
        builder.setCancelable(false);

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("Continue", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                forgotpasswordverifyOTP = LoginWebservice.verifyForgotPasswordOTP(input.getText().toString(), inputEmail.getText().toString(), "VerifyforgotOtpNumber");

                if (forgotpasswordverifyOTP) {
                    Intent i = new Intent(CheckLogin.this, UpdatePasswordActivity.class);
                    i.putExtra("userEmail", inputEmail.getText().toString());
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid Access code, Please Try again", Toast.LENGTH_LONG).show();
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();

    }


    public class AsyncCallWS extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            //Call Web Method
            String[] totalCount;
            loginWSResponse = LoginWebservice.invokeLoginWS(userNameET.getText().toString(), passWordET.getText().toString(), "getLogin");

            if(loginWSResponse == null)
            {
                try {
                    loginWSResponse = LoginWebservice.invokeLoginWS(userNameET.getText().toString(), passWordET.getText().toString(), "getLogin");
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            if (loginWSResponse.contains("True")) {
                totalCount = loginWSResponse.split(":");
                WScount = Integer.parseInt(totalCount[1]);
                loginStatus = Boolean.parseBoolean(totalCount[0]);


            }

            return loginStatus;

        }

        //Once WebService returns response
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            //Make Progress Bar invisible
            try {

                result = loginStatus;


                //Based on Boolean value returned from WebService
                Log.w("myTag", Boolean.toString(loginStatus));
                if (result) {
                    //Thread.sleep(4000);
                    //Navigate to Home Screen
                    deviceToken = MyFirebaseMessagingService.getDeviceToken();
                    int newFeedback = newFeedbackCheck(WScount);
                    Intent intObj = new Intent(CheckLogin.this, MyFeedbackActivity.class);
                    intObj.putExtra("Username", userNameET.getText().toString());

                    if (newFeedback != WScount) {
                        intObj.putExtra("newFeedback", newFeedback);
                    }

                    startActivity(intObj);
                    finish();


                    new SetSharedPreferences().setValue(CheckLogin.this, "Username", userNameET.getText().toString());
                    new SetSharedPreferences().setValue(CheckLogin.this, "Password", passWordET.getText().toString());
                    new SetSharedPreferences().setBool(CheckLogin.this, "isLoggedIn", true);

                    UpdatedeviceToken();
                } else {
                    //Set Error message
                    statusTV.setTextColor(Color.parseColor("#FF0000"));
                    statusTV.setText("Login Failed, Invalid Credentials");
                }

            } catch (Exception e) {
                statusTV.setText("Sorry Login Failed");
                return;

            }
            //Error status is true
            return;
        }


        @Override
        //Make Progress Bar visible
        protected void onPreExecute() {
            super.onPreExecute();
            webservicePG.setVisibility(View.VISIBLE);
            /*loginStatus = LoginWebservice.invokeLoginWS(editTextUsername,editTextPassword,"getLogin");*/

        }

        @Override
        protected void onProgressUpdate(Void... values) {

            webservicePG.setVisibility(View.INVISIBLE);
        }


    }


    public int newFeedbackCheck(int WScount) {
        int current = new SetSharedPreferences().getInt(CheckLogin.this, "currentFeedbackCount");

        if (WScount > current) {
            if (current < 0) {
                new SetSharedPreferences().setInt(CheckLogin.this, "currentFeedbackCount", WScount);
                return WScount;
            } else {
                newFeedbacCount = WScount - current;

                new SetSharedPreferences().setInt(CheckLogin.this, "currentFeedbackCount", WScount);
                return newFeedbacCount;


            }
        } else {
            new SetSharedPreferences().setInt(CheckLogin.this, "currentFeedbackCount", WScount);
            return WScount;
        }


    }



    private void UpdatedeviceToken() {




        URL url = NetworkUtil.buildURL(Constant.TESTURL + "WebService1.asmx/" + "UpdateDeviceTokenID?Email="
                + new SetSharedPreferences().getValue(CheckLogin.this, "Username")
                + "&PhoneDeviceID=" + deviceToken);
        AsyncUpdateToken ta = new AsyncUpdateToken();
        ta.execute(url);

        Toast.makeText(CheckLogin.this, deviceToken, Toast.LENGTH_LONG).show();
    }

    class AsyncUpdateToken extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            String data = null;

            try {
                data = NetworkUtil.getResponse(urls[0]);
                Log.d("Data received", " " + data);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Log.d("data", data);
            return data;


        }

        @Override
        protected void onPostExecute(String result) {

            try {
                String res = result.toString();
                Log.e("Token update", "Token update:" + res);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }


    }
}





