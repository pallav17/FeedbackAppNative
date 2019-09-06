package com.pallav.feedbacknative;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);


        getSupportActionBar().setTitle("");

        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayUseLogoEnabled(true);
        actionbar.setDisplayShowHomeEnabled(true);
        actionbar.setHomeButtonEnabled(true);

        actionbar.setLogo(R.drawable.actionbar_logo);


    }
}




