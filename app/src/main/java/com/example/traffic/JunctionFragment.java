package com.example.traffic;

import android.arch.persistence.room.Room;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.ui.SupportPlaceAutocompleteFragment;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import database.LocationDbObject;
import database.Timestamp;

/**
 * The fragment handles the creation of new LocationDbObjects into the database.
 * The junction data is used to connect the measurements to a location.
 */
public class JunctionFragment extends Fragment implements OnMapReadyCallback {
    MapView mMapView;
    private GoogleMap mMap;
    SupportPlaceAutocompleteFragment place;
    TextView txt;
    View mView;
    List<MarkerOptions> markerList= new ArrayList<MarkerOptions>();
    String TAG = this.getClass().toString();
    Timestamp db;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_junction, container, false);
        mMapView = mView.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        db = Room.databaseBuilder(getActivity().getApplicationContext(),
                Timestamp.class, "database-name").build();

        // Setting the API-KEY for the activity, to initialize the Google Places API. The key is stored in the manifest only (ignore by git), to avoid getting leaked on github
        try {
            ApplicationInfo ai = getActivity().getPackageManager().getApplicationInfo(getActivity().getPackageName(), PackageManager.GET_META_DATA);
            Bundle bundle = ai.metaData;
            String myApiKey = bundle.getString("com.google.android.geo.API_KEY");
            if (!Places.isInitialized()) {
                Places.initialize(getActivity().getApplicationContext(), myApiKey);
            }
            PlacesClient placesClient = Places.createClient(getActivity());
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Failed to load meta-data, NameNotFound: " + e.getMessage());
        } catch (NullPointerException e) {
            Log.e(TAG, "Failed to load meta-data, NullPointer: " + e.getMessage());
        }
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initializing AutoComplete object, to provide completion and the ability to add Place data into the database
        txt = mView.findViewById(R.id.txtInfo);
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getChildFragmentManager().findFragmentById(R.id.place_autocomplete);

        // Setting up the fields that are required from the Places API
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG, Place.Field.ADDRESS));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                AddPlace(place, 1);
            }

            @Override
            public void onError(Status status) {
            }
        });
        mMapView.getMapAsync(this);
        return mView;
    }

    /**
     * Method that receives the Place object, and creates a LocationDbObject from it.
     * The created object is stored in the database.
     * @param place Object that contains the necessary data for the LocationDbObject
     * @param pno
     */
    public void AddPlace(Place place, int pno) {
        final Place threadPlace = place;
        txt=mView.findViewById(R.id.txtInfo);
        try {
            if (mMap == null) {

            } else {
                //Moving the camera and adding a marker to the specified Place object
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(place.getLatLng(), 16));

                mMap.addMarker(new MarkerOptions().position(place.getLatLng())
                        .title(place.getName()));

                markerList.add(new MarkerOptions().position(place.getLatLng()));

                Thread thread = new Thread() {
                    @Override
                    public void run() {
                            db.locationDAO().insert(new LocationDbObject(threadPlace.getName(),threadPlace.getAddress(),threadPlace.getLatLng().latitude,threadPlace.getLatLng().longitude));
                    }
                };
                thread.start();
                txt.setText("Name:" + place.getName() + "\nAddress:" + place.getAddress());

            }
        } catch (Exception ex) {
            Toast.makeText(getActivity().getApplicationContext(), "Error:" + ex.getMessage().toString(), Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Receiving the GoogleMap object, and setting the listener for marker clicks
     * @param googleMap Object thats set as mMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        final String[] markerTexT = new String[1];
        mMap = googleMap;
        CameraPosition cameraPosition = new CameraPosition.Builder().target(new LatLng(47.187104, 18.412595)).zoom(12).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.setTrafficEnabled(false);
        mMap.setIndoorEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        if(mMap!=null)
        {
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    markerTexT[0] =(marker.getTitle());
                    return false;
                }
            });
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
}

