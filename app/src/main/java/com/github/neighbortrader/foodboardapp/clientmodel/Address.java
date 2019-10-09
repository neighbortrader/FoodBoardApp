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

    public Address(String street, String streetNumber, String zipCode, String city) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.zipCode = zipCode;
        this.city = city;
    }

    public String getFormattedSting(){
        return zipCode + " " + city + ", " + street + " " + streetNumber;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    @Override
    public Map<String, String> toNameValueMap() {
        Map<String, String> nameValueMap = new Hashtable<>();

        nameValueMap.put("zipNumber", zipCode);
        nameValueMap.put("place", city);
        nameValueMap.put("street", street);
        nameValueMap.put("addressNumber", streetNumber);

        return nameValueMap;
    }
}
