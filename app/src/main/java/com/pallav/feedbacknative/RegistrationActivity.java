package com.pallav.feedbacknative;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;

import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class RegistrationActivity extends AppCompatActivity {


    EditText edt_firstname, edt_lastname, edt_office, edt_email, edt_password, edt_confirm_password;
    Button signupAccount;

    boolean accountSignupStatus;
    boolean otpVerifationStatus;
    CheckLogin cl;

    String regexPassword = "[A-Z]{1}[A-Za-z0-9\\W]{7,}";
    String regexEmail = "^[A-Za-z0-9]+(.|_)+[A-Za-z0-9]+@+schaeffler.com$";
    String regexText = "^[A-za-z ]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        getSupportActionBar().setTitle("");

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayUseLogoEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setHomeButtonEnabled(true);

        actionbar.setLogo(R.drawable.actionbar_logo);
        actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));

        init();


        signupAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AsyncCallSignUp task = new AsyncCallSignUp();
                //Call execute
                if(validate())
                {
                    task.execute();
                }


            }
            });
        }




    public class AsyncCallSignUp extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            //Call Web Method

              if(accountSignupStatus)
              {
                  return true;
              }
              return false;
            }



        //Once WebService returns response
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            //Make Progress Bar invisible

            result = accountSignupStatus;
            // webservicePG.setVisibility(View.INVISIBLE);


            Log.w("myTag", Boolean.toString(accountSignupStatus));
            if (result) {
                Log.d("Account Created", "Account Created successfully");

                Toast.makeText(getApplicationContext(), "Account Created  successfully ", Toast.LENGTH_LONG).show();
                alertConfirmOTP();

              //  Intent intObj = new Intent(InsertFeedbackActivity.this, MyFeedbackActivity.class);
                //  intObj.putExtra("Username", userNameET.getText().toString());
               // startActivity(intObj);
            } else {

                Toast.makeText(getApplicationContext(), "Failed!! Account already exists or there are network issues in Device. ", Toast.LENGTH_LONG).show();
            }

            //Error status is true
            return;
        }

        @Override
        //Make Progress Bar visible
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected void onProgressUpdate(Void... values) {


        }

    }

    public void alertConfirmOTP() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Access Code ");
        builder.setMessage("Please enter the Access Code Received in Email. Don't tap the back button");
        builder.setCancelable(false);



        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("Verify", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                otpVerifationStatus = LoginWebservice.verifyOTP(input.getText().toString(),"VerifyOtpNumber");

                Log.e("Response Coming inn", Boolean.toString( otpVerifationStatus));

                if(otpVerifationStatus)
                {
                    Toast.makeText(getApplicationContext(), "Account Verified successfully ", Toast.LENGTH_LONG).show();
                    Intent i = new Intent(RegistrationActivity.this, CheckLogin.class);
                    startActivity(i);
                }

                else
                {
                    Toast.makeText(getApplicationContext(), "Sorry, Could not verify that Access Code ", Toast.LENGTH_LONG).show();
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


    private void init() {
        edt_firstname = (EditText) findViewById(R.id.edt_firstname);
        edt_lastname = (EditText) findViewById(R.id.edt_lastname);
        edt_office = (EditText) findViewById(R.id.edt_office);
        edt_email = (EditText) findViewById(R.id.edt_email);
        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_confirm_password = (EditText) findViewById(R.id.edt_confirm_password);
        signupAccount = (Button) findViewById(R.id.button1);

        AlertDialog alertDialog = new AlertDialog.Builder(RegistrationActivity.this).create();
        alertDialog.setTitle("Tip");
        alertDialog.setMessage("Before you register, please make sure you have instant access to your Schaeffler Email inbox. A four digit access code will be sent to your Email for verification");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();

    }


    private Boolean validate(){

        if(edt_firstname.getText().toString().matches(regexText) && edt_lastname.getText().toString().matches(regexText) && edt_office.getText().toString().matches(regexText) ) {
            if (edt_email.getText().toString().matches(regexEmail)) {

                if (edt_password.getText().toString().matches(regexPassword) && edt_confirm_password.getText().toString().matches(regexPassword)) {
                    accountSignupStatus = LoginWebservice.invokeSignupInsertUser(edt_firstname.getText().toString(), edt_lastname.getText().toString(), edt_office.getText().toString(), edt_email.getText().toString(), edt_password.getText().toString(), "false", "false", "InsertUserData");
                    return true;

                } else {
                    Toast.makeText(getApplicationContext(), "Not a valid password, Must contain  First character in Uppercase and at least 8 or more other characters", Toast.LENGTH_LONG).show();

                }

            } else {
                Toast.makeText(getApplicationContext(), "Not a valid Email, is should have a Schaeffler Domain", Toast.LENGTH_LONG).show();

            }
        }

        else {
            Toast.makeText(getApplicationContext(), "Not a valid First Name or Last Name or Office Location", Toast.LENGTH_LONG).show();

        }

            return false;
    }
}





