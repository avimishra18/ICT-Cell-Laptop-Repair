package com.example.ictcelllaptoprepair;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    //Splash Screen Timeout Time
    //private static int SPLASH_TIME_OUT=2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Splash Screen Code
        /*new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //Changes Intent to Login Screen
                Intent loginIntent = new Intent(MainActivity.this,LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        },SPLASH_TIME_OUT);
        */
    }
}

