package com.pallav.feedbacknative;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdatePasswordActivity extends AppCompatActivity {

   EditText edt_password,edt_confirm_password;
   Button button1;
    String regexPassword = "[A-Z]{1}[A-Za-z0-9\\W]{7,}";

    Boolean updatePassword = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        edt_password = (EditText) findViewById(R.id.edt_password);
        edt_confirm_password = (EditText) findViewById(R.id.edt_confirm_password);
        button1 = (Button) findViewById(R.id.button1);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = getIntent();

                if ((edt_password.getText().toString()).equals((edt_confirm_password.getText().toString()))) {

                    if (edt_password.getText().toString().matches(regexPassword) && edt_confirm_password.getText().toString().matches(regexPassword)) {
                        updatePassword = LoginWebservice.invokeUpdatePasswordWS(intent.getExtras().getString("userEmail"),edt_confirm_password.getText().toString(), "ForgotPassMainAndroid");
                        Log.d("updatePassword Response Coming inn", Boolean.toString(updatePassword));

                        if (updatePassword) {
                            Toast.makeText(getApplicationContext(), "Password reset Successful. Please Login with new credentials. ", Toast.LENGTH_LONG).show();
                            Intent i = new Intent(UpdatePasswordActivity.this, CheckLogin.class);
                            startActivity(i);


                        } else {
                            Toast.makeText(getApplicationContext(), "Sorry, There are issues with the Network Connection. Please try again on the Website", Toast.LENGTH_LONG).show();
                        }


                    }
                    else {
                        Toast.makeText(getApplicationContext(), "Not a valid password, Must contain  First character in Uppercase and at least 8 or more other characters", Toast.LENGTH_LONG).show();
                    }


                }

                else {
                    Toast.makeText(getApplicationContext(), "Passwords doesn't Match, Please try Again ", Toast.LENGTH_LONG).show();
                }





            }
        });


    }
        

    @Override
    public void onBackPressed() {


    }


}
