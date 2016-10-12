package com.caleb.comp592_5.response;

import com.caleb.comp592_5.common.logger.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Caleb on 8/3/2016.
 */
public class MyLine {

    private ArrayList<Agency> agencies = null;
    private String color = null;
    private String name = null;
    private String shortName = null;
    private String textColor = null;
    private String url = null;
    private Vehicle vehicle = null;

    public MyLine(JSONObject jsonObjectLine){
//        setAgencies(jsonObjectLine.optJSONArray("agencies"));
        setColor(jsonObjectLine.optString("color"));
        setName(jsonObjectLine.optString("name"));
        setShortName(jsonObjectLine.optString("short_name"));
        setTextColor(jsonObjectLine.optString("text_color"));
        setUrl(jsonObjectLine.optString("url"));
        setVehicle(jsonObjectLine.optJSONObject("vehicle"));
    }


    public void setAgencies(JSONArray jsonArrayAgencies) {
        if(jsonArrayAgencies != null) {
            for (int i = 0; i < jsonArrayAgencies.length(); i++) {
                Agency agency = new Agency(jsonArrayAgencies.optJSONObject(i));
                agencies.add(agency);
            }
        }else{
            Log.w("Agencies of Line: ","is null");
        }
    }
    public void setColor(String color) {
        if(color != null) {
            this.color = color;
        }else{
            Log.w("Color of Line: ","is null");
        }
    }
    public void setName(String name) {
        if(name != null) {
            this.name = name;
        }else{
            Log.w(" of Line: ","is null");
        }
    }
    public void setShortName(String shortName) {
        if(shortName != null) {
            this.shortName = shortName;
        }else{
            Log.w("Short Name of Line: ","is null");
        }
    }
    public void setTextColor(String textColor) {
        if(textColor != null) {
            this.textColor = textColor;
        }else{
            Log.w("Text Color of Line: ","is null");
        }
    }
    public void setUrl(String url) {
        if(url != null) {
            this.url = url;
        }else{
            Log.w("Url of Line: ","is null");
        }
    }
    public void setVehicle(JSONObject jsonObjectVehicle) {
        if(jsonObjectVehicle != null) {
            vehicle = new Vehicle(jsonObjectVehicle);
        }else{
            Log.w("Vehicle of Line: ","is null");
        }
    }


    public ArrayList<Agency> getAgencies() {
        return agencies;
    }
    public String getColor() {
        return color;
    }
    public String getName() {
        return name;
    }
    public String getShortName() {
        return shortName;
    }
    public String getTextColor() {
        return textColor;
    }
    public String getUrl() {
        return url;
    }
    public Vehicle getVehicle() {
        return vehicle;
    }


    public class Agency{
        private String name = null;
        private String phone = null;
        private String url = null;

        public Agency(JSONObject jsonObjectAgency){
            setName(jsonObjectAgency.optString("name"));
            setPhone(jsonObjectAgency.optString("phone"));
            setUrl(jsonObjectAgency.optString("url"));
        }

        public void setName(String name) {
            this.name = name;
        }
        public void setPhone(String phone) {
            this.phone = phone;
        }
        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }
        public String getPhone() {
            return phone;
        }
        public String getUrl() {
            return url;
        }
    }
    public class Vehicle{
        private String icon = null;
        private String name = null;
        private String type = null;
        private String localIcon = null;

        public Vehicle(JSONObject jsonObjectVehicle){
            setIcon(jsonObjectVehicle.optString("icon"));
            setName(jsonObjectVehicle.optString("name"));
            setLocalIcon(jsonObjectVehicle.optString("local_icon"));
            setType(jsonObjectVehicle.optString("type"));
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
        public void setName(String name) {
            this.name = name;
        }
        public void setLocalIcon(String localIcon) {
            this.localIcon = localIcon;
        }
        public void setType(String type) {
            this.type = type;
        }

        public String getIcon() {
            return icon;
        }
        public String getLocalIcon() {
            return localIcon;
        }
        public String getName() {
            return name;
        }
        public String getType() {
            return type;
        }

        public String getTypeDefinition(){
            switch (type){
                case "RAIL":
                    return "Rail.";
                case "METRO_RAIL":
                    return "Metro Rail.";
                case "SUBWAY":
                    return "Subway";
                case "TRAM":
                    return "Tram.";
                case "MONORAIL":
                    return "Monorail.";
                case "HEAVY_RAIL":
                    return "Heavy rail.";
                case "COMMUTER_TRAIN":
                    return "Commuter rail.";
                case "HIGH_SPEED_TRAIN":
                    return "High speed train.";
                case "BUS":
                    return "Bus.";
                case "INTERCITY_BUS":
                    return "Intercity bus.";
                case "TROLLEYBUS":
                    return "Trolleybus.";
                case "SHARE_TAXI":
                    return "Share taxi.";
                case "FERRY":
                    return "Ferry.";
                case "CABLE_CAR":
                    return "Cable Car";
                case "GONDOLA_LIFT":
                    return "Gondola Lift";
                case "FUNICULAR":
                    return "Funicular";
                case "OTHER":
                    return "Other";
                default:
                    return "Other";
            }
        }
    }
}
