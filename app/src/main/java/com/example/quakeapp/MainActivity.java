package com.example.quakeapp;

import android.os.Bundle;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ArrayList<Earthquakes> quakes = new ArrayList<Earthquakes>();
        quakes.add(new Earthquakes("4", "Los Angeles", "11 Jan 2022"));
        quakes.add(new Earthquakes("4", "Los Angeles", "11 Jan 2022"));
        quakes.add(new Earthquakes("4", "Los Angeles", "11 Jan 2022"));
        quakes.add(new Earthquakes("4", "Los Angeles", "11 Jan 2022"));
        quakes.add(new Earthquakes("4", "Los Angeles", "11 Jan 2022"));
        quakes.add(new Earthquakes("4", "Los Angeles", "11 Jan 2022"));
        quakes.add(new Earthquakes("4", "Los Angeles", "11 Jan 2022"));
        quakes.add(new Earthquakes("4", "Los Angeles", "11 Jan 2022"));
        quakes.add(new Earthquakes("4", "Los Angeles", "11 Jan 2022"));
        quakes.add(new Earthquakes("4", "Los Angeles", "11 Jan 2022"));

        // Create a new {@link ArrayAdapter} of earthquakes
        QuakeAdaptor adapter = new QuakeAdaptor(this, quakes);

        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        //Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
    }
}