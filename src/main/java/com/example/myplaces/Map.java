package com.example.myplaces;

public class Map {

    String locationName;
    double latitude;
    double longitude;
    String country;

    Map(String locationName,double latitude,double longitude,String country){

        this.locationName = locationName;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
