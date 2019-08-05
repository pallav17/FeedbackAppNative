package com.pallav.feedbacknative;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.pallav.feedbacknative.Util.GetApi;


public class MyFeedbackActivity extends AppCompatActivity {


    Gson gson = new Gson();
    String[] feedbacks;

    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    Intent InsertFeedback = new Intent(MyFeedbackActivity.this,InsertFeedbackActivity.class);
                    startActivity(InsertFeedback);
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_dashboard);

                    return true;
                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_notifications);
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
        mTextMessage = findViewById(R.id.message);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        //webView = (WebView)findViewById(R.id.webview);
        Intent getintent = getIntent();
        final String message = getintent.getStringExtra("Username");
//     Log.e("Email",message);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();
        AsyncCallDisplayFeedback  task = new AsyncCallDisplayFeedback();
        //Call execute
        task.execute();

      /*  webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                String key = "email1";
                String val = message;
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {

                    webView.evaluateJavascript("localStorage.setItem('"+ key +"','"+ val +"');", null);

                    GetApi gt = new GetApi();
                    gt.getResponse("GetFeedBackDetailNew");
                    Log.e("respone",gt.toString());

                } else {
                    webView.loadUrl("javascript:localStorage.setItem('"+ key +"','"+ val +"');");
                }
            }
        });*/
    }
        public class AsyncCallDisplayFeedback extends AsyncTask<Void, Void, Boolean> {
            @Override
            protected Boolean doInBackground(Void... voids) {
                //Call Web Method
                GetApi.invokeJSONWS("GetFeedBackDetailNew");
                return null;

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


            //Once WebService returns response
            protected void onPostExecute(Void result) {
                //super.onPostExecute(result);
                //gt.invokeJSONWS("GetFeedBackDetailNew");
                feedbacks = gson.fromJson(GetApi.responseJSON, String[].class);
                //  Toast.makeText(getApplicationContext(), "No Response", Toast.LENGTH_LONG).show();
                //Set Error message
                Log.e("response", feedbacks.toString());
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

}
