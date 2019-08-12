package com.pallav.feedbacknative;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;


public class InsertFeedbackActivity extends AppCompatActivity {

    EditText your_full_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.insertfeedback);

        init();
        IBAction();

        your_full_name.setText(InsertFeedbackActivity.this.getIntent().getStringExtra("name"));

    }

    private void init() {
        your_full_name = (EditText) findViewById(R.id.your_full_name);
    }

    private void IBAction() {
    }
}