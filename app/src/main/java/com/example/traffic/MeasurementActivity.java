package com.example.traffic;

import android.arch.persistence.room.Room;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

import database.LocationDatabase;
import database.LocationDbObject;

public class MeasurementActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    LocationDatabase db;
    private Spinner laneNumberSpinner,junctionTypeSpinner;
    private static final String[] paths = {"Sáv kiválasztása", "item 2", "item 3"};

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measurement_activity_main);

        TextView junctionName = findViewById(R.id.junctionName);
        junctionName.setText(getIntent().getStringExtra("junctionName"));
        laneNumberSpinner = (Spinner)findViewById(R.id.laneID);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(MeasurementActivity.this,
                R.layout.spinner_layout,paths);

        adapter.setDropDownViewResource(R.layout.measurement_activity_main);
        laneNumberSpinner.setAdapter(adapter);
        laneNumberSpinner.setOnItemSelectedListener(this);


        junctionTypeSpinner = (Spinner)findViewById(R.id.junctionType);
        adapter = new ArrayAdapter<String>(MeasurementActivity.this,
                R.layout.spinner_layout,paths);

        adapter.setDropDownViewResource(R.layout.measurement_activity_main);
        junctionTypeSpinner.setAdapter(adapter);
        junctionTypeSpinner.setOnItemSelectedListener(this);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {

        switch (position) {
            case 0:
                // Whatever you want to happen when the first item gets selected
                break;
            case 1:
                // Whatever you want to happen when the second item gets selected
                break;
            case 2:
                // Whatever you want to happen when the thrid item gets selected
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // TODO Auto-generated method stub
    }
}


