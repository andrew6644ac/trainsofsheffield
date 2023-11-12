package com.sheffieldtrains.domain.user;

public class Address {
    private String houseNumber;
    private String roadName;
    private String cityName;
    private String postcode;

    public Address(String houseNumber, String roadName, String cityName, String postcode) {
        this.houseNumber = houseNumber;
        this.roadName = roadName;
        this.cityName = cityName;
        this.postcode = postcode;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getRoadName() {
        return roadName;
    }

    public void setRoadName(String roadName) {
        this.roadName = roadName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }
}
