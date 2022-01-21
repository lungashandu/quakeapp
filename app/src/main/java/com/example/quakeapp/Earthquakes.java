package com.example.quakeapp;


public class Earthquakes {
    private double mMag;
    private String mLocation;
    private long mTimeInMilliseconds;


    public Earthquakes(double mag, String location, long timeInMilliseconds) {
        this.mMag = mag;
        this.mLocation = location;
        this.mTimeInMilliseconds = timeInMilliseconds;
    }

    public double getMag() {
        return mMag;
    }

    public String getLocation() { return mLocation; }

    public long getTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }



}
