package com.example.android.map;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

public class CityActivity extends AppCompatActivity implements OnMapReadyCallback {

    GoogleMap mMap;
    boolean mapReady = false;
    private Button btnSanFrancisco;
    private Button btnNewYork;
    private Button btnTokyo;

    private MapFragment mapFragment;

    static final CameraPosition SANFRANCISCO = CameraPosition.builder()
            .target(new LatLng(37.7833, - 122.4167))
            .zoom(21)
            .bearing(0)
            .tilt(45)
            .build();
    static final CameraPosition NEWYORK = CameraPosition.builder()
            .target(new LatLng(40.7127, -74.0059))
            .zoom(17)
            .bearing(0)
            .tilt(45)
            .build();
    static final CameraPosition TOKYO = CameraPosition.builder()
            .target(new LatLng(35.6833, 139.6833))
            .zoom(17)
            .bearing(90)
            .tilt(45)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);

        btnSanFrancisco = (Button) findViewById(R.id.btnSanFrancisco);
        btnSanFrancisco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady) {
                    flyTo(SANFRANCISCO);
                }
            }
        });

        btnNewYork = (Button) findViewById(R.id.btnNewYork);
        btnNewYork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady) {
                    flyTo(NEWYORK);
                }
            }
        });

        btnTokyo = (Button) findViewById(R.id.btnTokyo);
        btnTokyo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mapReady) {
                    flyTo(TOKYO);
                }
            }
        });

        mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap map) {
        mapReady = true;
        mMap = map;
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        flyTo(NEWYORK);
    }

    private void flyTo(CameraPosition target) {
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(target), 10000, null);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_city, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
