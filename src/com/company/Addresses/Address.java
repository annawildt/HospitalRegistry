package com.company.Addresses;

public class Address {
    private String name;
    private int zipcode;
    private String area;
    private String country;
    private AddressType addressType;

    public Address(String name, int zipcode, String area, String country, AddressType addressType) {
        this.name = name;
        this.zipcode = zipcode;
        this.area = area;
        this.country = country;
        this.addressType = addressType;
    }

    public String getName() {
        return name;
    }

    public int getZipcode() {
        return zipcode;
    }

    public String getArea() {
        return area;
    }

    public String getCountry() {
        return country;
    }

    public AddressType getAddressType() {
        return addressType;
    }
}
