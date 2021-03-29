package org.example;

public class PlacesData {

    private String businessName;
    private String businessAddress;
    private String cityName;
    private String stateName;
    private String zipCode;

    public PlacesData(String businessName, String businessAddress, String cityName, String stateName, String zipCode) {
        this.businessName = businessName;
        this.businessAddress = businessAddress;
        this.cityName = cityName;
        this.stateName = stateName;
        this.zipCode = zipCode;
    }

    public PlacesData() {
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }
}
