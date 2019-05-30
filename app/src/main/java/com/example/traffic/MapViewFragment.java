package com.example.traffic;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

import database.LocationDatabase;
import database.LocationDbObject;

/**
 * A fragment_measurement that launches other parts of the demo application.
 */
public class MapViewFragment extends Fragment implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener, View.OnClickListener {

    MapView mMapView;
    private GoogleMap googleMap;
    View mView;
    Button buttonMeasurement;
    boolean markerIsAssigned=false;
    final ArrayList<LocationDbObject> locationDbObjects = new ArrayList<>();
    String markerTexT;

    double latitude;
    double longitude;
    LocationDatabase db;
    // create marker
    MarkerOptions marker;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // inflat and return the layout
        mView = inflater.inflate(R.layout.fragment_measurement, container,false);
        mMapView = mView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume();// needed to get the map to display immediately

        buttonMeasurement = mView.findViewById(R.id.buttonMeasurement);
        buttonMeasurement.setOnClickListener(this);
        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                LocationDatabase.class, "database-name").build();

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                locationDbObjects.addAll((ArrayList) db.locationDAO().getAll());
            }
        });
        thread.start();

        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(this);
        //SupportMapFragment mapFragment = ((SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.frag));
        //mapFragment.getMapAsync(this);

        // Perform any camera updates here
        return mView;

    }

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

    @Override
    public void onMapReady(GoogleMap map) {
        JunctionFragment jf=new JunctionFragment();
        TextView txt=mView.findViewById(R.id.txtInfo);
        //DO WHATEVER YOU WANT WITH GOOGLEMAP
        // latitude and longitude
        //double latitude = 47.187104;
        //double longitude = 18.412595;

        // create marker
        //MarkerOptions marker = new MarkerOptions().position(new LatLng(latitude, longitude)).title("Hello Maps");

        // Changing marker icon
       // marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        googleMap = map;
        addMarkers();
        googleMap.setOnMarkerClickListener(this);
        /*for (int i=0; i<=jf.markerList.size();i++){
            map.addMarker(jf.markerList.get(i));
        }*/


        // adding marker
       // googleMap.addMarker(marker);
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(47.187104, 18.412595)).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        //map.setMyLocationEnabled(true);
        googleMap.setTrafficEnabled(false);
        googleMap.setIndoorEnabled(true);
        googleMap.setBuildingsEnabled(true);
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        // Setting a click event handler for the map
        googleMap.setOnMapLongClickListener(new GoogleMap.OnMapLongClickListener() {

            @Override
            public void onMapLongClick(LatLng latLng) {

                // Creating a marker
                MarkerOptions markerOptions = new MarkerOptions();

                // Setting the position for the marker
                markerOptions.position(latLng);

                // Setting the title for the marker.
                // This will be displayed on taping the marker
                markerOptions.title(latLng.latitude + " : " + latLng.longitude);

                // Clears the previously touched position
                //googleMap.clear();

                // Animating to the touched position
                //googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));

                // Placing a marker on the touched position
                googleMap.addMarker(markerOptions);
                startMeasurement(mView);
            }
        });
    }


    public void startMeasurement(View mView)
    {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        markerTexT =(marker.getTitle().toString());
        markerIsAssigned=true;
        return false;
    }

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