package com.caleb.comp592_5.activity;

import com.caleb.comp592_5.data.LocationData;
import com.caleb.comp592_5.common.activities.SampleActivityBase;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.caleb.comp592_5.adapter.SearchAdapter;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Caleb on 8/30/2016.
 */
public class SearchActivity
        extends SampleActivityBase
        implements PlaceSelectionListener {

    private static final String ACTIVITYNAME = "ActivityName";
    private static final String ORIGIN = "Origin";
    private static final String DESTINATION = "Destination";
    private static final String CURRENTLOCATION = "CurrentLocation";
    private static final String SELECTEDLOCATION = "SelectedLocation";
    private static final String TARGET = "Target";

    static final String STATE_SEARCHLIST = "searchlist";

    LocationData mCurrentLocation;
    private LocationData currentLocationData = null;

    private ArrayList<LocationData> searchList;
    private SearchAdapter searchAdapter;

    LocationData originLocation = null;
    LocationData destinationLocation= null;

    String target = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Setup Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Retrieve the PlaceAutocompleteFragment.
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.autocomplete_fragment);

        final View fragmentView = autocompleteFragment.getView();
        if(fragmentView != null){
            Log.e(TAG,"Can get view in autocompleteFragment");
        }
        fragmentView.post(new Runnable()
        {
            public void run()
            {
                fragmentView.requestFocus();
            }
        });
        // Register a listener to receive callbacks when a place has been selected or an error has
        // occurred.
        autocompleteFragment.setOnPlaceSelectedListener(this);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        prepareaInitialList(savedInstanceState);
        prepareRecyclerView();
        prepareSearchList();
    }

    private void prepareaInitialList(Bundle savedInstanceState) {

        Intent intent = getIntent();
        currentLocationData = intent.getParcelableExtra(CURRENTLOCATION);
        destinationLocation = intent.getParcelableExtra(DESTINATION);
        originLocation = intent.getParcelableExtra(ORIGIN);
        target = intent.getStringExtra(TARGET);

        searchList = new ArrayList<>();
        searchList.add(currentLocationData);
    }

    /**
     * Callback invoked when a place has been selected from the PlaceAutocompleteFragment.
     */
    @Override
    public void onPlaceSelected(Place place) {
        Log.i(TAG, "Place Selected: " + place.getName());

        LocationData SelectedLocationData = convertPlacetoLocationData(place);
        updateSearchList(SelectedLocationData);

        getSelectedLocation(SelectedLocationData);


        //Start a new Activity
    }

    /**
     * Callback invoked when PlaceAutocompleteFragment encounters an error.
     */
    @Override
    public void onError(Status status) {
        Log.e(TAG, "onError: Status = " + status.toString());

        Toast.makeText(this, "Place selection failed: " + status.getStatusMessage(),
                Toast.LENGTH_SHORT).show();
    }

//    /**
//     * Helper method to format information about a place nicely.
//     */
//    private static Spanned formatPlaceDetails(Resources res, CharSequence name, String id,
//                                              CharSequence address, CharSequence phoneNumber, Uri websiteUri) {
//        Log.e(TAG, res.getString(R.string.place_details, name, id, address, phoneNumber,
//                websiteUri));
//        return Html.fromHtml(res.getString(R.string.place_details, name, id, address, phoneNumber,
//                websiteUri));
//
//    }

    /**
     * Setting up RecyclerView and RecyclerAdapter
     */
    private void prepareRecyclerView(){

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        searchAdapter = new SearchAdapter(this,searchList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        if (recyclerView != null) {
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(searchAdapter);
        }
    }

    /**
     * Display the Search History Data
     */
    private void prepareSearchList(){
        searchAdapter.notifyDataSetChanged();
    }

    /**
     * Might be deleted in the furture design
     * Add a history record as a card in RecyclerView
     */
    private void updateSearchList(LocationData searchData){

        for (LocationData locationData : searchList) {
            if(searchData.getAddress().equals(locationData.getAddress())){
                searchList.remove(locationData);
            }
        }

        searchList.remove(0);
        Collections.reverse(searchList);
        searchList.add(searchData);
        searchList.add(currentLocationData);
        Collections.reverse(searchList);
        if(searchList.size() > 10){
            searchList.remove(10);
        }

        searchAdapter.notifyDataSetChanged();
    }

    public void lunchRouteActivity(LocationData selectedLocation){

        Intent intent = new Intent(this, RouteActivity.class);
        if("destination".equals(target)){
            intent.putExtra(DESTINATION,selectedLocation);
            intent.putExtra(ORIGIN,originLocation);
        }else{
            intent.putExtra(ORIGIN,selectedLocation);
            intent.putExtra(DESTINATION,destinationLocation);
        }

        intent.putExtra(CURRENTLOCATION,currentLocationData);
        startActivity(intent);
    }

    public void getSelectedLocation(LocationData selectedLocation){
        if("destination".equals(target)){
            if(originLocation != null &&
                    selectedLocation.getAddress().equals(originLocation.getAddress())){
                Toast.makeText(this,"Cannot choose same address",Toast.LENGTH_SHORT).show();
            }else {
                lunchRouteActivity(selectedLocation);
            }
        }else{
            if(destinationLocation != null &&
                    selectedLocation.getAddress().equals(destinationLocation.getAddress())){
                Toast.makeText(this,"Cannot choose same address",Toast.LENGTH_SHORT).show();
            }else{
                lunchRouteActivity(selectedLocation);
            }
        }
    }


    private LocationData convertPlacetoLocationData(Place place){
        String placeAddress;
        if(place.getAddress().toString().contains(place.getName().toString())){
            placeAddress = place.getAddress().toString();
        }
        else{
            placeAddress = place.getName().toString() + " " + place.getAddress().toString();
        }
        LocationData locationData = new LocationData(
                place.getLatLng(),
                placeAddress,
                place.getId());
        return locationData;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {

        // Save the user's current game state
        savedInstanceState.putParcelableArrayList(STATE_SEARCHLIST,searchList);
        savedInstanceState.putString("a","asd");
        Log.i(TAG, savedInstanceState.toString());
        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(savedInstanceState);
    }
}
