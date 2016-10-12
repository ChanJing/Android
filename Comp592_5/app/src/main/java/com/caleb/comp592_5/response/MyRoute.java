package com.caleb.comp592_5.response;

import com.caleb.comp592_5.common.logger.Log;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Caleb on 8/2/2016.
 */
public class MyRoute {

    private MyBound mybounds = null;
    private String copyrights = null;
    private ArrayList<MyLeg> myLegs = null;
    private MyPolyline overviewPolyline = null;
    private String summary = null;
    private ArrayList<String> warnings = null;
    private ArrayList<Integer> waypointOrder = null;
    private MyFare myFare = null;


    public MyRoute(JSONObject jsonObjectRoute){
        setMyBounds(jsonObjectRoute.optJSONObject("bounds"));
        setCopyrights(jsonObjectRoute.optString("copyrights"));
        setMyLegs(jsonObjectRoute.optJSONArray("legs"));
        setOverviewPolyline(jsonObjectRoute.optJSONObject("overview_polyline"));
        setSummary(jsonObjectRoute.optString("summary"));
        setWarnings(jsonObjectRoute.optJSONArray("warnings"));
        setWaypontOrder(jsonObjectRoute.optJSONArray("waypoint_order"));
        setMyFare(jsonObjectRoute.optJSONObject("fare"));
    }

    public void setMyBounds(JSONObject jsonObjectBounds){
        if(jsonObjectBounds != null) {
            mybounds = new MyBound(jsonObjectBounds);
        }else{
            Log.w("Bounds of Route: ","is null");
        }
    }
    public void setCopyrights(String copyrights){
        this.copyrights = copyrights;
    }
    public void setMyLegs(JSONArray jsonArrayLegs){
        if(jsonArrayLegs != null) {
            myLegs = new ArrayList<MyLeg>();
            for (int i = 0; i < jsonArrayLegs.length(); i++) {
                MyLeg myLeg = new MyLeg(jsonArrayLegs.optJSONObject(i));
                myLegs.add(myLeg);
            }
        }else{
            Log.w("Legs of Routes: ","is null");
        }
    }
    public void setOverviewPolyline(JSONObject jsonObjectOverviewPolyline){
        if(jsonObjectOverviewPolyline != null) {
            overviewPolyline = new MyPolyline(jsonObjectOverviewPolyline);
        }else{
            Log.w("Overview Polyline of Route: ","is null");
        }
    }
    public void setSummary(String summary){
        if(summary != null) {
            this.summary = summary;
        }else{
            Log.w("Summary of Route: ","is null");
        }
    }
    public void setWarnings(JSONArray jsonArrayWarnings){
        if(jsonArrayWarnings != null) {
            warnings = new ArrayList<String>();
            for (int i = 0; i < jsonArrayWarnings.length(); i++) {
                warnings.add(jsonArrayWarnings.optString(i));
            }
        }else{
            Log.w("Warnings of Route: ","is null");
        }
    }
    public void setWaypontOrder(JSONArray jsonArrayWaypointOrder){
        if(jsonArrayWaypointOrder != null) {
            waypointOrder = new ArrayList<Integer>();
            for (int i = 0; i < jsonArrayWaypointOrder.length(); i++) {
                Integer arrayWaypontOrder = Integer.valueOf(jsonArrayWaypointOrder.optInt(i));
                waypointOrder.add(arrayWaypontOrder);
            }
        }else{
            Log.w("WaypontOrder of Route: ","is null");
        }
    }
    public void setMyFare(JSONObject jsonObjectFare){
        if(jsonObjectFare != null){
            myFare = new MyFare(jsonObjectFare);
        }else{
            Log.w("Fare of Route: ","is null");
        }
    }

    public MyBound getMyBounds(){
        return mybounds;
    }
    public String getCopyrights(){
        return copyrights;
    }
    public ArrayList<MyLeg> getMyLegs(){
        return myLegs;
    }
    public MyPolyline getOverviewPolyline(){
        return overviewPolyline;
    }
    public String getSummary(){
        return summary;
    }
    public ArrayList<String> getWarnings(){
        return warnings;
    }
    public ArrayList<Integer> getWaypontOrder(){
        return waypointOrder;
    }
    public MyFare getMyFare(){
        return myFare;
    }


    public class MyBound {

        private LatLng northeast = null;
        private LatLng southwest = null;

        public MyBound(JSONObject jsonObjectbounds){
            setNortheast(jsonObjectbounds.optJSONObject("northeast"));
            setSouthwest(jsonObjectbounds.optJSONObject("southwest"));
        }

        public void setNortheast(JSONObject jsonObjectNortheast) {
            if (jsonObjectNortheast != null) {
                double lat = jsonObjectNortheast.optDouble("lat");
                double lng = jsonObjectNortheast.optDouble("lng");
                northeast = new LatLng(lat, lng);
            }else{
                Log.w("Northeast of Bound: ","is null");
            }
        }
        public void setSouthwest(JSONObject jsonObjectSouthwest) {
            if (jsonObjectSouthwest != null) {
                double lat = jsonObjectSouthwest.optDouble("lat");
                double lng = jsonObjectSouthwest.optDouble("lng");
                northeast = new LatLng(lat, lng);
            }else{
                Log.w("Southwest of Bound: ","is null");
            }
        }

        public LatLng getNortheast(){
            return northeast;
        }
        public LatLng getSouthwest(){
            return southwest;
        }
    }
    public class MyFare {

        private String currency = null;
        private int value;
        private String text = null;

        public MyFare(JSONObject jsonObjectFare){
            setCurrency(jsonObjectFare.optString("currency"));
            setValue(jsonObjectFare.optInt("value"));
            setText(jsonObjectFare.optString("text"));
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }
        public void setValue(int value) {
            this.value = value;
        }
        public void setText(String text) {
            this.text = text;
        }

        public String getCurrency() {
            return currency;
        }
        public String getText() {
            return text;
        }
        public int getValue() {
            return value;
        }
    }
}
