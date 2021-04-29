package com.pallav.feedbacknative;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;

import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.*;
import com.pallav.feedbacknative.Util.GetApi;
import com.pallav.feedbacknative.Util.SetSharedPreferences;

public class CheckLogin extends AppCompatActivity implements View.OnClickListener {

    static boolean errored = false;
    Button b;
    TextView statusTV;
    EditText userNameET, passWordET,inputEmail;
    ProgressBar webservicePG;
    boolean loginStatus;
    String editTextPassword;
    boolean forgotpasswordOTP, forgotpasswordverifyOTP;

    Button btn_create_an_account, btn_forgot_password;

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
        //Display progress bar until web service invocation completes
        //Button Click Listener
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

            new Handler().postDelayed(new Runnable(){
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
        }else if (v == btn_forgot_password) {
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




               forgotpasswordOTP = LoginWebservice.forgorPasswordWS(inputEmail.getText().toString(),"ForgotPassword");

                Log.d("Forgot Password Response Coming inn", Boolean.toString( forgotpasswordOTP));

                if(forgotpasswordOTP)
                {
                    Toast.makeText(getApplicationContext(), "Access Code sent successfully ", Toast.LENGTH_LONG).show();
                    alertConfirmOTP();
                }

                else
                {
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



                forgotpasswordverifyOTP = LoginWebservice.verifyForgotPasswordOTP(input.getText().toString(),inputEmail.getText().toString() , "VerifyforgotOtpNumber");

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

    public class AsyncCallWS extends AsyncTask<Void, Void, Boolean>

    {
        @Override
        protected Boolean doInBackground(Void... voids) {
            //Call Web Method

            loginStatus = LoginWebservice.invokeLoginWS(userNameET.getText().toString(), passWordET.getText().toString(),"getLogin");
            return loginStatus;

        }

        //Once WebService returns response
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            //Make Progress Bar invisible
            try {
                Thread.sleep(2000);
                result = loginStatus;
                webservicePG.setVisibility(View.INVISIBLE);


                //Based on Boolean value returned from WebService
                Log.w("myTag", Boolean.toString(loginStatus));
                if (result) {
                    //Navigate to Home Screen
                    Intent intObj = new Intent(CheckLogin.this, MyFeedbackActivity.class);
                    intObj.putExtra("Username", userNameET.getText().toString());
                    startActivity(intObj);
                    finish();


                    new SetSharedPreferences().setValue(CheckLogin.this, "Username", userNameET.getText().toString());
                    new SetSharedPreferences().setValue(CheckLogin.this, "Password", passWordET.getText().toString());
                    new SetSharedPreferences().setBool(CheckLogin.this, "isLoggedIn", true);
                } else {
                    //Set Error message
                    statusTV.setTextColor(Color.parseColor("#FF0000"));
                    statusTV.setText("Login Failed, Invalid Credentials");
                }

            }
            catch (InterruptedException e)
            {
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


        }
    }

}

