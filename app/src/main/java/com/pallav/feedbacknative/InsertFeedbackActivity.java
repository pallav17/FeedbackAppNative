package com.pallav.feedbacknative;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Rating;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.pallav.feedbacknative.Util.SetSharedPreferences;


public class InsertFeedbackActivity extends AppCompatActivity {

    EditText your_full_name, subject, likes, suggestions;
    RatingBar ratingBar;
    Button sendFeedback;
    boolean sendFeedbackStatus;
    String ratingValue;
    String Sender_Email;
    CheckLogin cl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertfeedback);

        init();


        your_full_name.setText(InsertFeedbackActivity.this.getIntent().getStringExtra("name"));


        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {

                double value = (Double.valueOf(rating));
                int result = (int) Math.ceil(value);

                ratingValue = (String.valueOf(result));

                Log.d("Rating_value ", ratingValue);
            }
        });

        CheckLogin cl = new CheckLogin();

        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        Sender_Email=(mSharedPreference.getString("Username", "Default_Value"));
        Toast.makeText(getApplicationContext(), Sender_Email, Toast.LENGTH_SHORT).show();




        sendFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AsyncCallWS task = new AsyncCallWS();
                //Call execute
                task.execute();

            }
        });
    }

    private void init() {
        your_full_name = (EditText) findViewById(R.id.your_full_name);
        subject = (EditText) findViewById(R.id.subject);
        likes =  (EditText) findViewById(R.id.likes);
        suggestions = (EditText) findViewById(R.id.suggestions);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        sendFeedback = (Button) findViewById(R.id.sendFeedback);
    }



    public class AsyncCallWS extends AsyncTask<Void, Void, Boolean>

    {
        @Override
        protected Boolean doInBackground(Void... voids) {
            //Call Web Method


            sendFeedbackStatus = LoginWebservice.invokeInsertFeedbackWS(subject.getText().toString(), getIntent().getStringExtra("Email"), likes.getText().toString(), suggestions.getText().toString(),Sender_Email,ratingValue,"InsertFeedbackDataNew");
            return sendFeedbackStatus;

        }


               /* //Once WebService returns response
                protected void onPostExecute(Boolean result) {
                    //Make Progress Bar invisible
                    webservicePG.setVisibility(View.INVISIBLE);
                    Intent intObj = new Intent(CheckLogin.this,MainActivity.class);

                    //Error status is false
                    if(!errored){
                        //Based on Boolean value returned from WebService
                        Log.w("myTag",Boolean.toString(loginStatus));

                       // Log.w("myTag",Boolean.toString(result));
                        if(loginStatus){
                            //Navigate to Home Screen
                            startActivity(intObj);
                        }else{
                            //Set Error message
                            statusTV.setText("Login Failed, try again");
                        }
                        //Error status is true
                    }else{
                        statusTV.setText("Error occured in invoking webservice");
                    }
                    //Re-initialize Error Status to False
                    errored = false;
                }*/



        //Once WebService returns response
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            //Make Progress Bar invisible

            result = sendFeedbackStatus;
            // webservicePG.setVisibility(View.INVISIBLE);


            Log.w("myTag",Boolean.toString(sendFeedbackStatus));
            if(result){
                Log.d("Insert Feedback", " Feedback send successfully");

                Toast.makeText(getApplicationContext(), "Feedback send successfully ", Toast.LENGTH_LONG).show();

                Intent intObj = new Intent(InsertFeedbackActivity.this,MyFeedbackActivity.class);
                //  intObj.putExtra("Username", userNameET.getText().toString());
                startActivity(intObj);
            }else{

                Log.d("Insert Feedback","Login Failed, try again");
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





}