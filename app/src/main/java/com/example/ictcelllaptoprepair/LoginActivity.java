package com.example.ictcelllaptoprepair;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

public class LoginActivity extends AppCompatActivity {

    //Declaring Login Edit Text & Button
    EditText usernameEditText, passwordEditText;
    Button loginButton, registerSwitchButton;

    //Basic Splash Screen + Login Activity
    RelativeLayout layoutRelayOne,layoutRelayTwo;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //Sets Visibility when the code will be run
            layoutRelayOne.setVisibility(View.VISIBLE);
            layoutRelayTwo.setVisibility(View.VISIBLE);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        layoutRelayOne = findViewById(R.id.relativeOne);
        layoutRelayTwo = findViewById(R.id.relativeTwo);
        //Sets the delay
        handler.postDelayed(runnable,2500);


        //Linking the xml with Java file
        usernameEditText = findViewById(R.id.editTextUserName);
        passwordEditText = findViewById(R.id.editTextPassword);
        loginButton = findViewById(R.id.buttonLogin);
        registerSwitchButton = findViewById(R.id.buttonSignUp);

        registerSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
