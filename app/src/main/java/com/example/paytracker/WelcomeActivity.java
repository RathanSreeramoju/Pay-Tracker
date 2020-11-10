package com.example.paytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.ActionBar;

/**
 * User can sign up by giving their personal information
 */

public class WelcomeActivity extends AppCompatActivity {

    Button bt_signupaccount;
    TextView bt_signinaccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        bt_signupaccount=(Button)findViewById(R.id.bt_signupaccount);
        bt_signinaccount=(TextView) findViewById(R.id.bt_signinaccount);

        bt_signinaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WelcomeActivity.this,LoginActivity.class);
                startActivity(intent);
            }
        });

        bt_signupaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(WelcomeActivity.this,RegistrationActivity.class);
                startActivity(intent);
            }
        });
    }
}