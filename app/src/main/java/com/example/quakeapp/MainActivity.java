package com.example.quakeapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Earthquakes> quakes = QueryUtils.extractEarthquakes();

        // Create a new {@link ArrayAdapter} of earthquakes
        QuakeAdaptor adapter = new QuakeAdaptor(this, quakes);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = findViewById(R.id.list);

        //Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);

        earthquakeListView.setOnItemClickListener((adapterView, view, i, l) -> {
            Earthquakes earthquakes = quakes.get(i);

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(earthquakes.getUrl()));
            startActivity(intent);
        });
    }
}