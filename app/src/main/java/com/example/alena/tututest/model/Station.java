package com.example.alena.tututest.model;

/**
 * Created by alena on 19.10.2016.
 */

public class Station {
    private String countryTitle;
    private Point point;
    private String districtTitle;
    private String cityId;
    private String cityTitle;
    private String regionTitle;
    private String stationId;
    private String stationTitle;

    public String getCountryTitle() {
        return countryTitle;
    }

    public Point getStationPoint() {
        return point;
    }

    public String getDistrictTitle() {
        return districtTitle;
    }

    public String getCityId() {
        return cityId;
    }

    public String getCityTitle() {
        return cityTitle;
    }

    public String getRegionTitle() {
        return regionTitle;
    }

    public String getStationId() {
        return stationId;
    }

    public String getStationTitle() {
        return stationTitle;
    }

    @Override
    public String toString() {
        return "Station{" +
                "countryTitle='" + countryTitle + '\'' +
                ", stationPoint=" + point +
                ", districtTitle='" + districtTitle + '\'' +
                ", cityId='" + cityId + '\'' +
                ", cityTitle='" + cityTitle + '\'' +
                ", regionTitle='" + regionTitle + '\'' +
                ", stationId='" + stationId + '\'' +
                ", stationTitle='" + stationTitle + '\'' +
                '}';
    }
}
