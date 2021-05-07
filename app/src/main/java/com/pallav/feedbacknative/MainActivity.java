package com.pallav.feedbacknative;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.sql.DriverPropertyInfo;

public class MainActivity extends AppCompatActivity {
    private TextView mTextMessage,unreadCount;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {


                case R.id.navigation_dashboard:
                    mTextMessage.setText(R.string.title_compose_feedback);
                    return true;

                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_feedback_inbox);
                    return true;

                case R.id.navigation_sendItems:
                    mTextMessage.setText(R.string.title_sent_items);
                    return true;

                case R.id.navigation_notifications:
                    mTextMessage.setText(R.string.title_profile);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        mTextMessage = findViewById(R.id.message);


       // initializeCountDrawer();



    }
   /* private  void initializeCountDrawer(){

        //Gravity property aligns the text
        unreadCount.setGravity(Gravity.CENTER_VERTICAL);
     unreadCount.setTypeface(null, Typeface.BOLD);
        unreadCount.setTextColor(getResources().getColor(R.color.colorAccent));
        unreadCount.setText("99+");

    }*/

}
