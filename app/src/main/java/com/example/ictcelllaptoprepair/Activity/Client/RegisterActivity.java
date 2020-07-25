package com.example.ictcelllaptoprepair.Activity.Client;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ictcelllaptoprepair.Activity.Common.LoginActivity;
import com.example.ictcelllaptoprepair.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.ictcelllaptoprepair.Config.Constants.baseServerURL;

public class RegisterActivity extends AppCompatActivity {

    //Declaring Register Edit Text & Buttons
    EditText registerRollNumber,registerUserName, registerNumber, registerPassword,registerRePassword;
    Button registerButton, loginSwitchButton;
    ProgressBar loading;

    //URL of PHP File
    private static final String URL_REGISTER= baseServerURL+"register.php";

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
        loading = findViewById(R.id.progressBarLoading);

        //Changes Intent Back to Log in Screen
        loginSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Calls out the register function
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Checking various User mistakes while registering

                //User name not correct
                if(registerRollNumber.getText().toString().length()<3)
                    Toast.makeText(RegisterActivity.this, "Enter a valid roll number", Toast.LENGTH_SHORT).show();
                else if(registerUserName.getText().toString().length()<3)
                    Toast.makeText(RegisterActivity.this, "Enter a valid username", Toast.LENGTH_SHORT).show();
                    //Number not 10 digit
                else if(registerNumber.getText().toString().length()!=10)
                    Toast.makeText(RegisterActivity.this, "Enter a valid 10 digit number", Toast.LENGTH_SHORT).show();
                else if(registerPassword.getText().toString().length()<8)
                    Toast.makeText(RegisterActivity.this, "Passwords is too short", Toast.LENGTH_SHORT).show();
                    //Password not same.
                else if(!registerPassword.getText().toString().equals(registerRePassword.getText().toString()))
                    Toast.makeText(RegisterActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                else
                    register();
            }
        });
    }

    //Register Function
    private void register(){
        loading.setVisibility(View.VISIBLE);
        registerButton.setVisibility(View.GONE);

        final String rollnumber = this.registerRollNumber.getText().toString().trim();
        final String username = this.registerUserName.getText().toString().trim();
        final String number = this.registerNumber.getText().toString().trim();
        final String password = this.registerPassword.getText().toString().trim();


        //Main Volley Code to make request
        StringRequest request = new StringRequest(Request.Method.POST,URL_REGISTER,
                new Response.Listener<String>() {   //3rd Parameter
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            //Successful Register
                            if(success.equals("1")){
                                Toast.makeText(RegisterActivity.this, "Register Successful!", Toast.LENGTH_SHORT).show();

                                //Switches Intent To Log In Screen(with Rollnumber)
                                Intent loginIntent = new Intent(RegisterActivity.this,LoginActivity.class);
                                loginIntent.putExtra("rollnumber",rollnumber);
                                startActivity(loginIntent);
                                finish();
                            }

                        }catch (JSONException e){
                            //Displays Error in JSON
                            e.printStackTrace();
                            Toast.makeText(RegisterActivity.this, "Error!"+e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            registerButton.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {      //4th Parameter
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Displays Volley Error Message
                        Toast.makeText(RegisterActivity.this, "Error!"+error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        registerButton.setVisibility(View.VISIBLE);
                    }
                })
                //Main body of method Stringrequest
        {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("rollnumber",rollnumber);
                params.put("username",username);
                params.put("password",password);
                params.put("number",number);

                return params;
            }
        };
        //Adds request to queue
        RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
        queue.add(request);
    }
}
