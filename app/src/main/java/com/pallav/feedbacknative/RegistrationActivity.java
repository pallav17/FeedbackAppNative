package com.pallav.feedbacknative;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {


    EditText edt_firstname, edt_lastname, edt_office, edt_email, edt_password, edt_confirm_password;
    Button signupAccount;

    boolean accountSignupStatus;
    CheckLogin cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        init();


        signupAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AsyncCallSignUp task = new AsyncCallSignUp();
                //Call execute
                task.execute();

            }
            });
        }





    public class AsyncCallSignUp extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            //Call Web Method

            accountSignupStatus = LoginWebservice.invokeSignupInsertUser(edt_firstname.getText().toString(), edt_lastname.getText().toString(), edt_office.getText().toString(), edt_email.getText().toString(), edt_password.getText().toString(), "false", "false", "InsertUserData");
            return accountSignupStatus;

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

                Log.d("Insert Feedback", "Login Failed, try again");
            }
            //Error status is true
            return;
        }

        @Override
        //Make Progress Bar visible
        protected void onPreExecute() {
            super.onPreExecute();
            // webservicePG.setVisibility(View.VISIBLE);
            /*loginStatus = LoginWebservice.invokeLoginWS(editTextUsername,editTextPassword,"getLogin");*/

        }

        @Override
        protected void onProgressUpdate(Void... values) {


        }

    }

    public void alertConfirmOTP() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Access Code ");
        builder.setMessage("Please enter the Access Code Received in Email.");
        builder.setCancelable(false);



        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("Verify", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               // Intent i = new Intent(CheckLogin.this, UpdatePasswordActivity.class);
            //    startActivity(i);
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
    }


    }





