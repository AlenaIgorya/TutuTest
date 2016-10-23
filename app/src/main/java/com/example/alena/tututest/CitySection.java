package com.example.alena.tututest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.alena.tututest.model.City;
import com.example.alena.tututest.model.Station;
import com.google.gson.Gson;

import io.github.luizgrp.sectionedrecyclerviewadapter.StatelessSection;
import timber.log.Timber;

/**
 * Created by alena on 23.10.2016.
 */

class CitySection extends StatelessSection {

    private final AppCompatActivity context;
    private final City city;

    CitySection(City city, AppCompatActivity context) {
        super(R.layout.list_item_header, R.layout.list_item);
        this.context = context;
        this.city = city;
    }

    @Override
    public int getContentItemsTotal() {
        return city.getStationList().size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new StationViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return  new CityViewHolder(view);
    }


    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, int position) {
        StationViewHolder itemHolder = (StationViewHolder) holder;
        itemHolder.bindStation(city.getStationList().get(position));
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder) {
        CityViewHolder cityViewHolder = (CityViewHolder) holder;
        cityViewHolder.bindCity(city);
    }

    private class StationViewHolder extends RecyclerView.ViewHolder {

        private TextView mCountryTextView;
        private TextView mStationTextView;
        private ImageButton mImageButton;

        StationViewHolder(View oneView) {
            super(oneView);

            mCountryTextView = (TextView) oneView.findViewById(R.id.country_city_tv_item);
            mStationTextView = (TextView) oneView.findViewById(R.id.station_tv_item);
            mImageButton = (ImageButton) oneView.findViewById(R.id.button_info_item);
        }

        void bindStation(final Station station){
            Timber.v("station" + station);

            if (station != null) {
                mCountryTextView.setText(station.getCountryTitle() + ", " + station.getCityTitle());
                mStationTextView.setText(station.getStationTitle());

                mImageButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Gson gson = new Gson();
                        String cityString = gson.toJson(station);
                        Intent intent = new Intent(context, InfoStationActivity.class);
                        intent.putExtra("EXTRA_STATION", cityString);
                        context.startActivity(intent);
                    }
                });

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent answerIntent = new Intent();
                        String stationString = station.getStationTitle();
                        answerIntent.putExtra(DirectionActivity.CHOOSE, stationString);
                        context.setResult(context.RESULT_OK, answerIntent);
                        context.finish();
                    }
                });
            }
        }
    }

    private class CityViewHolder extends RecyclerView.ViewHolder {

        private TextView mCountryCityTextView;

        CityViewHolder(View oneView) {
            super(oneView);
            mCountryCityTextView = (TextView) oneView.findViewById(R.id.country_city_tv_item_header);
        }

        void bindCity(final City city){
            if (city != null ) {
                mCountryCityTextView.setText(city.getCountryTitle() + ", " + city.getCityTitle());
            }
        }
    }
}
