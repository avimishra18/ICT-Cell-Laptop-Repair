package com.example.ictcelllaptoprepair;

import android.content.Intent;
import android.media.session.PlaybackState;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {

    //Login Link of PHP FILE
    private static String URL_READ = "http://ictcell-com.stackstaging.com/read.php";

    //Array List Model

    //Implementing a custom list view
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        list = findViewById(R.id.historyListView);

        //Creating a JSON Array request
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.POST, URL_READ, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {

                //Model ArrayList for Custom List View of History
                ArrayList<Model> data = new ArrayList<>();
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
                            String status = jsonObject.getString("status");
                            String complaintDate = jsonObject.getString("complaintdate");
                            String repairDate = jsonObject.getString("repaireddate");
                            Model model = new Model(complaintID, rollnumber, laptopModel, serialNumber, issue, status, complaintDate, repairDate);
                            data.add(model);
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
}
