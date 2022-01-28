package com.example.quakeapp;


public class Earthquakes {
    private double mMag;
    private String mLocation;
    private long mTimeInMilliseconds;
    private String mUrl;


    public Earthquakes(double mag, String location, long timeInMilliseconds, String url) {
        this.mMag = mag;
        this.mLocation = location;
        this.mTimeInMilliseconds = timeInMilliseconds;
        this.mUrl = url;
    }

    public double getMag() {
        return mMag;
    }

    public String getLocation() { return mLocation; }

    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public String getUrl() {return  mUrl;}



}
