package com.example.ictcelllaptoprepair;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class AdminHome extends AppCompatActivity {

    TextView textViewAdminUsername;
    CardView cardViewAdminRegister,cardViewAdminUpdate,cardViewAdminHistory,cardViewUserSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);

        //Linking the XML & Java
        textViewAdminUsername = findViewById(R.id.textViewAdminUsername);
        cardViewAdminRegister = findViewById(R.id.cardViewAdminRegister);
        cardViewAdminUpdate = findViewById(R.id.cardViewAdminUpdate);
        cardViewAdminHistory = findViewById(R.id.cardViewAdminHistory);
        cardViewUserSettings = findViewById(R.id.cardViewAdminSetting);

        textViewAdminUsername.setText("Welcome "+SaveSharedPreference.getUserName(AdminHome.this));

        //on Click for Register
        cardViewAdminRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerComplaintIntent = new Intent(AdminHome.this,RegisterComplaint.class);
                startActivity(registerComplaintIntent);
            }
        });

        //on Click Listener for History
        cardViewAdminHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyIntent = new Intent(AdminHome.this,HistoryActivity.class);
                startActivity(historyIntent);
            }
        });

        //on Click Listener For Account Settings
        cardViewUserSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userSettingIntent = new Intent(AdminHome.this, AccountSettings.class);
                startActivity(userSettingIntent);
            }
        });
    }
}
