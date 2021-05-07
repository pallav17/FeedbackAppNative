package com.pallav.feedbacknative;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.pallav.feedbacknative.Util.Constant;
import com.pallav.feedbacknative.Util.NetworkUtil;
import com.pallav.feedbacknative.Util.SetSharedPreferences;

import java.net.URL;

public class SentItemsActivity extends AppCompatActivity {

    URL url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_items);

        url = NetworkUtil.buildURL(Constant.TESTURL +"WebService1.asmx/" + "TokenTest1DF?Email="
                + new SetSharedPreferences().getValue(SentItemsActivity.this, "Username")
                + "&Token=af9bce267343ad72bd6abe7aff58edf2");
    AsyncCallDisplaySentItems task = new AsyncCallDisplaySentItems();
        task.execute(url);





    }


    class AsyncCallDisplaySentItems extends AsyncTask<URL, Void, String> {


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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);



        }
    }

    public class SentFeedfacksItems{

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getFeedbackSent() {
            return feedbackSent;
        }

        public void setFeedbackSent(String feedbackSent) {
            this.feedbackSent = feedbackSent;
        }

        public String getSuggestion() {
            return suggestion;
        }

        public void setSuggestion(String suggestion) {
            this.suggestion = suggestion;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public int getRating() {
            return rating;
        }

        public void setRating(int rating) {
            this.rating = rating;
        }

        String firstName, lastName,feedbackSent, suggestion, subject;
        int rating;



    }

}


