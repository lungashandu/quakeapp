package com.example.quakeapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class QuakeAdaptor extends ArrayAdapter<Earthquakes> {

    public QuakeAdaptor(Context context, ArrayList<Earthquakes> quakes){
        super(context, 0, quakes);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listItemView = convertView;
        if (listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
        }

        Earthquakes currentQuake = getItem(position);

        TextView magTextView = listItemView.findViewById(R.id.magnitudeTextView);
        magTextView.setText(currentQuake.getmMag());

        TextView locationTextView = listItemView.findViewById(R.id.locationTextView);
        locationTextView.setText(currentQuake.getmLocation());

        TextView dateTextView = listItemView.findViewById(R.id.dateTextView);
        dateTextView.setText(currentQuake.getmDate());

        return listItemView;
    }
}
