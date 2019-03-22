package com.example.ictcelllaptoprepair;

public class Model {

    private String complaintID,rollnumber,model,serialnumber,issue,status,complaintdate,repaireddate;

    //Constructor
    public Model(String complaintID, String rollnumber, String model, String serialnumber, String issue,
                 String status, String complaintdate, String repaireddate) {
        this.complaintID = complaintID;
        this.rollnumber = rollnumber;
        this.model = model;
        this.serialnumber = serialnumber;
        this.issue = issue;
        this.status = status;
        this.complaintdate = complaintdate;
        this.repaireddate = repaireddate;
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
