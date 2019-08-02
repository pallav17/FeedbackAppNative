package com.pallav.feedbacknative;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.*;
import com.pallav.feedbacknative.Util.GetApi;

public class CheckLogin extends AppCompatActivity {

    static boolean errored = false;
    Button b;
    TextView statusTV;
    EditText userNameET , passWordET;
    ProgressBar webservicePG;
    String editTextUsername;
    boolean loginStatus;
    String editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);

        if (android.os.Build.VERSION.SDK_INT > 9)
        {
            StrictMode.ThreadPolicy policy = new
                    StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
                //Name Text control
                userNameET = (EditText) findViewById(R.id.editText1);
                passWordET = (EditText) findViewById(R.id.editText2);
                //Display Text control
                statusTV = (TextView) findViewById(R.id.tv_result);
                //Button to trigger web service invocation
                b = (Button) findViewById(R.id.button1);
                //Display progress bar until web service invocation completes
                webservicePG = (ProgressBar) findViewById(R.id.progressBar1);
                //Button Click Listener
                b.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //Check if text controls are not empty
                        if (userNameET.getText().length() != 0 && userNameET.getText().toString() != "") {
                            if(passWordET.getText().length() != 0 && passWordET.getText().toString() != ""){
                                editTextUsername = userNameET.getText().toString();
                                editTextPassword = passWordET.getText().toString();
                                statusTV.setText("");
                                //Create instance for AsyncCallWS
                                AsyncCallWS task = new AsyncCallWS();
                                //Call execute
                                task.execute();
                            }
                            //If Password text control is empty
                            else{
                                statusTV.setText("Please enter Password");
                            }
                            //If Username text control is empty
                        } else {
                            statusTV.setText("Please enter Username");
                        }
                    }
                });
            }





        public class AsyncCallWS extends AsyncTask<Void, Void, Boolean>

        {
                @Override
                protected Boolean doInBackground(Void... voids) {
                    //Call Web Method

                    loginStatus = LoginWebservice.invokeLoginWS(editTextUsername,editTextPassword,"getLogin");
                    return loginStatus;

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

                result = loginStatus;
                webservicePG.setVisibility(View.INVISIBLE);

                //Based on Boolean value returned from WebService
                    Log.w("myTag",Boolean.toString(loginStatus));
                    if(result){
                        //Navigate to Home Screen
                        Intent intObj = new Intent(CheckLogin.this,MyFeedbackActivity.class);
                        intObj.putExtra("Username", editTextUsername);
                        startActivity(intObj);

                    }else{
                        //Set Error message
                        statusTV.setText("Login Failed, try again");
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

