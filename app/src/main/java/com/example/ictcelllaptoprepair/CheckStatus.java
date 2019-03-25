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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CheckStatus extends AppCompatActivity {

    private static String URL_CHECKSTATUS="http://ictcell-com.stackstaging.com/checkstatus.php";
    //Linking up the XML & Java Files
    RelativeLayout relativeLayoutStatus;
    EditText editTextStatusComplaintID;
    Button buttonCheckStatus;
    ProgressBar progressBarCheck,circularProgressBar;
    TextView textViewActualStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_status);

        editTextStatusComplaintID = findViewById(R.id.editTextStatusComplaintID);
        buttonCheckStatus = findViewById(R.id.buttonCheckStatus);
        progressBarCheck = findViewById(R.id.progressBarCheck);
        textViewActualStatus = findViewById(R.id.textViewActualStatus);
        circularProgressBar = findViewById(R.id.circularProgressBar);
        relativeLayoutStatus = findViewById(R.id.relativeLayoutStatus);

        //Hides the circular progress bar if edit text value is changed
        editTextStatusComplaintID.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relativeLayoutStatus.setVisibility(View.GONE);
            }
        });

        //Main Button to check status
        buttonCheckStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editTextStatusComplaintID.getText().toString().isEmpty())
                    Toast.makeText(CheckStatus.this, "Enter valid complaint ID", Toast.LENGTH_SHORT).show();
                else
                    checkStatus();
            }
        });
    }

    //Main check function
    public void checkStatus(){
        progressBarCheck.setVisibility(View.VISIBLE);
        buttonCheckStatus.setVisibility(View.GONE);

        final String complaintID = this.editTextStatusComplaintID.getText().toString().trim();
        final String rollnumber = SaveSharedPreference.getUserRollnumber(CheckStatus.this);

        //Main Volley Code to make request
        StringRequest request = new StringRequest(Request.Method.POST,URL_CHECKSTATUS,
                new Response.Listener<String>() {   //3rd Parameter
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success =  jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");


                            if(success.equals("0")) {
                                progressBarCheck.setVisibility(View.GONE);
                                buttonCheckStatus.setVisibility(View.VISIBLE);
                            }
                            //Successful
                            else if(success.equals("1")){

                                for(int i=0; i<jsonArray.length();i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);
                                    String status = object.getString("status").trim();
                                    //Toast.makeText(CheckStatus.this, "Successful!\n"+status, Toast.LENGTH_SHORT).show();
                                    SaveSharedPreference.setStatus(CheckStatus.this,status);
                                    progressBarCheck.setVisibility(View.GONE);
                                    buttonCheckStatus.setVisibility(View.VISIBLE);
                                    setprogress();
                                }
                            }

                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText(CheckStatus.this, "Enter valid complaint ID  ", Toast.LENGTH_SHORT).show();
                            progressBarCheck.setVisibility(View.GONE);
                            buttonCheckStatus.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {      //4th Parameter
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Displays Error Message
                        Toast.makeText(CheckStatus.this, "Error!"+error.toString(), Toast.LENGTH_SHORT).show();
                        progressBarCheck.setVisibility(View.GONE);
                        buttonCheckStatus.setVisibility(View.VISIBLE);
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
                return params;
            }
        };
        //Adds request to queue
        RequestQueue queue = Volley.newRequestQueue(CheckStatus.this);
        queue.add(request);
    }

    //Circular Progress Bar
    public void setprogress(){
        //Makes the layout visible
        relativeLayoutStatus.setVisibility(View.VISIBLE);

        String status = SaveSharedPreference.getStatus(CheckStatus.this);
        if(status.equals("1")){
            circularProgressBar.setProgress(100);
            textViewActualStatus.setText("Laptop\nReturned");
        }
        else if(status.equals("0")){
            circularProgressBar.setProgress(25);
            textViewActualStatus.setText("Complaint\nRegistered");
        }
        else if(status.equals("2")){
            circularProgressBar.setProgress(50);
            textViewActualStatus.setText("Under\nRepair");
        }
        else if(status.equals("3")){
            circularProgressBar.setProgress(75);
            textViewActualStatus.setText("Repair\nCompleted");
        }
    }
}
