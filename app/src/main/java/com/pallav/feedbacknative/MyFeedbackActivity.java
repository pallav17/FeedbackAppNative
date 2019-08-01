package com.pallav.feedbacknative;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.pallav.feedbacknative.Util.GetApi;


public class MyFeedbackActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.myfeedbacks);

        Intent getintent = getIntent();
        String message = getintent.getStringExtra("Username");
//     Log.e("Email",message);
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();

        GetApi gt = new GetApi();

        gt.getResponse("GetFeedBackDetailNew");




    }
}
