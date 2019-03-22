package com.example.ictcelllaptoprepair;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    //Declaring Login Edit Text & Button
    EditText usernameEditText, passwordEditText;
    TextInputLayout passwordInputLayout;
    Button loginButton, registerSwitchButton;
    ProgressBar progressBar;

    //Login Link of PHP FILE
    //LOCAL LINK: private static String URL_LOGIN="http://10.5.223.31/volley/login.php";
    private static String URL_LOGIN="http://ictcell-com.stackstaging.com/login.php";

    //Basic Splash Screen + Login Activity
    RelativeLayout layoutRelayOne,layoutRelayTwo;
    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //Shared Preference To Keep User Logged In
            if(SaveSharedPreference.getUserRollnumber(LoginActivity.this).length() != 0) {

                if(SaveSharedPreference.getUserAdmin(LoginActivity.this).equals("1")) {
                    Intent adminHomeIntent = new Intent(LoginActivity.this, AdminHome.class);
                    startActivity(adminHomeIntent);
                    finish();
                }
                else {
                    Intent userHomeIntent = new Intent(LoginActivity.this, UserHome.class);
                    startActivity(userHomeIntent);
                    finish();
                }
            }
            //Sets Visibility when user is not logged in
            else {

                layoutRelayOne.setVisibility(View.VISIBLE);
                layoutRelayTwo.setVisibility(View.VISIBLE);
            }
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
        progressBar = findViewById(R.id.progressBarLoading);
        passwordInputLayout = findViewById(R.id.widgetPassword);

        //Switches Intent to Register Activity
        registerSwitchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        //Login Button
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString().trim();
                String password = passwordEditText.getText().toString().trim();

                if(!username.isEmpty())
                    login(username,password);
                else
                    usernameEditText.setError("Please insert email");
            }
        });

        //Intent after successful registration
        final Intent loginIntent = getIntent();
        final String usernameReceived = loginIntent.getStringExtra("rollnumber");
        usernameEditText.setText(usernameReceived);
    }

    //Definition of Login Function
    private void login(final String roll, final String pass){
        progressBar.setVisibility(View.VISIBLE);
        //loginButton.setVisibility(View.GONE);

        //New String Request
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() { //3rd Parameter
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success =  jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");

                            //If password entered doesn't matches
                            if(success.equals("0")){
                                passwordInputLayout.setError("Incorrect Password");
                                progressBar.setVisibility(View.GONE);
                                loginButton.setVisibility(View.VISIBLE);
                            }
                            else if(success.equals("1")){
                                //If Login is successful, we retrieve the data from the server
                                for(int i=0; i<jsonArray.length();i++){

                                    JSONObject object = jsonArray.getJSONObject(i);


                                    String username = object.getString("username").trim();
                                    String rollnumber = object.getString("rollnumber").trim();
                                    String number = object.getString("number").trim();
                                    String password = object.getString("password").trim();
                                    String admin = object.getString("admin").trim();

                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(LoginActivity.this,"Login Successful\n"+rollnumber+"\n"+number+"\n"+admin,Toast.LENGTH_SHORT).show();

                                    //Storing data in Shared preference if Successful
                                    SaveSharedPreference.setUserName(LoginActivity.this,username);
                                    SaveSharedPreference.setUserRollnumber(LoginActivity.this,rollnumber);
                                    SaveSharedPreference.setUserNumber(LoginActivity.this,number);
                                    SaveSharedPreference.setUserRole(LoginActivity.this,admin);
                                    SaveSharedPreference.setUserPassword(LoginActivity.this,password);


                                    //Switching Intent to Dashboard(HOME)
                                    if(admin.equals("0")){
                                        Intent userHomeIntent = new Intent(LoginActivity.this, UserHome.class);
                                        startActivity(userHomeIntent);
                                        finish();
                                    }
                                    else if(admin.equals("1")) {
                                        Intent adminHomeIntent = new Intent(LoginActivity.this, AdminHome.class);
                                        startActivity(adminHomeIntent);
                                        finish();
                                    }
                                }
                            }
                        }catch (JSONException e){ //Catches Error if JSON type mismatch etc
                            e.printStackTrace();
                            progressBar.setVisibility(View.GONE);
                            loginButton.setVisibility(View.VISIBLE);
                            //Condition of Error when username not in DATABASE
                            if(e.toString().trim().equals("org.json.JSONException: End of input at character 0 of")){
                                usernameEditText.setError("Incorrect Username");
                            }
                            else
                                Toast.makeText(LoginActivity.this, "JSON:Error\n"+e.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() { //4th Parameter
                    @Override
                    public void onErrorResponse(VolleyError error) { //Displays Error in volley (connectivity issues,server down) etc
                        progressBar.setVisibility(View.GONE);
                        loginButton.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, "Volley:Error\n"+error.toString(), Toast.LENGTH_SHORT).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {

                Map<String,String> params = new HashMap<>();

                params.put("rollnumber",roll);
                params.put("password",pass);

                return params;
            }
        };
        //Adding to request queue
        RequestQueue requestQueue = Volley.newRequestQueue(LoginActivity.this);
        requestQueue.add(stringRequest);
    }
}
