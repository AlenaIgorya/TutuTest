package com.example.alena.tututest;

import android.content.Context;
import android.content.res.AssetManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.alena.tututest.model.CitiesObject;
import com.example.alena.tututest.model.City;
import com.example.alena.tututest.model.Station;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;
import timber.log.Timber;

/**
 * Created by alena on 20.10.2016.
 */

public class DirectionActivity extends AppCompatActivity implements MenuItemCompat.OnActionExpandListener {

    public final static String CHOOSE = "alena.tutuTest";
    public final static String DIRECTION = "DIRECTION";
    public final static String DIRECTION_FROM = "DIRECTIONFROM";
    public final static String DIRECTION_TO = "DIRECTIONTO";

    private List<City> mList;
    private List<Station> mListStation;
    private List<Station> mListStationSearch;
    private CitiesObject citiesObject;

    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;

    private StationListAdapter mAdapter;
    private SectionedRecyclerViewAdapter sectionAdapter;

    private Context context = this;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direction);

        Toolbar mActionBarToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        setSupportActionBar(mActionBarToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        mRecyclerView = (RecyclerView) findViewById(R.id.station_recycler_view);
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mListStation = new ArrayList<>();
        mListStationSearch = new ArrayList<>();

        new ParseJsonTask().execute();

        sectionAdapter = new SectionedRecyclerViewAdapter();
        mAdapter = new StationListAdapter(mListStation, (AppCompatActivity) context);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        mRecyclerView.setAdapter(sectionAdapter);
    }

    @Override
    public boolean onMenuItemActionExpand(MenuItem item) {
        return false;
    }

    @Override
    public boolean onMenuItemActionCollapse(MenuItem item) {
        return false;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_direction_activity, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                Timber.v("запрос:  " + query);
                mListStationSearch.clear();
                for (Station station: mListStation){
                    if (station.getStationTitle().toLowerCase().contains(query.toLowerCase())){
                        mListStationSearch.add(station);
                    }
                }

                mAdapter.setCityList(mListStationSearch);
                mAdapter.notifyDataSetChanged();

                mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                mRecyclerView.setAdapter(mAdapter);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return true;
            }
        });
        return true;
    }
    

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            Toast.makeText(this, "Станция не выбрана", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private class ParseJsonTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... voids) {

            AssetManager am = getAssets();
            try {
                InputStream is = am.open("allStations.json");
                InputStreamReader isr = new InputStreamReader(is);
                Gson gson = new Gson();
                citiesObject = gson.fromJson(isr, CitiesObject.class);
            } catch (IOException e) {
                Timber.e(e);
            }

            String direction = getIntent().getStringExtra(DIRECTION);
            Timber.v("activity_direction: " + direction);

            if (direction.equals(DIRECTION_FROM)) {
                mList = citiesObject.getCitiesFrom();
            } else if (direction.equals(DIRECTION_TO)) {
                mList = citiesObject.getCitiesTo();
            }

            for (City city : mList) {
                sectionAdapter.addSection(new CitySection(city, (AppCompatActivity) context));
                if (city.getStationList() != null) {
                    for (Station station : city.getStationList()) {
                        mListStation.add(station);
                    }
                } else {
                    Timber.v("station list is empty");
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            mProgressBar.setVisibility(View.GONE);
            sectionAdapter.notifyDataSetChanged();
        }
    }
}
