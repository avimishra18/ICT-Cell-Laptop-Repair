package com.example.ictcelllaptoprepair;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class ScanCodeActivity extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    //Declaration of ZXingScannerView
    ZXingScannerView ScannerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ScannerView = new ZXingScannerView(this);
        setContentView(ScannerView);
    }

    @Override
    public void handleResult(Result result) {
        if(RegisterComplaint.radioButtonModel.getText().equals("HP ProBook x360 440 G1")){
            String serialnumber="";
            if(!result.toString().isEmpty()){
                for(int i=0;i<result.toString().length();i++){
                    if(result.toString().charAt(i)==' ')
                        break;
                    else
                        serialnumber+= result.toString().charAt(i);
                }
            }
            RegisterComplaint.editTextComplaintSerialNumber.setText(serialnumber);
            Toast.makeText(this, serialnumber, Toast.LENGTH_SHORT).show();
        }
        else if(RegisterComplaint.radioButtonModel.getText().equals("Third Year Laptop")){
            RegisterComplaint.editTextComplaintSerialNumber.setText(result.getText());
        }
        else
            RegisterComplaint.editTextComplaintSerialNumber.setText(result.getText());
        onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        ScannerView.stopCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ScannerView.setResultHandler(this);
        ScannerView.startCamera();
    }
}
