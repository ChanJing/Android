package com.caleb.comp592_5.response;

import com.caleb.comp592_5.common.logger.Log;

import org.json.JSONObject;

/**
 * Created by Caleb on 8/2/2016.
 */
public class MyPolyline {

    private String points = null;

    public MyPolyline(JSONObject jsonObjectOverviewPolyline){
        setPoints(jsonObjectOverviewPolyline.optString("points"));
    }

    public void setPoints(String points){
        if(points != null) {
            this.points = points;
        }else{
            Log.w("Points of Polyline: ","is null");
        }
    }

    public String getPoints(){
        return points;
    }
}
