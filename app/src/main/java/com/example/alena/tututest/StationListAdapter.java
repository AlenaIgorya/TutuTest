package com.example.alena.tututest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.alena.tututest.model.Station;
import com.google.gson.Gson;

import java.util.List;

import timber.log.Timber;

/**
 * Created by alena on 22.10.2016.
 */

public class StationListAdapter extends RecyclerView.Adapter<StationListAdapter.StationViewHolder> {

    private final AppCompatActivity context;
    private List<Station> cityList;

    public StationListAdapter(List<Station> stationList, AppCompatActivity context) {
        this.cityList = stationList;
        this.context = context;
    }

    @Override
    public StationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new StationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StationViewHolder holder, int position) {
        Station city = cityList.get(position);
        holder.bindWish(city);
    }

    public void setCityList(List<Station> cityList) {
        this.cityList = cityList;
    }

    @Override
    public int getItemCount() {
        if (cityList != null) {
            return cityList.size();
        } else {
            return 0;
        }
    }

    class StationViewHolder extends RecyclerView.ViewHolder {

        private TextView mCountryTextView;
        private TextView mStationTextView;
        private ImageButton mImageButton;

        StationViewHolder(View oneView) {
            super(oneView);

            mCountryTextView = (TextView) oneView.findViewById(R.id.country_city_tv_item);
            mStationTextView = (TextView) oneView.findViewById(R.id.station_tv_item);
            mImageButton = (ImageButton) oneView.findViewById(R.id.button_info_item);
        }

        void bindWish(final Station station){

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
}