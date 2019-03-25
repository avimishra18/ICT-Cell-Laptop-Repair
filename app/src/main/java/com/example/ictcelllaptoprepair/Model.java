package com.example.ictcelllaptoprepair;

import android.widget.Toast;

public class Model {

    private String complaintID,rollnumber,model,serialnumber,issue,status,complaintdate,repaireddate;

    //Constructor
    public Model(String complaintID, String rollnumber, String model, String serialnumber, String issue, String status, String complaintdate, String repaireddate) {
        this.complaintID = complaintID;
        this.rollnumber = rollnumber;
        this.model = model;
        this.serialnumber = serialnumber;
        this.issue = issue;
        this.status = status;
        this.repaireddate = repaireddate;

        String output="";
        for(int i=0;i<complaintdate.length();i++){
            if(complaintdate.charAt(i)==' ')
                break;
            else
                output += complaintdate.charAt(i);
        }
        this.complaintdate = output;
    }

    //Getters
    public String getComplaintID() {
        return complaintID;
    }

    public String getRollnumber() {
        return rollnumber;
    }

    public String getModel() {
        return model;
    }

    public String getSerialnumber() {
        return serialnumber;
    }

    public String getIssue() {
        return issue;
    }

    public String getStatus() {
        return status;
    }

    public String getComplaintdate() {
        return complaintdate;
    }

    public String getRepaireddate() {
        return repaireddate;
    }
}
