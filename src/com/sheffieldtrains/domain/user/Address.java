package com.sheffieldtrains.domain.user;

import java.util.Objects;

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

    public Address() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return getHouseNumber().equals(address.getHouseNumber()) && getRoadName().equals(address.getRoadName()) && getCityName().equals(address.getCityName()) && getPostcode().equals(address.getPostcode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getHouseNumber(), getRoadName(), getCityName(), getPostcode());
    }
}
