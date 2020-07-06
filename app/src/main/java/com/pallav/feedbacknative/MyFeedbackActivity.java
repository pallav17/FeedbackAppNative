package com.pallav.feedbacknative;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pallav.feedbacknative.Adapter.FeedbackListAdapter;
import com.pallav.feedbacknative.Util.Constant;
import com.pallav.feedbacknative.Util.NetworkUtil;
import com.pallav.feedbacknative.Util.Services;
import com.pallav.feedbacknative.Util.SetSharedPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;


public class MyFeedbackActivity extends AppCompatActivity implements Services.webserviceAsync {


    Gson gson = new Gson();
    String[] feedbacks;
    URL url;

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent MyFeedbacks = new Intent(MyFeedbackActivity.this,MyFeedbackActivity.class);
                    startActivity(MyFeedbacks);
                    return true;

                case R.id.navigation_dashboard:
                    Intent InsertFeedback = new Intent(MyFeedbackActivity.this, EmployeesActivity.class);
                    startActivity(InsertFeedback);
                    return true;

                case R.id.navigation_notifications:
                    Intent ProfileActivity = new Intent(MyFeedbackActivity.this, ProfileActivity.class);
                    startActivity(ProfileActivity);
                    return true;

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.myfeedbacks);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayUseLogoEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setHomeButtonEnabled(true);

        actionbar.setLogo(R.drawable.actionbar_logo);


       actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));

        //webView = (WebView)findViewById(R.id.webview);
        Intent getintent = getIntent();
        final String message = getintent.getStringExtra("Username");
//     Log.e("Email",message);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        url = NetworkUtil.buildURL(Constant.CLOUDURL +"WebService1.asmx/" + "TokenTest1DF?Email="
                + new SetSharedPreferences().getValue(MyFeedbackActivity.this, "Username")
                + "&Token=af9bce267343ad72bd6abe7aff58edf2");
        AsyncCallDisplayFeedback  task = new AsyncCallDisplayFeedback();
        task.execute(url);

    // callWebServiceForGetData();



    }

    Services services;
    String userId;
    private void callWebServiceForGetData() {
        userId = "71418";
        services = new Services(MyFeedbackActivity.this, null);
        services.isShowProgress(true);
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
               JSONObject header= new JSONObject();
                try {
                    header.put("","");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                services.sendRequest(getResources().getString(R.string.base_url) + getResources().getString(R.string.employee_url) + userId
                        , null, header, null, Services.webcallid.GET_EMP);
            }
        }, 300);
    }

    @Override
    public void getResponse(String url, JSONObject result, Object status, Services.webcallid callId) {
        Log.e("result", ""+result);

        /*if (callId == Services.webcallid.GET_EMP && result != null) {

            JSONObject jsonObject = result;

            for (int i = 0; i < 100; i++) {
                HashMap<String, String>map = new HashMap<>();
                try {
                  //  map.put("name", jsonObject.get("employee_name").toString());

                    map.put("name","Feedback for Native App");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                arrData.add(map);
            }
            setRecyclerView();

        }*/
    }

    @Override
    public void getResponseWithJsonArray(String url, JSONArray result, Object status, Services.webcallid callId) {

    }

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    ArrayList<HashMap<String, String>> arrData ;

    private void setRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        // specify an adapter (see also next example)

        mAdapter = new FeedbackListAdapter(arrData);

        Collections.reverse(arrData);
        recyclerView.setAdapter(mAdapter);
    }

    class AsyncCallDisplayFeedback extends AsyncTask<URL, Void, String> {


        private String FirstName[];
        private String LastName[];
        private String Subject[];
            @Override
            protected String doInBackground(URL... urls) {
                //Call Web Method
              //  GetApi.invokeJSONWS("GetFeedBackDetailNew");
                String data = null;

                try {
                    data = NetworkUtil.getResponse(urls[0]);
                    Log.d("Magaj ni patar fadai gayi", " "+data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
               /* try{

                    JSONArray jsonArray = new JSONArray(data);
                    ArrayList<HashMap<String, String>> arrData = new ArrayList<>();

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

                    int arraylength = jsonArray.length();
                    FirstName = new String[arraylength];
                    LastName = new String[arraylength];
                    Subject = new String[arraylength];
                    // posterUrl = new String[arraylength];
                    for(int i =0; i < arraylength ; i++)
                    {
                        JSONObject jobj1 = jsonArray.getJSONObject(i);
                      //  JSONObject jobj1 = jsonArray.getJSONObject(i);
                      //  JSONObject jobj3 = jsonArray.getJSONObject(i);

                        // JSONObject jobj3 = jobj2.getJSONObject("")
                        FirstName[i] = jobj1.getString("FirstName");
                        LastName[i] = jobj1.getString("LastName");
                     Subject[i] = jobj1.getString("Subject");
                        Log.w("First Name",FirstName[i]);
                        Log.w("Last Name",LastName[i]);
                        Log.w("Subject",Subject[i]);
                    }*/

               /* } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                Log.d("data", data);
                return data;
               // LoginWebservice.invokeDisplayFeedbackWS("pallav.shah@schaeffler.com","af9bce267343ad72bd6abe7aff58edf2","TokenTest1DF" );
                //  Thread.sleep("1000.0");
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




            @Override
            //Make Progress Bar visible
            protected void onPreExecute() {
                super.onPreExecute();
              //  webservicePG.setVisibility(View.VISIBLE);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_logout:
                new SetSharedPreferences().setValue(MyFeedbackActivity.this, "Username", "");
                new SetSharedPreferences().setValue(MyFeedbackActivity.this, "Password" , "");
                new SetSharedPreferences().setBool(MyFeedbackActivity.this, "isLoggedIn" , false);

                Intent intObj = new Intent(MyFeedbackActivity.this, CheckLogin.class);
                startActivity(intObj);
                finish();
                return true;
        }
        return false;
    }
}
