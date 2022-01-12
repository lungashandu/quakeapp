package com.example.quakeapp;

public class Earthquakes {
    private String mMag;
    private String mLocation;
    private String mDate;

    public Earthquakes(String mag, String location, String date) {
        this.mMag = mag;
        this.mLocation = location;
        this.mDate = date;
    }

    public String getmMag() {
        return mMag;
    }

    public String getmLocation() {
        return mLocation;
    }

    public String getmDate() {
        return mDate;
    }

}
