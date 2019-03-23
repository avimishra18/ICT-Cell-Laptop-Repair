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
    private static String URL_READ="http://ictcell-com.stackstaging.com/read.php";
    private static String URL_USER_READ="http://ictcell-com.stackstaging.com/userread.php";

    //Array List Model
    final ArrayList<Model> data =new ArrayList<>();

    String rollnumber,complaintID;

    //Implementing a custom list view
    ListView list;
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        list = findViewById(R.id.historyListView);

        //New String Request FOR ALL HISTORY (ADMIN)
        if(SaveSharedPreference.getUserAdmin(HistoryActivity.this).equals("1")){

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
                    new Response.Listener<String>() { //3rd Parameter
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                String success =  jsonObject.getString("success");
                                Toast.makeText(HistoryActivity.this, success, Toast.LENGTH_LONG).show();
                                JSONArray jsonArray = jsonObject.getJSONArray("read");

                                //No records found
                                if(success.equals("0")){
                                    Toast.makeText(HistoryActivity.this, "No Records Found", Toast.LENGTH_SHORT).show();
                                }
                                else if(success.equals("1")){
                                    //If Login is successful, we retrieve the data from the server
                                    for(int i=0; i<jsonArray.length();i++){

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        final String rollnumber = object.getString("rollnumber").trim();
                                        final String complaintID = object.getString("complaintID").trim();
                                        //model = new Model(rollnumber,complaintID);
                                        //Toast.makeText(HistoryActivity.this,rollnumber+"\n"+complaintID, Toast.LENGTH_SHORT).show();
                                        //data.add(model);

                                        Toast.makeText(HistoryActivity.this,"Successful",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            }catch (JSONException e){ //Catches Error if JSON type mismatch etc
                                e.printStackTrace();
                                Toast.makeText(HistoryActivity.this, "JSON:Error\n"+e.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    },
                    new Response.ErrorListener() { //4th Parameter
                        @Override
                        public void onErrorResponse(VolleyError error) { //Displays Error in volley (connectivity issues,server down) etc
                            Toast.makeText(HistoryActivity.this, "Volley:Error\n"+error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    });
        /*
        {

            @Override
            protected Map<String, String> getParams() {

                Map<String,String> params = new HashMap<>();

                //params.put("complaintID",complaintID);
                //params.put("rollnumber",rollnumber);

                return params;
            }
        };
        */
            //Adding to request queue
            RequestQueue requestQueue = Volley.newRequestQueue(HistoryActivity.this);
            requestQueue.add(stringRequest);
        }

        //For User History
        else {

            //New String Request
            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_USER_READ,
                    new Response.Listener<String>() { //3rd Parameter
                        @Override
                        public void onResponse(String response) {
                            try{
                                JSONObject jsonObject = new JSONObject(response);
                                String success =  jsonObject.getString("success");
                                JSONArray jsonArray = jsonObject.getJSONArray("read");

                                //If no records come..
                                if(success.equals("0")){
                                    Toast.makeText(HistoryActivity.this, "Success=0", Toast.LENGTH_SHORT).show();
                                }
                                else if(success.equals("1")){
                                    //If we find an record is successful, we retrieve the data from the server
                                    for(int i=0; i<jsonArray.length();i++){

                                        JSONObject object = jsonArray.getJSONObject(i);

                                        complaintID = object.getString("complaintID").trim();
                                        rollnumber = object.getString("rollnumber").trim();
                                        storeData(complaintID,rollnumber);
                                        //Toast.makeText(HistoryActivity.this,rollnumber+"\n"+complaintID, Toast.LENGTH_SHORT).show();
                                        Toast.makeText(HistoryActivity.this,model.getRollnumber()+"\n"+model.getComplaintID(), Toast.LENGTH_SHORT).show();

                                        //Toast.makeText(HistoryActivity.this, complaintID[index]+" "+rollnumber[index], Toast.LENGTH_SHORT).show();
                                        //Toast.makeText(HistoryActivity.this,"Successful",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                catch (JSONException e){ //Catches Error if JSON type mismatch, end of input at character 0 etc
                                e.printStackTrace();
                                    Toast.makeText(HistoryActivity.this, "JSON:Error\n"+e.toString(), Toast.LENGTH_SHORT).show();
                                }
                        }
                    },
                    new Response.ErrorListener() { //4th Parameter
                        @Override
                        public void onErrorResponse(VolleyError error) { //Displays Error in volley (connectivity issues,server down) etc
                            Toast.makeText(HistoryActivity.this, "Volley:Error\n"+error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    })
            {
                @Override
                protected Map<String, String> getParams() {

                    Map<String,String> params = new HashMap<>();
                    params.put("rollnumber",SaveSharedPreference.getUserRollnumber(HistoryActivity.this));
                    //Toast.makeText(HistoryActivity.this,SaveSharedPreference.getUserRollnumber(HistoryActivity.this) , Toast.LENGTH_SHORT).show();
                    return params;


                }
            };

            //Adding to request queue
            RequestQueue requestQueue = Volley.newRequestQueue(HistoryActivity.this);
            requestQueue.add(stringRequest);
        }
        //Custom Adapter for LIST VIEW
        CustomAdapter adapter = new CustomAdapter(HistoryActivity.this,0,data);
        list.setAdapter(adapter);
    }
    public void storeData(String complaintID, String rollnumber){
        model = new Model(complaintID,rollnumber,"Model","Serial Number","Issue","Status","ComplaintDate","RepairDate");
    }
}
