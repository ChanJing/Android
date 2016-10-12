package com.caleb.comp592_5.response;

import com.caleb.comp592_5.common.logger.Log;

import org.json.JSONObject;

/**
 * Created by Caleb on 8/3/2016.
 */
public class Distance {

    private String text = null;
    private long value;

    public Distance(JSONObject distance) {
        if(distance != null) {
            this.text = distance.optString("text");
            this.value = distance.optLong("value");
        }else{
            Log.w("Distance: ","is null");
        }
    }

    public String getText() {
        return text;
    }

    public long getValue() {
        return value;
    }
}
