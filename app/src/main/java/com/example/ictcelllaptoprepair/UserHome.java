package com.example.ictcelllaptoprepair;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

public class UserHome extends AppCompatActivity {

    TextView textViewUserUsername;
    CardView cardViewUserStatus,cardViewUserHistory,cardViewUserContactUs,cardViewUserSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_home);

        //Linking up with the XML File
        textViewUserUsername = findViewById(R.id.textViewUserUsername);
        cardViewUserStatus = findViewById(R.id.cardViewUserStatus);
        cardViewUserHistory = findViewById(R.id.cardViewUserHistory);
        cardViewUserContactUs = findViewById(R.id.cardViewUserContactUs);
        cardViewUserSettings = findViewById(R.id.cardViewUserSettings);

        textViewUserUsername.setText("Welcome "+SaveSharedPreference.getUserName(UserHome.this));

        //on Click Listener for History
        cardViewUserHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent historyIntent = new Intent(UserHome.this,HistoryActivity.class);
                startActivity(historyIntent);
                finish();
            }
        });

        //on Click Listener for Contact
        cardViewUserContactUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendMailIntent = new Intent(Intent.ACTION_SEND);
                sendMailIntent.setData(Uri.parse("mailto:"));
                sendMailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{"helpdesk@kiit.ac.in"});
                sendMailIntent.putExtra(Intent.EXTRA_SUBJECT,"New Complaint");
                sendMailIntent.putExtra(Intent.EXTRA_TEXT,"Complaint Details");
                Intent intent = sendMailIntent.setType("message/rfc822");
                Intent chooser = Intent.createChooser(sendMailIntent, "Send Email");
                startActivity(chooser);
            }
        });

        //on Click Lister For Account Settings
        cardViewUserSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent userSettingIntent = new Intent(UserHome.this, AccountSettings.class);
                startActivity(userSettingIntent);
                finish();
            }
        });
    }
}
