package com.example.quakeapp;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.content.AsyncTaskLoader;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<Earthquakes>> {
    private static final String LOG_TAG = EarthquakeLoader.class.getName();
    private String mUrl;

    public EarthquakeLoader(@NonNull Context context, String url) {
        super(context);
        this.mUrl = url;
    }

    @Nullable
    @Override
    public List<Earthquakes> loadInBackground() {
        if (mUrl == null){return null;}
        List<Earthquakes> result = QueryUtils.fetchEarthquakeData(mUrl);
        return result;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }
}
