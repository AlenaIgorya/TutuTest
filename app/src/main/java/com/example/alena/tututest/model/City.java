package com.example.alena.tututest.model;

import java.util.List;

/**
 * Created by alena on 19.10.2016.
 */

public class City {
    private String countryTitle;
    private Point point;
    private String districtTitle;
    private String cityId;
    private String cityTitle;
    private String regionTitle;
    private List<Station> stations;

    public List<Station> getStationList() {
        return stations;
    }

    @Override
    public String toString() {
        return "City{" +
                "countryTitle='" + countryTitle + '\'' +
                ", point=" + point +
                ", districtTitle='" + districtTitle + '\'' +
                ", cityId='" + cityId + '\'' +
                ", cityTitle='" + cityTitle + '\'' +
                ", regionTitle='" + regionTitle + '\'' +
                ", stationList=" + stations +
                '}';
    }

    public String getCountryTitle() {
        return countryTitle;
    }

    public String getCityTitle() {
        return cityTitle;
    }


}
