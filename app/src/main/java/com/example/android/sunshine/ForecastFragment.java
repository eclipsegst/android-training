package com.example.android.sunshine;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zz on 3/29/2015.
 */
public class ForecastFragment extends Fragment {
    private ArrayAdapter<String> mForecastAdapter;

    public ForecastFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Add this line in order for this fragment to handle menu events
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.forecastfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        // Tha action bar will automatically handle clicks on Home/Up button,
        // so long as you specify a parent activity in AndroidManifest.xml
        int id = item.getItemId();
        if (id == R.id.action_refresh) {
            FetchWeatherTask  weatherTask = new FetchWeatherTask();
            weatherTask.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        String[] forecastArray = {
                "Today - Sunny - 88/63",
                "Tomorrow - Foggy - 70/40",
                "Weds - Cloudy - 72/63",
                "Thurs - Asteroids - 75/65",
                "Fri - Heavy Rain - 65/56",
                "Sat - HELP TRAPPED IN WEATHER STATION - 60/51",
                "Sun - Sunny - 80/68"
        };
        List<String> weekForecast = new ArrayList<String>(Arrays.asList(forecastArray));

        mForecastAdapter =
                new ArrayAdapter<String>(
                        // The current context (this fragment's parent activity)
                        getActivity(),
                        // ID of list item layout
                        R.layout.list_item_forecast,
                        // ID of the textview to populate
                        R.id.list_item_forecast_textview,
                        // Forecast data
                        weekForecast);

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        // Get a reference to the ListView, and attach this adapter to this ListView.
        ListView listView = (ListView) rootView.findViewById(R.id.listView_forecast);
        // Bind adapter to the ListView
        listView.setAdapter(mForecastAdapter);
        return rootView;
    }

    public class FetchWeatherTask extends AsyncTask<Void, Void, Void> {
        private final String LOG_TAG = FetchWeatherTask.class.getSimpleName();

        @Override
        protected Void doInBackground(Void... params) {
            // These two needs to be declared outside the try/catch
            // so that they can closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

            // Contain the raw JSON response as a string.
            String forecastJasonStr = null;

            try {
                // Construct the URL for the OpenWeattherMap query
                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7");

                // create the request to OpenWeatherMa, and open the connection;
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a string
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing).
                    // But it does make debugging a *lot* easier if you print out the completed buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty. No point in parsing.
                    return null;
                }
                forecastJasonStr = buffer.toString();
                Log.v(LOG_TAG, "Forecast JSON String: " + forecastJasonStr);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }
            return null;
        }
    }
}
