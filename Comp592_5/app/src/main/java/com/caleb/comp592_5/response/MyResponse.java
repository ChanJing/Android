package com.caleb.comp592_5.response;

import com.caleb.comp592_5.common.logger.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Caleb on 8/2/2016.
 */
public class MyResponse{

    private ArrayList<String> availableTravelModes = null;
    private ArrayList<GeocodedWaypoint> geocodedWaypoints = null;
    private ArrayList<MyRoute> myRoutes = null;
    private String status = null;

    public MyResponse(){

    }

    public MyResponse(JSONObject jsonObjectresponse){
        setStatus(jsonObjectresponse.optString("status"));
        if(getRequestStatusCheck()) {
            setAvailableTravelModes(jsonObjectresponse.optJSONArray("available_travel_modes"));
            setGeocodedWaypoints(jsonObjectresponse.optJSONArray("geocoded_waypoints"));
            setMyRoutes(jsonObjectresponse.optJSONArray("routes"));
        }else{

        }
    }

    public void setAvailableTravelModes(JSONArray jsonArrayAvailableTravelModes){
        if(jsonArrayAvailableTravelModes != null) {
            availableTravelModes = new ArrayList<String>();
            for (int i = 0; i < jsonArrayAvailableTravelModes.length(); i++) {
                availableTravelModes.add(jsonArrayAvailableTravelModes.optString(i));
            }
        }
    }
    public void setGeocodedWaypoints(JSONArray jsonArrayGeocodedWaypoints){
        if(jsonArrayGeocodedWaypoints != null){
            geocodedWaypoints = new ArrayList<GeocodedWaypoint>();
            for (int i = 0; i < jsonArrayGeocodedWaypoints.length(); i++) {
                GeocodedWaypoint geocodedWaypoint = new GeocodedWaypoint(jsonArrayGeocodedWaypoints.optJSONObject(i));
                geocodedWaypoints.add(geocodedWaypoint);
            }
        }
    }
    public void setMyRoutes(JSONArray jsonArrayroutes){
        if(jsonArrayroutes != null) {
            myRoutes = new ArrayList<MyRoute>();
            for (int i = 0; i < jsonArrayroutes.length(); i++) {
                MyRoute myRoute = new MyRoute(jsonArrayroutes.optJSONObject(i));
                myRoutes.add(myRoute);
            }
        }else{
            Log.w("Routes of Response: ","is null");
        }
    }
    public void setStatus(String status){
        this.status = status;
    }

    public ArrayList<String> getAvailableTravelModes(){
        return availableTravelModes;
    }
    public ArrayList<GeocodedWaypoint> getGeocodedWaypoints(){
        return geocodedWaypoints;
    }
    public ArrayList<MyRoute> getMyRoutes(){
        return myRoutes;
    }
    public String getStatus(){
        return status;
    }
    public String getStatusCodes(){
        switch (status){
            case "NOT_FOUND":
                return " Please Check the Locations";
            case "ZERO_RESULTS":
                return "Sorry, no route could be found between the origin and destination.";
            case "MAX_WAYPOINTS_EXCEEDED":
                return "Sorry, too many waypoints are provided in the request.";
            case "INVALID_REQUEST":
                return "Sorry, this request is invalid.";
            case "OVER_QUERY_LIMIT":
                return "Sorry, too many requests from your application within the allowed time period.";
            case "REQUEST_DENIED":
                return "Sorry, the service denied use of the directions service by your application.";
            case "UNKNOWN_ERROR":
                return "Sorry, there is a server error, please try again later.";
            default:
                return "OK";
        }
    }
    public boolean getRequestStatusCheck(){
        if("OK".equals(status)) {
            return true;
        }else {
            return false;
        }
    }
}
