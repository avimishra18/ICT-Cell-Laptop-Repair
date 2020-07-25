package com.example.ictcelllaptoprepair;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    //Declaring Register Edit Text & Buttons
    EditText registerRollNumber,registerUserName, registerNumber, registerPassword,registerRePassword;
    Button registerButton, loginSwitchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerRollNumber = findViewById(R.id.editTextRollNumber);
        registerUserName = findViewById(R.id.editTextUserName);
        registerNumber = findViewById(R.id.editTextNumber);
        registerPassword = findViewById(R.id.editTextPassword);
        registerRePassword = findViewById(R.id.editTextRePassword);
        registerButton = findViewById(R.id.buttonSignUp);
        loginSwitchButton = findViewById(R.id.buttonLogin);

        loginSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
