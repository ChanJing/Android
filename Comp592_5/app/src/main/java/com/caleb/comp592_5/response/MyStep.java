package com.caleb.comp592_5.response;

import com.caleb.comp592_5.common.logger.Log;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Caleb on 8/3/2016.
 */
public class MyStep {

    private Distance distance = null;
    private Duration duration = null;
    private LatLng endLocation = null;
    private String htmlInstructions = null;
    private MyPolyline polyline = null;
    private LatLng startLocation = null;
    private ArrayList<MyStep> mySteps = null;
    private MyTransitDetails myTransitDetails = null;
    private String travelMode = null;


    public MyStep(JSONObject jsonObjectStep) {
        setDistance(jsonObjectStep.optJSONObject("distance"));
        setDuration(jsonObjectStep.optJSONObject("duration"));
        setEndLocation(jsonObjectStep.optJSONObject("end_location"));
        setHtmlInstructions(jsonObjectStep.optString("html_instructions"));
        setPolyline(jsonObjectStep.optJSONObject("polyline"));
        setStartLocation(jsonObjectStep.optJSONObject("start_location"));
        setMySteps(jsonObjectStep.optJSONArray("steps"));
        setMyTransitDetails(jsonObjectStep.optJSONObject("transit_details"));
        setTravelMode(jsonObjectStep.optString("travel_mode"));
    }

    public void setDistance(JSONObject jsonObjectDistance) {
        if(jsonObjectDistance != null) {
            distance = new Distance(jsonObjectDistance);
        }else{
            Log.w("Distance of Step: ","is null");
        }
    }
    public void setDuration(JSONObject jsonObjectDuration) {
        if(jsonObjectDuration != null) {
            duration = new Duration(jsonObjectDuration);
        }else{
            Log.w("Duration of Step: ","is null");
        }
    }
    public void setEndLocation(JSONObject jsonObjectEndLocation) {
        if(jsonObjectEndLocation != null) {
            double lat = jsonObjectEndLocation.optDouble("lat");
            double lng = jsonObjectEndLocation.optDouble("lng");
            endLocation = new LatLng(lat, lng);
        }else{
            Log.w("End Location of Step: ","is null");
        }
    }
    public void setHtmlInstructions(String htmlInstructions) {
        if(htmlInstructions != null) {
            this.htmlInstructions = htmlInstructions;
        }else{
            Log.w("Html Instructions of Step: ","is null");
        }
    }
    public void setPolyline(JSONObject jsonObjectPolyline) {
        if(jsonObjectPolyline != null) {
            polyline = new MyPolyline(jsonObjectPolyline);
        }else{
            Log.w("Polyline of Step: ","is null");
        }
    }
    public void setStartLocation(JSONObject jsonObjectstartLocation) {
        if(jsonObjectstartLocation != null) {
            double lat = jsonObjectstartLocation.optDouble("lat");
            double lng = jsonObjectstartLocation.optDouble("lng");
            startLocation = new LatLng(lat, lng);
        }else{
            Log.w("Start Location of Step: ","is null");
        }
    }
    public void setMySteps(JSONArray jsonArraySteps) {
        if(jsonArraySteps != null){
            mySteps = new ArrayList<MyStep>();
            for (int i = 0; i < jsonArraySteps.length(); i++) {
                MyStep myStep = new MyStep(jsonArraySteps.optJSONObject(i));
                mySteps.add(myStep);
            }
        }else{
            Log.w("Steps of Step: ","is null");
        }
    }
    public void setMyTransitDetails(JSONObject jsonObjectTransitDetails) {
        if (jsonObjectTransitDetails != null){
            myTransitDetails = new MyTransitDetails(jsonObjectTransitDetails);
        }else{
            Log.w("Transit Details of Step: ","is null");
        }
    }
    public void setTravelMode(String travelMode) {
        if(travelMode != null) {
            this.travelMode = travelMode;
        }else {
            Log.w("Travel Mode of Step: ","is null");
        }
    }

    public Distance getDistance() {
        return distance;
    }
    public Duration getDuration() {
        return duration;
    }
    public LatLng getEndLocation() {
        return endLocation;
    }
    public String getHtmlInstructions() {
        return htmlInstructions;
    }
    public MyPolyline getPolyline() {
        return polyline;
    }
    public LatLng getStartLocation() {
        return startLocation;
    }
    public ArrayList<MyStep> getMySteps() {
        return mySteps;
    }
    public MyTransitDetails getMyTransitDetails() {
        return myTransitDetails;
    }
    public String getTravelMode() {
        return travelMode;
    }
}
