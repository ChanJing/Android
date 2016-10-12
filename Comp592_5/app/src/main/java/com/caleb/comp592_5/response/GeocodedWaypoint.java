package com.caleb.comp592_5.response;

import com.caleb.comp592_5.common.logger.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Caleb on 8/2/2016.
 */
public class GeocodedWaypoint {

    private String geocoderStatus = null;
    private String placeId = null;
    private ArrayList<String> types = null;
    private Boolean partialMatch = null;

    public GeocodedWaypoint(){}

    public GeocodedWaypoint(JSONObject jsonObjectGeocodedWaypoint){
        setGeocoderStatus(jsonObjectGeocodedWaypoint.optString("geocoder_status"));
        setPlaceId(jsonObjectGeocodedWaypoint.optString("place_id"));
        setTypes(jsonObjectGeocodedWaypoint.optJSONArray("types"));
        setPartialMatch(jsonObjectGeocodedWaypoint.optBoolean("partial_match"));
    }

    public void setGeocoderStatus(String geocoderStatus){
        this.geocoderStatus = geocoderStatus;
    }
    public void setPlaceId(String placeId){
        this.placeId = placeId;
    }
    public void setTypes(JSONArray jsonArraytypes){
        if(jsonArraytypes != null){
            types = new ArrayList<String>();
            for (int i = 0; i < jsonArraytypes.length(); i++) {
                types.add(jsonArraytypes.optString(i));
            }
        }else{
            Log.w("Types of Geocode Waypoint: ","is null");
        }
    }
    public void setPartialMatch(Boolean partialMatch) {
        if(partialMatch != null) {
            this.partialMatch = partialMatch;
        }else{
            Log.w("Partial Match of Geocoded Waypoint: ","is null");
        }
    }

    public String getGeocoderStatus(){
        return geocoderStatus;
    }
    public String getPlaceId(){
        return placeId;
    }
    public ArrayList<String> getTypes(){
        return types;
    }
    public Boolean getPartialMatch() {
        return partialMatch;
    }
    public String getPartialMatchResult(){
            if (partialMatch) {
                return "There is no problem";
            }else {
                return "Please check the address(s)";
        }
    }
    public String getGeocoderStatusCodes(){
        if("OK".equals(geocoderStatus)){
            return "OK";
        }else{
            return "The geocode was successful but returned no results. Please check input";
        }
    }
}
