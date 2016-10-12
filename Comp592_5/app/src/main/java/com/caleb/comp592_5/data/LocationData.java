package com.caleb.comp592_5.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Caleb on 9/1/2016.
 */
public class LocationData implements Parcelable {

    private String id = null;
    private String address = null;
    private LatLng latLng = null;
    public LocationData() {

    }

    public LocationData(LatLng latLng, String address, String id){
        this.latLng = latLng;
        this.address = address;
        this.id = id;
    }


    public String getAddress() { return address;};

    public void setAddress(String name) { this.address = address;}

    public LatLng getLatLng() {return latLng;}

    public void setLatLng(LatLng latLng) { this.latLng = latLng;}

    public String getId(){ return id;}

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel out, int flags)
    {
        out.writeString(id);
        out.writeString(address);
        out.writeDouble(latLng.latitude);
        out.writeDouble(latLng.longitude);
    }

    public static final Parcelable.Creator<LocationData> CREATOR = new Creator<LocationData>()
    {
        @Override
        public LocationData[] newArray(int size)
        {
            return new LocationData[size];
        }

        @Override
        public LocationData createFromParcel(Parcel in)
        {
            return new LocationData(in);
        }
    };
    public LocationData(Parcel in)
    {
        id = in.readString();
        address = in.readString();
        latLng = new LatLng(in.readDouble(),in.readDouble());
    }
}
