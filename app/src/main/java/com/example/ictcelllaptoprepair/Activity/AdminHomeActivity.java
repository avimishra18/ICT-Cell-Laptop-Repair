package com.example.ictcelllaptoprepair.Activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.cardview.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.example.ictcelllaptoprepair.R;
import com.example.ictcelllaptoprepair.Helper.SaveSharedPreference;

public class AdminHomeActivity extends AppCompatActivity {

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

        textViewAdminUsername.setText("Welcome "+ SaveSharedPreference.getUserName(AdminHomeActivity.this));

        //on Click for Register
        cardViewAdminRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerComplaintIntent = new Intent(AdminHomeActivity.this, RegisterComplaintActivity.class);
                startActivity(registerComplaintIntent);
            }
        });

        //on Click Listener for History
        cardViewAdminUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent updateIntent = new Intent(AdminHomeActivity.this, UpdateStatusActivity.class);
                startActivity(updateIntent);
            }
        });

        //on Click Listener for History
        cardViewAdminHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyIntent = new Intent(AdminHomeActivity.this, HistoryActivity.class);
                startActivity(historyIntent);
            }
        });

        //on Click Listener For Account Settings
        cardViewUserSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userSettingIntent = new Intent(AdminHomeActivity.this, AccountSettingsActivity.class);
                startActivity(userSettingIntent);
            }
        });
    }
}
