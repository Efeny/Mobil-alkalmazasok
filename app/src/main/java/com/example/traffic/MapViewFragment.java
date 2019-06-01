package com.example.traffic;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import database.LocationDbObject;
import database.Timestamp;

/**
 * Initial fragment when launching the activity. Shows the available locations for measurements, currently stored in the database
 */
public class MapViewFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, View.OnClickListener {

    MapView mMapView;
    private GoogleMap googleMap;
    View mView;
    Button buttonMeasurement;
    boolean markerIsAssigned=false;
    final ArrayList<LocationDbObject> locationDbObjects = new ArrayList<>();
    String markerTexT;
    Timestamp db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflate and return the layout
        mView = inflater.inflate(R.layout.fragment_start, container,false);
        mMapView = mView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        //Initialize the database in the fragments instance
        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                Timestamp.class, "database-name").build();

        // Adding every element from the database into the locationDbObjects ArrayList
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                locationDbObjects.addAll(db.locationDAO().getAll());
            }
        });
        thread.start();

        buttonMeasurement = mView.findViewById(R.id.buttonMeasurement);
        buttonMeasurement.setOnClickListener(this);

        // Setting up google maps
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Starting the google map object acquisition
        mMapView.getMapAsync(this);
        return mView;

    }

    /**
     * Method that adds every marker to the returned googleMap object.
     */
    private void addMarkers() {
        MarkerOptions markerOptions=new MarkerOptions();
        for (LocationDbObject item: locationDbObjects
             ) {
            markerOptions.position(new LatLng(item.latitude,item.longitude));
            markerOptions.title(item.name);
            googleMap.addMarker(markerOptions);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    /**
     * Receives the object and sets up basic UI properties and adds markers
     * @param map A GoogleMap object
     */
    @Override
    public void onMapReady(GoogleMap map) {
        googleMap = map;
        addMarkers();
        googleMap.setOnMarkerClickListener(this);

        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(47.187104, 18.412595)).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        googleMap.setTrafficEnabled(false);
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
    }

    /**
     * Stores the title of the created marker, and enables measurements to take place at the specified junction
     * @param marker The Marker object, that fired the onMarkerClick event
     * @return
     */
    @Override
    public boolean onMarkerClick(Marker marker) {

        markerTexT =(marker.getTitle());
        markerIsAssigned=true;
        return false;
    }

    /**
     * Method that starts the measurement activity, or tells the user
     * to select a junction from the map.
     * @param v View that contains which button was clicked
     *          (currently not handled, because only a single button would fire this event).
     */
    @Override
    public void onClick(View v) {
        if (markerIsAssigned==false) {
            Context context = getActivity().getApplicationContext();
            CharSequence text = "Hely kiválasztása kötelező!";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context, text, duration).show();
        }
        else {
            MainActivity main=(MainActivity) getActivity();
            main.changeActivity(markerTexT);
        }
    }
}