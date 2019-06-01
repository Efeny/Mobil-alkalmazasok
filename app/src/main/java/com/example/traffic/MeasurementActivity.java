package com.example.traffic;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateFormat;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import database.LocationDatabase;
import database.LocationDbObject;
import database.Timestamp;
import database.TimestampDbObject;

public class MeasurementActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    LocationDatabase locationDB;
    Timestamp db;
    TimestampDbObject timestampDbObject;
    private Spinner laneNumberSpinner,junctionTypeSpinner;
    String junctionNameString;
    private static final String[] lanes = {"Sáv 1", "Sáv 2", "Sáv 3"};
    private static final String[] juncType = {"lámpás útkereszteződés", "körforgalom"};

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurement);
        junctionNameString = getIntent().getStringExtra("junctionName");
        db = Room.databaseBuilder(getApplicationContext(),
                Timestamp.class, "database-name").build();

        final TextView junctionName = findViewById(R.id.junctionName);
        junctionName.setText(junctionNameString);
        laneNumberSpinner = (Spinner)findViewById(R.id.laneID);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_layout,lanes);

        //adapter.setDropDownViewResource(R.layout.activity_measurement);
        laneNumberSpinner.setAdapter(adapter);
        laneNumberSpinner.setOnItemSelectedListener(this);


        junctionTypeSpinner = (Spinner)findViewById(R.id.junctionType);
        adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_layout,juncType);

        //adapter.setDropDownViewResource(R.layout.activity_measurement);
        junctionTypeSpinner.setAdapter(adapter);
        junctionTypeSpinner.setOnItemSelectedListener(this);

        Button carBtn=findViewById(R.id.carButton);
        Button truckBtn=findViewById(R.id.truckButton);
        Button busBtn=findViewById(R.id.busButton);
        carBtn.setOnClickListener(this);
        truckBtn.setOnClickListener(this);
        busBtn.setOnClickListener(this);
    }

    /**
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        Long tsLong = System.currentTimeMillis()/1000;

        final Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(tsLong*1000);
        final String date = DateFormat.format("yyyy-MM-dd hh-mm-ss", cal).toString();
        final String vehicleType;
        if (v.getId()==R.id.carButton)
        {
            vehicleType="car";
        }
        else if(v.getId()==R.id.truckButton)
        {
            vehicleType="truck";
        }
        else
        {
            vehicleType="bus";
        }
        Thread thread = new Thread() {
            @Override
            public void run() {
                if(db == null){
                    db  = Room.databaseBuilder(getApplicationContext(),
                            Timestamp.class, "database-name").build();
                }
                java.sql.Date sqlDate = new java.sql.Date(new java.util.Date().getTime());
                timestampDbObject = new TimestampDbObject(db.locationDAO().findByName(junctionNameString).id, sqlDate,vehicleType);
                db.timestampDAO().insertTimestamp(timestampDbObject);
            }
        };
        thread.start();

        int duration = Toast.LENGTH_SHORT;
        Toast.makeText(this, date, duration).show();
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


