package com.example.ictcelllaptoprepair;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class LoginActivity extends AppCompatActivity {

    RelativeLayout layoutRelayOne,layoutRelayTwo;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
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

        handler.postDelayed(runnable,2500);
    }
}
