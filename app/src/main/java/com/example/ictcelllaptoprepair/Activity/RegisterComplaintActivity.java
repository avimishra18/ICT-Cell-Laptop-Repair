package com.example.ictcelllaptoprepair;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class RegisterComplaint extends AppCompatActivity {

    int radioButtonId=0;
    RadioGroup radioGroupModel;
    ProgressBar progressBarComplaint;
    EditText editTextComplaintIssue,editTextComplainRollNumber;
    Button buttonRegisterComplaint, buttonScanCode;

    //Public Static to get Scanner Code
    public static RadioButton radioButtonModel;
    public static EditText editTextComplaintSerialNumber;

    //PHP FILE LINK
    private static String URL_NEWCOMPLAINT="http://ictcell-com.stackstaging.com/newcomplaint.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_complaint);

        //Linking up the XML & Java Files
        radioGroupModel = findViewById(R.id.radioGroupModel);
        progressBarComplaint = findViewById(R.id.progressBarComplaint);

        editTextComplainRollNumber = findViewById(R.id.editTextComplainRollNumber);
        editTextComplaintIssue = findViewById(R.id.editTextComplaintIssue);
        editTextComplaintSerialNumber = findViewById(R.id.editTextComplaintSerialNumber);

        buttonRegisterComplaint = findViewById(R.id.buttonRegisterComplaint);
        buttonScanCode = findViewById(R.id.buttonScanCode);

        //On click Listener for Radio Group
        radioGroupModel.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                //Gets the ID of Checked Radio Button
                radioButtonId = radioGroupModel.getCheckedRadioButtonId();
                radioButtonModel = findViewById(radioButtonId);
            }
        });

        //On click Listener for scanning QR code
        buttonScanCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ScanCodeIntent = new Intent(RegisterComplaint.this,ScanCodeActivity.class);
                startActivity(ScanCodeIntent);
            }
        });

        //On click Listener for registering complaint
        buttonRegisterComplaint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Various User Mistakes
                if(editTextComplainRollNumber.getText().toString().length()<3)
                    Toast.makeText(RegisterComplaint.this, "Enter a valid roll number", Toast.LENGTH_SHORT).show();
                else if(radioButtonId==0)
                    Toast.makeText(RegisterComplaint.this, "Please select the model", Toast.LENGTH_SHORT).show();
                else if(editTextComplaintSerialNumber.getText().toString().length()<3)
                    Toast.makeText(RegisterComplaint.this, "Enter a valid serial number", Toast.LENGTH_SHORT).show();
                else if (editTextComplaintIssue.getText().toString().length()<3)
                    Toast.makeText(RegisterComplaint.this, "Please type the issue", Toast.LENGTH_SHORT).show();
                else
                    newComplaint();
            }
        });
    }

    //Main New Complaint Register Function
    public void newComplaint(){

        //Progress Bar when clicked
        progressBarComplaint.setVisibility(View.VISIBLE);
        buttonRegisterComplaint.setVisibility(View.GONE);

        //Getting Final Strings of details to be send to the Server
        final String rollnumber = editTextComplainRollNumber.getText().toString();
        final String model = radioButtonModel.getText().toString();
        final String serialnumber = editTextComplaintSerialNumber.getText().toString();
        final String issue = editTextComplaintIssue.getText().toString();



        //Main Volley Code
        StringRequest request = new StringRequest(Request.Method.POST, URL_NEWCOMPLAINT,
                new Response.Listener<String>() { //3rd Parameter
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");

                            if(success.equals("1")){

                                finish();
                                Toast.makeText(RegisterComplaint.this, "Complaint Registered", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RegisterComplaint.this, "Error!"+e.toString(), Toast.LENGTH_SHORT).show();
                            progressBarComplaint.setVisibility(View.GONE);
                            buttonRegisterComplaint.setVisibility(View.VISIBLE);
                        }

                    }
                },
                new Response.ErrorListener() { //4th Parameter
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(RegisterComplaint.this, "Error!"+error.toString(), Toast.LENGTH_SHORT).show();
                        progressBarComplaint.setVisibility(View.GONE);
                        buttonRegisterComplaint.setVisibility(View.VISIBLE);
                    }
                }
        )
        //MAIN VOLLEY CODE
        {
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<>();
                params.put("rollnumber",rollnumber);
                params.put("model",model);
                params.put("serialnumber",serialnumber);
                params.put("issue",issue);
                return params;
            }
        };
        //Adds request to queue
        RequestQueue queue = Volley.newRequestQueue(RegisterComplaint.this);
        queue.add(request);
    }
}
