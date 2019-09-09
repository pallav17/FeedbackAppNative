package com.pallav.feedbacknative;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.pallav.feedbacknative.Util.SetSharedPreferences;


public class ProfileActivity extends AppCompatActivity {

    TextView txtEmailValue;

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
        actionbar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#ffffff")));

        txtEmailValue = (TextView)findViewById(R.id.txtEmailValue);

        txtEmailValue.setText(new SetSharedPreferences().getValue(ProfileActivity.this, "Username"));


    }
}




