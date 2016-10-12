package com.caleb.comp592_5.response;

import org.json.JSONObject;

/**
 * Created by Caleb on 8/3/2016.
 */
public class MyTime {

    private String text = null;
    private String timeZone = null;
    private long value;

    public MyTime(JSONObject jsonObjectTime) {
        text = jsonObjectTime.optString("text");
        timeZone = jsonObjectTime.optString("time_zone");
        value = jsonObjectTime.optLong("value");
    }

    public String getText() {
        return text;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public long getValue() {
        return value;
    }
}
