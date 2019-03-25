package com.example.ictcelllaptoprepair;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Model> dataList;

    RelativeLayout relativeItemOnClick;
    TextView textViewAdapterComplaintID,textViewAdapterRollNumber,textViewAdapterStatus,textViewAdapterComplaintDate;
    TextView textViewAdapterRepairDate,textViewAdapterModel,textViewAdapterSerialNumber,textViewAdapterIssue;

    //Constructor of Custom Adapter
    public CustomAdapter(Context context, int resource, ArrayList<Model> objects) {
        super(context, resource, objects);
        this.context = context;
        this.dataList = objects;
    }

    @NonNull
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){
        if(convertView==null)
            convertView = LayoutInflater.from(context).inflate(R.layout.history_item_layout,parent,false);

        //Linking up the UI of ListView to Current Object
        textViewAdapterComplaintID = convertView.findViewById(R.id.textViewAdapterComplaintID);
        textViewAdapterRollNumber = convertView.findViewById(R.id.textViewAdapterRollNumber);
        textViewAdapterStatus = convertView.findViewById(R.id.textViewAdapterStatus);
        textViewAdapterComplaintDate = convertView.findViewById(R.id.textViewAdapterComplaintDate);
        textViewAdapterRepairDate = convertView.findViewById(R.id.textViewAdapterRepairDate);
        textViewAdapterModel = convertView.findViewById(R.id.textViewAdapterModel);
        textViewAdapterSerialNumber = convertView.findViewById(R.id.textViewAdapterSerialNumber);
        textViewAdapterIssue = convertView.findViewById(R.id.textViewAdapterIssue);
        relativeItemOnClick = convertView.findViewById(R.id.relativeItemOnClick);


        Model currentOb = dataList.get(position);

        textViewAdapterComplaintID.setText(currentOb.getComplaintID());
        textViewAdapterRollNumber.setText("ROLL NUMBER : "+currentOb.getRollnumber());
        textViewAdapterComplaintDate.setText("COMPLAINT DATE : "+currentOb.getComplaintdate());
        textViewAdapterIssue.setText("ISSUE : "+currentOb.getIssue());
        textViewAdapterModel.setText("MODEL : "+currentOb.getModel());
        textViewAdapterSerialNumber.setText("SERIAL NUMBER : "+currentOb.getSerialnumber());
        textViewAdapterStatus.setText("STATUS : "+currentOb.getStatus());

        if(currentOb.getRepaireddate().isEmpty())
            textViewAdapterRepairDate.setText("");
        else
            textViewAdapterRepairDate.setText("REPAIRED DATE : "+currentOb.getRepaireddate());

        return convertView;
    }
}
