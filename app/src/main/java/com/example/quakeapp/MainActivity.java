package com.example.quakeapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Earthquakes>> {

    private static final String USGS_REQUEST_URL = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";
    private  QuakeAdaptor mAdapter;
    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create a new {@link ArrayAdapter} of earthquakes
        mAdapter = new QuakeAdaptor(this, new ArrayList<>());
        ListView earthquakeListView = findViewById(R.id.list);
        mEmptyStateTextView = findViewById(R.id.emptyState);

        //Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(mAdapter);
        earthquakeListView.setEmptyView(mEmptyStateTextView);

        // Set an item click listener on the ListView, which sends an intent to a web browser
        // to open a website with more information about the selected earthquake.
        earthquakeListView.setOnItemClickListener((AdapterView<?> adapterView, View view, int position, long l) -> {
            // Find the current earthquake that was clicked on
            Earthquakes currentEarthquake = mAdapter.getItem(position);

            // Convert the String URL into a URI object (to pass into the Intent constructor)
            Uri earthquakeUri = Uri.parse(currentEarthquake.getUrl());

            // Create a new intent to view the earthquake URI
            Intent websiteIntent = new Intent(Intent.ACTION_VIEW, earthquakeUri);

            // Send the intent to launch a new activity
            startActivity(websiteIntent);
        });

        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        // If there is a network connection, fetch data
        if (networkInfo != null && networkInfo.isConnected()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(1, null, this);
        } else {
            ProgressBar progressBar = findViewById(R.id.loading_spinner);
            progressBar.setVisibility(View.GONE);

            mEmptyStateTextView.setText(R.string.noConnection);
        }

    }


    @Override
    public Loader<List<Earthquakes>> onCreateLoader(int i, Bundle bundle) {
        return new EarthquakeLoader(this, USGS_REQUEST_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Earthquakes>> loader, List<Earthquakes> earthquakes) {
        ProgressBar progressBar = findViewById(R.id.loading_spinner);
        progressBar.setVisibility(View.GONE);
        // Clear the adapter of previous earthquake data
        mAdapter.clear();

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (earthquakes != null && !earthquakes.isEmpty()) {
            mAdapter.addAll(earthquakes);
        }
        mEmptyStateTextView.setText(R.string.empty);
    }

    @Override
    public void onLoaderReset(Loader<List<Earthquakes>> loader) {
        mAdapter.clear();
    }
}