package com.example.ictcelllaptoprepair;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class UpdateStatus extends AppCompatActivity {

    private static String URL_STATUS="http://ictcell-com.stackstaging.com/status.php";

    //Linking up the XML & Java File
    EditText editTextStatusComplaintID,editTextStatusRollNumber;
    int radioButtonId=0;
    RadioGroup radioGroupStatus;
    RadioButton radioButtonSelected;
    Button buttonUpdateStatus;
    ProgressBar progressBarStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_status);

        editTextStatusRollNumber = findViewById(R.id.editTextStatusRollNumber);
        editTextStatusComplaintID = findViewById(R.id.editTextStatusComplaintID);
        radioGroupStatus = findViewById(R.id.radioGroupStatus);
        progressBarStatus = findViewById(R.id.progressBarStatus);
        buttonUpdateStatus = findViewById(R.id.buttonUpdateStatus);

        //On click Listener for Radio Group
        radioGroupStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                //Gets the ID of Checked Radio Button
                radioButtonId = radioGroupStatus.getCheckedRadioButtonId();
                radioButtonSelected = findViewById(radioButtonId);
            }
        });

        buttonUpdateStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editTextStatusComplaintID.getText().toString().isEmpty())
                    Toast.makeText(UpdateStatus.this, "Enter valid Complaint ID", Toast.LENGTH_SHORT).show();
                else if (editTextStatusRollNumber.getText().toString().isEmpty())
                    Toast.makeText(UpdateStatus.this, "Enter valid Roll Number", Toast.LENGTH_SHORT).show();
                else if(radioButtonId==0)
                    Toast.makeText(UpdateStatus.this, "Select status", Toast.LENGTH_SHORT).show();
                else
                    updateStatus();
            }
        });
    }

    //Main Update Code
    public void updateStatus(){
        progressBarStatus.setVisibility(View.VISIBLE);
        buttonUpdateStatus.setVisibility(View.GONE);

        final String complaintID = this.editTextStatusComplaintID.getText().toString().trim();
        final String rollnumber = this.editTextStatusRollNumber.getText().toString().trim();
        final String status = setStatus(radioButtonSelected);

        //Main Volley Code to make request
        StringRequest request = new StringRequest(Request.Method.POST,URL_STATUS,
                new Response.Listener<String>() {   //3rd Parameter
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            //Successful Register
                            if(success.equals("1")){
                                Toast.makeText(UpdateStatus.this, "Update Successful!", Toast.LENGTH_SHORT).show();
                                Intent adminHomeIntent = new Intent(UpdateStatus.this,AdminHome.class);
                                startActivity(adminHomeIntent);
                                finish();
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(UpdateStatus.this, "Error!"+e.toString(), Toast.LENGTH_SHORT).show();
                            progressBarStatus.setVisibility(View.GONE);
                            buttonUpdateStatus.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {      //4th Parameter
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Displays Error Message
                        Toast.makeText(UpdateStatus.this, "Error!"+error.toString(), Toast.LENGTH_SHORT).show();
                        progressBarStatus.setVisibility(View.GONE);
                        buttonUpdateStatus.setVisibility(View.VISIBLE);
                    }
                }
        )
        //Main body of method Stringrequest
        {
            @Override
            protected Map<String, String> getParams() {

                Map<String, String> params = new HashMap<>();
                params.put("complaintID",complaintID);
                params.put("rollnumber",rollnumber);
                params.put("status",status);
                return params;
            }
        };
        //Adds request to queue
        RequestQueue queue = Volley.newRequestQueue(UpdateStatus.this);
        queue.add(request);

    }

    //Status Select
    public String setStatus(RadioButton radioButton){
        String string = radioButton.getText().toString();
        if(string.equals("Complaint Registered"))
            string="0";
        else if(string.equals("Under Repair"))
            string="2";
        else if(string.equals("Repair Completed"))
            string="3";
        else if(string.equals("Laptop Returned"))
            string="1";
        return string;
    }
}
