package com.caleb.comp592_5.response;

import com.caleb.comp592_5.common.logger.Log;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONObject;

/**
 * Created by Caleb on 8/3/2016.
 */
public class MyTransitDetails {

    private MyStop arrivalStop = null;
    private MyTime arrivalTime = null;
    private MyStop departureStop = null;
    private MyTime departureTime = null;
    private String headsign = null;
    private MyLine myLine = null;
    private int numStops;
    private long headway;

    public MyTransitDetails(JSONObject jsonObjectTransitDetails){
        setArrivalStop(jsonObjectTransitDetails.optJSONObject("arrival_stop"));
        setArrivalTime(jsonObjectTransitDetails.optJSONObject("arrival_time"));
        setDepartureStop(jsonObjectTransitDetails.optJSONObject("departure_stop"));
        setDepartureTime(jsonObjectTransitDetails.optJSONObject("departure_time"));
        setHeadsign(jsonObjectTransitDetails.optString("headsign"));
        setHeadway(jsonObjectTransitDetails.optLong("headway"));
        setMyLine(jsonObjectTransitDetails.optJSONObject("line"));
        setNumStops(jsonObjectTransitDetails.optInt("num_stops"));
    }

    public void setArrivalStop(JSONObject jsonObjectArrivalStop) {
        if(jsonObjectArrivalStop != null){
            arrivalStop = new MyStop(jsonObjectArrivalStop);
        }
    }
    public void setArrivalTime(JSONObject jsonObjectArrivalTime) {
        if(jsonObjectArrivalTime != null){
            arrivalTime = new MyTime(jsonObjectArrivalTime);
        }
    }
    public void setDepartureStop(JSONObject jsonObjectDepartureStop) {
        if (jsonObjectDepartureStop != null){
            departureStop = new MyStop(jsonObjectDepartureStop);
        }
    }
    public void setDepartureTime(JSONObject jsonObjectArrivalTimeDepartureTime) {
        if (jsonObjectArrivalTimeDepartureTime != null){
            departureTime = new MyTime(jsonObjectArrivalTimeDepartureTime);
        }
    }
    public void setHeadsign(String headsign) {
        if(headsign != null) {
            this.headsign = headsign;
        }else{
            Log.w("Headsign of TransitDetails: ","is null");
        }
    }
    public void setHeadway(long headway) {
        if(headway != 0) {
            this.headway = headway;
        }else{
            Log.w("Headway of TransitDetails: ","is null");
        }
    }
    public void setMyLine(JSONObject jsonObjectLine) {
        if(jsonObjectLine != null) {
            myLine = new MyLine(jsonObjectLine);
        }else{
            Log.w("Line of TransitDetails: ","is null");
        }
    }
    public void setNumStops(int numStops) {
        if(numStops != 0) {
            this.numStops = numStops;
        }else {
            Log.w("NumStops of TransitDetails: ","is null");
        }
    }

    public MyStop getArrivalStop() {
        return arrivalStop;
    }
    public MyTime getArrivalTime() {
        return arrivalTime;
    }
    public MyStop getDepartureStop() {
        return departureStop;
    }
    public MyTime getDepartureTime() {
        return departureTime;
    }
    public String getHeadsign() {
        return headsign;
    }
    public long getHeadway() {
        return headway;
    }
    public MyLine getMyLine() {
        return myLine;
    }
    public int getNumStops() {
        return numStops;
    }

    public class MyStop {

        private LatLng location = null;
        private String name = null;

        public MyStop(JSONObject jsonObjectStop){
            setLocation(jsonObjectStop.optJSONObject("location"));
            setName(jsonObjectStop.optString("name"));
        }

        public void setLocation(JSONObject jsonObjectLocation) {
            if(jsonObjectLocation != null) {
                double lat = jsonObjectLocation.optDouble("lat");
                double lng = jsonObjectLocation.optDouble("lng");
                location = new LatLng(lat, lng);
            }
        }
        public void setName(String name) {
            this.name = name;
        }

        public LatLng getLocation() {
            return location;
        }
        public String getName() {
            return name;
        }
    }
}
