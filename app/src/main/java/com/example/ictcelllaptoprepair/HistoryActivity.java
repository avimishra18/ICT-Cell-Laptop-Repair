package com.example.ictcelllaptoprepair;

import android.media.session.PlaybackState;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    //Implementing a custom list view
    ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        list = findViewById(R.id.historyListView);
        final ArrayList<Model> data =new ArrayList<>();
        //data.add(new Model());

        CustomAdapter adapter = new CustomAdapter(HistoryActivity.this,0,data);
        list.setAdapter(adapter);
    }
}
