package com.example.traffic;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Locale;

public class MeasurementActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner spinner;
    private static final String[] paths = {"Sáv kiválasztása", "item 2", "item 3"};

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.measurement_activity_main);

        spinner = (Spinner)findViewById(R.id.laneID);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(MeasurementActivity.this,
                R.layout.measurement_activity_main,paths);

        adapter.setDropDownViewResource(R.layout.measurement_activity_main);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        spinner = (Spinner)findViewById(R.id.junctionType);
        adapter = new ArrayAdapter<String>(MeasurementActivity.this,
                R.layout.measurement_activity_main,paths);

        adapter.setDropDownViewResource(R.layout.measurement_activity_main);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

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


