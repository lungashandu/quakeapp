package com.example.quakeapp;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public final class QueryUtils {
    /**
     * Tag for the log message*/
    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Query the USGS dataset and return an {@link Earthquakes} object to represent earthquakes
     * @return
     */
    public static List<Earthquakes> fetchEarthquakeData(String requestUrl){
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String JSONResponse = null;
        try {
            JSONResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream ", e);
        }
        // Extract relevant fields from the JSON response and create an {@link Earthquake} object
        List<Earthquakes> earthquakes = extractFeatureFromJSON(JSONResponse);

        // Return the object
        return earthquakes;
    }

    /**
     * Returns new URL object from the given string URL
     */
    private static URL createUrl(String stringUrl){
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error creating URL ", e);
        }
        return url;
    }

    /** Make an HTTP request to the given URL and return a String as the response

    /**
     * Return a list of {@link Earthquakes} objects that has been built up from
     * parsing a JSON response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String JSONResponse = "";

        //if the url is null, the return early
        if (url == null){
            return JSONResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds*/);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200){
                inputStream = urlConnection.getInputStream();
                JSONResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error: response code " + urlConnection.getResponseCode());
            }
        } catch (IOException e){
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results. ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null){
                inputStream.close();
            }
        }
        return JSONResponse;
    }

    /**
     * Convert the {@link InputStream} into a string which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();
        if (inputStream != null){
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null){
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return an {@link ArrayList<Earthquakes>} object by parsing out information
     * about the first earthquake from the input earthquakeJSON string.
     */
    public static List<Earthquakes> extractFeatureFromJSON(String earthquakeJson) {

        // Create an empty ArrayList that we can start adding earthquakes to
        List<Earthquakes> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject root = new JSONObject(earthquakeJson);
            JSONArray featuresArray = root.getJSONArray("features");

            for (int i = 0; i < featuresArray.length(); i++) {
                JSONObject earthquakeObject = featuresArray.getJSONObject(i);
                JSONObject propertiesObject = earthquakeObject.getJSONObject("properties");

                double mag = propertiesObject.getDouble("mag");
                String location = propertiesObject.getString("place");
                long time = propertiesObject.getLong("time");
                String url = propertiesObject.getString("url");


                Earthquakes earthquake = new Earthquakes(mag, location, time, url);
                earthquakes.add(earthquake);
            }
        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        // Return the list of earthquakes
        return earthquakes;
    }
}
