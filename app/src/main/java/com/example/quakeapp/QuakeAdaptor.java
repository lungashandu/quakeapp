package com.example.quakeapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.graphics.drawable.GradientDrawable;

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

        String mag = formatDecimal(currentQuake.getMag());
        TextView magTextView = listItemView.findViewById(R.id.magnitudeTextView);
        magTextView.setText(mag);

        GradientDrawable magnitudeCircle = (GradientDrawable) magTextView.getBackground();
        int magnitudeColor = getMagnitudeColor(currentQuake.getMag());
        magnitudeCircle.setColor(magnitudeColor);

        final String LOCATION_SEPARATOR = " of ";
        String locationOffset;
        String primaryLocation;
        String originalLocation = currentQuake.getLocation();

        if (originalLocation.contains(LOCATION_SEPARATOR)) {
            String[] location = currentQuake.getLocation().split(LOCATION_SEPARATOR);
            locationOffset = location[0] + LOCATION_SEPARATOR;
            primaryLocation = location[1];

        } else {
            locationOffset = getContext().getString(R.string.nearThe);
            primaryLocation = originalLocation;
        }

        TextView locationOffsetTextView = listItemView.findViewById(R.id.locationOffsetTextView);
        locationOffsetTextView.setText(locationOffset);
        TextView primaryLocationTextView = listItemView.findViewById(R.id.primaryLocationTextView);
        primaryLocationTextView.setText(primaryLocation);

        Date dateObject = new Date(currentQuake.getTimeInMilliseconds());

        TextView dateTextView = listItemView.findViewById(R.id.dateTextView);
        String formattedDate = formatDate(dateObject);
        dateTextView.setText(formattedDate);

        TextView timeTextView = listItemView.findViewById(R.id.timeTextView);
        String formattedTime = formatTime(dateObject);
        timeTextView.setText(formattedTime);

        return listItemView;
    }

    private int getMagnitudeColor(double mag) {
        int magnitudeResourceID;
        int magfloor = (int) Math.floor(mag);
        switch (magfloor) {
            case 0:
            case 1:
                magnitudeResourceID = R.color.magnitude1;
                break;
            case 2:
                magnitudeResourceID = R.color.magnitude2;
                break;
            case 3:
                magnitudeResourceID = R.color.magnitude3;
                break;
            case 4:
                magnitudeResourceID = R.color.magnitude4;
                break;
            case 5:
                magnitudeResourceID = R.color.magnitude5;
                break;
            case 6:
                magnitudeResourceID = R.color.magnitude6;
                break;
            case 7:
                magnitudeResourceID = R.color.magnitude7;
                break;
            case 8:
                magnitudeResourceID = R.color.magnitude8;
                break;
            case 9:
                magnitudeResourceID = R.color.magnitude9;
                break;
            default:
                magnitudeResourceID = R.color.magnitude10;
                break;
        }
        return ContextCompat.getColor(getContext(), magnitudeResourceID);
    }

    private String formatDecimal(double mag){
        DecimalFormat decimalFormat = new DecimalFormat("0.0");
        return decimalFormat.format(mag);
    }

    private String formatTime(Date dateObject) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd MM yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatDate(Date dateObject) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
