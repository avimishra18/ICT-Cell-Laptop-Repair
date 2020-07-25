package com.example.ictcelllaptoprepair.Activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.ictcelllaptoprepair.Adapter.CustomAdapter;
import com.example.ictcelllaptoprepair.Model.Complaint;
import com.example.ictcelllaptoprepair.Helper.MySingleton;
import com.example.ictcelllaptoprepair.R;
import com.example.ictcelllaptoprepair.Helper.SaveSharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.ictcelllaptoprepair.Config.Constants.baseServerURL;

public class HistoryActivity extends AppCompatActivity {

    //Login Link of PHP FILE
    private static final String URL_READ = baseServerURL+"read.php";

    //Relative Layout for more detials
    RelativeLayout relativeItemOnClick;

    //Implementing a custom list view
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        list = findViewById(R.id.historyListView);
        //relativeItemOnClick = findViewById(R.id.relativeItemOnClick);

        //Creating a JSON Array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL_READ, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                //Model ArrayList for Custom List View of History
                ArrayList<Complaint> data = new ArrayList<>();
                int count = 0;

                while (count < response.length()) {

                    try {

                        JSONObject jsonObject = response.getJSONObject(count);
                        //Gets the roll number
                        String rollnumber = jsonObject.getString("rollnumber");
                        //If Roll Number of User || Operator is Admin
                        if(SaveSharedPreference.getUserRollnumber(HistoryActivity.this).equals(rollnumber) || SaveSharedPreference.getUserAdmin(HistoryActivity.this).equals("1")) {

                            String complaintID = jsonObject.getString("complaintID");
                            String laptopModel = jsonObject.getString("model");
                            String serialNumber = jsonObject.getString("serialnumber");
                            String issue = jsonObject.getString("issue");
                            String status = setStatus(jsonObject.getString("status"));
                            String complaintDate = jsonObject.getString("complaintdate");
                            String repairDate = jsonObject.getString("repaireddate");
                            Complaint complaint = new Complaint(complaintID, rollnumber, laptopModel, serialNumber, issue, status, complaintDate, repairDate);
                            data.add(complaint);
                        }
                        count++;

                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(HistoryActivity.this, "Error!\n" + e.toString(), Toast.LENGTH_SHORT).show();
                    }

                    //Custom Adapter for LIST VIEW
                    CustomAdapter adapter = new CustomAdapter(HistoryActivity.this, 0, data);
                    list.setAdapter(adapter);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(HistoryActivity.this, "Volley Error\n" + error, Toast.LENGTH_SHORT).show();
                error.printStackTrace();
            }
        }
        );
        //Adds the request to the queue
        MySingleton.getInstance(HistoryActivity.this).addToRequestQueue(jsonArrayRequest);
    }

    //Circular Progress Bar
    public String setStatus(String status){

        String output="";
        if(status.equals("1")){
            //circularProgressBar.setProgress(100);
            output = ("Laptop Returned");
        }
        else if(status.equals("0")){
            //circularProgressBar.setProgress(25);
            output = ("Complaint Registered");
        }
        else if(status.equals("2")){
            //circularProgressBar.setProgress(50);
            output = ("Under Repair");
        }
        else if(status.equals("3")){
            //circularProgressBar.setProgress(75);
            output = ("Repair Completed");
        }
        return output;
    }
}
