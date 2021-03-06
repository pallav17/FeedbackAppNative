package com.pallav.feedbacknative;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Rating;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.pallav.feedbacknative.Util.Constant;
import com.pallav.feedbacknative.Util.NetworkUtil;
import com.pallav.feedbacknative.Util.SetSharedPreferences;

import java.net.URL;


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

        getSupportActionBar().setTitle("");

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayUseLogoEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setHomeButtonEnabled(true);

        actionbar.setLogo(R.drawable.actionbar_logo);
        actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));


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
       // Sender_Email=(mSharedPreference.getString("Username", "Default_Value"));

        Sender_Email = ( new SetSharedPreferences().getValue(InsertFeedbackActivity.this, "Username"));
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






        //Once WebService returns response
        protected void onPostExecute(Boolean result) {
            super.onPostExecute(result);
            //Make Progress Bar invisible

            result = sendFeedbackStatus;
            // webservicePG.setVisibility(View.INVISIBLE);


            Log.w("myTag",Boolean.toString(sendFeedbackStatus));
            if(result){
                SendPushNotification();
                Log.d("Insert Feedback", " Feedback send successfully");

                Toast.makeText(getApplicationContext(), "Thanks for your feedback", Toast.LENGTH_LONG).show();

                Intent intObj = new Intent(InsertFeedbackActivity.this,SentItemsActivity.class);
                 intObj.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |  Intent.FLAG_ACTIVITY_CLEAR_TASK);
                //  intObj.putExtra("Username", userNameET.getText().toString());
                finish();
                startActivity(intObj);
            }else{

                Log.d("Insert Feedback"," Failed, try again");
            }
            //Error status is true
            return;
        }

        @Override
        //Make Progress Bar visible
        protected void onPreExecute() {
            super.onPreExecute();
           //webservicePG.setVisibility(View.VISIBLE);
             /*loginStatus = LoginWebservice.invokeLoginWS(editTextUsername,editTextPassword,"getLogin");*/

        }

        @Override
        protected void onProgressUpdate(Void... values) {


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.login_action, menu);
        return true;
    }

    private void SendPushNotification() {

        URL url = NetworkUtil.buildURL(Constant.TESTURL +"WebService1.asmx/" + "pushNotify?Email="
                +getIntent().getStringExtra("Email"));

        AsyncPushNotification ta = new AsyncPushNotification()  ;
        ta.execute(url);

        Toast.makeText(InsertFeedbackActivity.this,"Notification Sent", Toast.LENGTH_SHORT).show();
    }

}


 class AsyncPushNotification extends AsyncTask<URL, Void, String> {

    @Override
    protected String doInBackground(URL... urls) {
        String data = null;

        try {
            data = NetworkUtil.getResponse(urls[0]);
            Log.d("Data received", " "+data);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.d("data", data);
        return data;


    }

}