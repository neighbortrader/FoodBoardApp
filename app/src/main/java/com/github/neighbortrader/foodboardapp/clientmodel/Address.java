package com.github.neighbortrader.foodboardapp.clientmodel;

import java.util.Hashtable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class Address implements ToNameValueMap {
    @Getter
    @Setter
    private String street;

    @Getter
    @Setter
    private String streetNumber;

    @Getter
    @Setter
    private String zipCode;

    @Getter
    @Setter
    private String city;

    @Getter
    @Setter
    private String country;

    public Address(String street, String streetNumber, String zipCode, String city, String country) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }

    @Override
    public Map<String, String> toNameValueMap() {
        Map<String, String> nameValueMap = new Hashtable<>();

        nameValueMap.put("street", street);
        nameValueMap.put("streetNumber", streetNumber);
        nameValueMap.put("zipCode", zipCode);
        nameValueMap.put("city", city);
        nameValueMap.put("country", country);

        return nameValueMap;
    }
}
