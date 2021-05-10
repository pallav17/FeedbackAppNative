package com.pallav.feedbacknative;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.pallav.feedbacknative.Adapter.FeedbackListAdapter;
import com.pallav.feedbacknative.Adapter.SendItemsAdapter;
import com.pallav.feedbacknative.Util.Constant;
import com.pallav.feedbacknative.Util.NetworkUtil;
import com.pallav.feedbacknative.Util.SetSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class SentItemsActivity extends AppCompatActivity {

    URL url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sent_items);

        ActionBar actionbar = getSupportActionBar();
        getSupportActionBar().setTitle("");
        actionbar.setDisplayUseLogoEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setHomeButtonEnabled(true);

        actionbar.setLogo(R.drawable.actionbar_logo);


        actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));


        url = NetworkUtil.buildURL(Constant.TESTURL + "WebService1.asmx/" + "DisplaySentItemsAndroid?Email="
                + new SetSharedPreferences().getValue(SentItemsActivity.this, "Username")
                + "&Token=af9bce267343ad72bd6abe7aff58edf2");
        AsyncCallDisplaySentItems task = new AsyncCallDisplaySentItems();
        task.execute(url);
    }

        private RecyclerView recyclerView;
        private RecyclerView.Adapter mAdapter;
        private RecyclerView.LayoutManager layoutManager;
        ArrayList<HashMap<String, String>> arrData ;

        private void setRecyclerView() {
            recyclerView = (RecyclerView) findViewById(R.id.sentItems_recycler_view);
            recyclerView.setHasFixedSize(true);

            layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);

            // specify an adapter (see also next example)

            int totalCount = arrData.size();

            mAdapter = new SendItemsAdapter(arrData);


            recyclerView.setAdapter(mAdapter);


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
        protected void onPostExecute(String result) {
            try{

                JSONArray jsonArray = new JSONArray(result);
                arrData = new ArrayList<>();

                for (int i = 0; i < jsonArray.length(); i++) {
                    HashMap<String, String> map = new HashMap<>();
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        map.put("Subject", jsonObject.get("Subject").toString());
                        map.put("Description", jsonObject.get("Description").toString());
                        map.put("Suggestion", jsonObject.get("Suggestion").toString());
                        map.put("Rating", jsonObject.get("Rating").toString());
                        map.put("FirstName", jsonObject.get("FirstName").toString());
                        map.put("LastName", jsonObject.get("LastName").toString());
                        map.put("FeedbackDate", jsonObject.get("FeedbackDate").toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    arrData.add(map);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            setRecyclerView();
        }



        }
    }

 /*class SentFeedfacksItems{

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



    }*/






