package com.pallav.feedbacknative;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ProgressBar;

public class splashActivity extends AppCompatActivity {
    private ProgressBar progressbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        progressbar = findViewById(R.id.progressbar);

        getSupportActionBar().hide();

        int secondsDelayed = 1;
        new Handler().postDelayed(new Runnable() {
            public void run() {
                startActivity(new Intent(splashActivity.this,CheckLogin.class));
                finish();
            }
        }, secondsDelayed * 2000);
    }
}
