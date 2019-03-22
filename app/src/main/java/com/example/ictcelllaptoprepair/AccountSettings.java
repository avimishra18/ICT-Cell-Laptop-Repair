package com.example.ictcelllaptoprepair;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AccountSettings extends AppCompatActivity {

    //PHP FILE URL
    private static String URL_UPDATE="http://ictcell-com.stackstaging.com/edit_detail.php";

    //Text View which show various details
    RelativeLayout relativeAccountUpdate;
    TextView textViewSettingUserName,textViewSettingRollNumber,textViewSettingNumber;
    EditText editTextUpdateUserName,editTextUpdateNumber,editTextUpdatePassword,editTextUpdateRePassword;
    ProgressBar progressBarSettingLoading;
    Button buttonUpdate,buttonConfirmUpdate,logOutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        //Linking up the Java & XML Files
        relativeAccountUpdate = findViewById(R.id.relativeAccountUpdate);

        textViewSettingUserName = findViewById(R.id.textViewSettingUserName);
        textViewSettingRollNumber = findViewById(R.id.textViewSettingRollNumber);
        textViewSettingNumber = findViewById(R.id.textViewSettingUserNumber);

        editTextUpdateUserName = findViewById(R.id.editTextUpdateUserName);
        editTextUpdateNumber = findViewById(R.id.editTextUpdateNumber);
        editTextUpdatePassword = findViewById(R.id.editTextUpdatePassword);
        editTextUpdateRePassword = findViewById(R.id.editTextUpdateRePassword);

        progressBarSettingLoading = findViewById(R.id.progressBarSettingLoading);
        buttonUpdate = findViewById(R.id.buttonUpdate);
        buttonConfirmUpdate = findViewById(R.id.buttonConfirmUpdate);
        logOutButton = findViewById(R.id.buttonLogOut);

        //Setting the data
        textViewSettingRollNumber.setText("ROLL NUMBER : "+ SaveSharedPreference.getUserRollnumber(this));
        textViewSettingUserName.setText("USER NAME : "+ SaveSharedPreference.getUserName(this));
        textViewSettingNumber.setText("NUMBER : "+ SaveSharedPreference.getUserNumber(this));

        //Log Out Button
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
            }
        });

        //Update Button
        buttonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Changing visibility of various UI
                buttonUpdate.setVisibility(View.GONE);
                relativeAccountUpdate.setVisibility(View.VISIBLE);
            }
        });

        //Confirm Update Button
        buttonConfirmUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //NOT WORKING
                //Setting Text To Previous Values
                editTextUpdateUserName.setHint(SaveSharedPreference.getUserName(AccountSettings.this));
                editTextUpdateNumber.setHint(SaveSharedPreference.getUserNumber(AccountSettings.this));


                //Various User Error
                if(editTextUpdateUserName.getText().toString().length()<3)
                   Toast.makeText(AccountSettings.this, "Enter a valid username", Toast.LENGTH_SHORT).show();
                else if(editTextUpdateNumber.getText().toString().length()!=10)
                    Toast.makeText(AccountSettings.this, "Enter a valid 10 digit number", Toast.LENGTH_SHORT).show();
                else if(editTextUpdatePassword.getText().toString().length()<8)
                    Toast.makeText(AccountSettings.this, "Passwords is too short", Toast.LENGTH_SHORT).show();
                else if(!editTextUpdatePassword.getText().toString().equals(editTextUpdateRePassword.getText().toString()))
                    Toast.makeText(AccountSettings.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                else
                    update();
            }
        });
    }

    //Log Out Function
    private void logOut(){
        SaveSharedPreference.getSharedPreferences(AccountSettings.this).edit().clear().apply();
        Intent loginIntent = new Intent(AccountSettings.this,LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    //Main Update Function
    private void update(){
        progressBarSettingLoading.setVisibility(View.VISIBLE);
        buttonConfirmUpdate.setVisibility(View.GONE);

        final String username = this.editTextUpdateUserName.getText().toString().trim();
        final String number = this.editTextUpdateNumber.getText().toString().trim();
        final String password = this.editTextUpdatePassword.getText().toString().trim();


        //Main Volley Code to make request
        StringRequest request = new StringRequest(Request.Method.POST,URL_UPDATE,
                new Response.Listener<String>() {   //3rd Parameter
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            //Successful Register
                            if(success.equals("1")){
                                Toast.makeText(AccountSettings.this, "Update Successful!", Toast.LENGTH_SHORT).show();
                                //Logs Out to save details
                                logOut();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(AccountSettings.this, "Error!"+e.toString(), Toast.LENGTH_SHORT).show();
                            progressBarSettingLoading.setVisibility(View.GONE);
                            buttonConfirmUpdate.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {      //4th Parameter
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Displays Error Message
                        Toast.makeText(AccountSettings.this, "Error!"+error.toString(), Toast.LENGTH_SHORT).show();
                        progressBarSettingLoading.setVisibility(View.GONE);
                        buttonConfirmUpdate.setVisibility(View.VISIBLE);
                    }
                }
        )
                //Main body of method Stringrequest
        {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("rollnumber",SaveSharedPreference.getUserRollnumber(AccountSettings.this));
                params.put("username",username);
                params.put("password",password);
                params.put("number",number);

                return params;
            }
        };
        //Adds request to queue
        RequestQueue queue = Volley.newRequestQueue(AccountSettings.this);
        queue.add(request);
    }
}
