package com.example.alena.tututest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.alena.tututest.model.Point;
import com.example.alena.tututest.model.Station;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;

import timber.log.Timber;

/**
 * Created by alena on 21.10.2016.
 */

public class InfoStationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private TextView mTitle;
    private TextView mCountry;
    private TextView mRegion;

    private Station station;
    private double longitude;
    private double latitude;

    private GoogleMap mMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_info_station);

        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mActionBarToolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        } else {
            Timber.v("map_fragment is null");
        }

        String stationString = getIntent().getStringExtra("EXTRA_STATION");
        Gson gson = new Gson();
        station = gson.fromJson(stationString, Station.class);
        longitude = station.getStationPoint().getLongitude();
        latitude = station.getStationPoint().getLatitude();

        mTitle = (TextView) findViewById(R.id.station_title_info);
        mCountry = (TextView) findViewById(R.id.country_title_info);
        mRegion = (TextView) findViewById(R.id.region_title_info);

        mTitle.setText(station.getStationTitle());
        mCountry.setText(station.getCityTitle() + ", " +station.getCountryTitle());
        mRegion.setText(station.getRegionTitle());
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng stationLatLng = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(stationLatLng).title(""+ station.getStationTitle()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(stationLatLng));

        CameraPosition cameraPosition = new CameraPosition.Builder().target(stationLatLng).zoom((float) 14).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
