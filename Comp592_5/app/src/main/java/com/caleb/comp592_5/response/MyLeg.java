package com.caleb.comp592_5.response;

import com.caleb.comp592_5.common.logger.Log;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


/**
 * Created by Caleb on 8/2/2016.
 */
public class MyLeg {

    private MyTime arrivalTime = null;
    private MyTime departureTime = null;
    private Distance distance = null;
    private Duration duration = null;
    private String endAddress = null;
    private LatLng endLocation = null;
    private String startAddress = null;
    private LatLng startLocation = null;
    private ArrayList<MyStep> mySteps = null;
    private Duration durationInTraffic = null;
    private ArrayList<String> trafficSpeedEntry = null;
    private ArrayList<String> viaWaypoint = null;

    public MyLeg(JSONObject jsonObjectLeg){
        setDistance(jsonObjectLeg.optJSONObject("distance"));
        setDuration(jsonObjectLeg.optJSONObject("duration"));
        setEndAddress(jsonObjectLeg.optString("end_address"));
        setEndLocation(jsonObjectLeg.optJSONObject("end_location"));
        setStartAddress(jsonObjectLeg.optString("start_address"));
        setStartLocation(jsonObjectLeg.optJSONObject("start_location"));
        setMySteps(jsonObjectLeg.optJSONArray("steps"));
        setArrivalTime(jsonObjectLeg.optJSONObject("arrival_time"));
        setDepartureTime(jsonObjectLeg.optJSONObject("departure_time"));
        setDurationInTraffic(jsonObjectLeg.optJSONObject("duration_in_traffic"));
        setTrafficSpeedEntry(jsonObjectLeg.optJSONArray("traffic_speed_entry"));
        setViaWaypoint(jsonObjectLeg.optJSONArray("via_waypoint"));
    }

    public void setArrivalTime(JSONObject jsonObjectArrivalTime) {
        if(jsonObjectArrivalTime != null){
            arrivalTime = new MyTime(jsonObjectArrivalTime);
        }else{
            Log.w("Arrival Time of Leg: ","is null");
        }
    }
    public void setDepartureTime(JSONObject jsonObjectDepartureTime) {
        if(jsonObjectDepartureTime != null){
            departureTime = new MyTime(jsonObjectDepartureTime);
        }else{
            Log.w("Departure Time of Leg: ","is null");
        }
    }
    public void setDistance(JSONObject jsonObjectDistance) {
        if(jsonObjectDistance != null){
            distance = new Distance(jsonObjectDistance);
        }else{
            Log.w("Distance of Leg: ","is null");
        }
    }
    public void setDuration(JSONObject jsonObjectDuration) {
        if(jsonObjectDuration != null){
            duration = new Duration(jsonObjectDuration);
        }else{
            Log.w("Duration of Leg: ","is null");
        }
    }
    public void setEndAddress(String endAddress) {
        this.endAddress = endAddress;
    }
    public void setEndLocation(JSONObject jsonObjectEndLocation) {
        if(jsonObjectEndLocation != null){
            double lat = jsonObjectEndLocation.optDouble("lat");
            double lng = jsonObjectEndLocation.optDouble("lng");
            endLocation = new LatLng(lat, lng);
        }else{
            Log.w("End Location of Leg: ","is null");
        }
    }
    public void setStartAddress(String startAddress) {
        this.startAddress = startAddress;
    }
    public void setStartLocation(JSONObject jsonObjectstartLocation) {
        if(jsonObjectstartLocation != null){
            double lat = jsonObjectstartLocation.optDouble("lat");
            double lng = jsonObjectstartLocation.optDouble("lng");
            startLocation = new LatLng(lat, lng);
        }else{
            Log.w("Start Location of Leg: ","is null");
        }
    }
    public void setMySteps(JSONArray jsonArraySteps) {
        if(jsonArraySteps != null){
            mySteps = new ArrayList<MyStep>();
            for(int i = 0; i < jsonArraySteps.length(); i++){
                MyStep myStep = new MyStep(jsonArraySteps.optJSONObject(i));
                mySteps.add(myStep);
            }
        }else{
            Log.w("Steps of Leg: ","is null");
        }
    }
    public void setDurationInTraffic(JSONObject jsonObjectDurationInTraffic) {
        if(jsonObjectDurationInTraffic != null){
            durationInTraffic = new Duration(jsonObjectDurationInTraffic);
        }else{
            Log.w("Duration In Traffic of Leg: ","is null");
        }
    }
    public void setTrafficSpeedEntry(JSONArray jsonArrayTrafficSpeedEntry) {
        if(jsonArrayTrafficSpeedEntry != null) {
            trafficSpeedEntry = new ArrayList<String>();
            for (int i = 0; i < jsonArrayTrafficSpeedEntry.length(); i++) {
                trafficSpeedEntry.add(jsonArrayTrafficSpeedEntry.optString(i));
            }
        }else{
            Log.w("Traffic Speed Entry of Leg: ","is null");
        }
    }
    public void setViaWaypoint(JSONArray jsonArrayViaWaypoint) {
        if(jsonArrayViaWaypoint != null) {
            viaWaypoint = new ArrayList<String>();
            for (int i = 0; i < jsonArrayViaWaypoint.length(); i++) {
                viaWaypoint.add(jsonArrayViaWaypoint.optString(i));
            }
        }else{
            Log.w("Via Waypoint of Leg: ","is null");
        }
    }

    public MyTime getArrivalTime() {
        return arrivalTime;
    }
    public MyTime getDepartureTime() {
        return departureTime;
    }
    public Distance getDistance() {
        return distance;
    }
    public Duration getDuration() {
        return duration;
    }
    public Duration getDurationInTraffic() {
        return durationInTraffic;
    }
    public String getEndAddress() {
        return endAddress;
    }
    public LatLng getEndLocation() {
        return endLocation;
    }
    public String getStartAddress() {
        return startAddress;
    }
    public LatLng getStartLocation() {
        return startLocation;
    }
    public ArrayList<MyStep> getMySteps() {
        return mySteps;
    }
    public ArrayList<String> getTrafficSpeedEntry() {
        return trafficSpeedEntry;
    }
    public ArrayList<String> getViaWaypoint() {
        return viaWaypoint;
    }
}
