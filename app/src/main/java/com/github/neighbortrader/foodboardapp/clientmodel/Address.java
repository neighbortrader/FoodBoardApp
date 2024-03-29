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
    private String postCode;

    @Getter
    @Setter
    private String city;

    public Address(String street, String streetNumber, String postCode, String city) {
        this.street = street;
        this.streetNumber = streetNumber;
        this.postCode = postCode;
        this.city = city;
    }

    public String getFormattedSting() {
        if (this != null) {
            return new StringBuilder().append(postCode)
                    .append(", ")
                    .append(city)
                    .append(", ")
                    .append(street)
                    .append(", ")
                    .append(streetNumber).toString();
        }
        return "";
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", streetNumber='" + streetNumber + '\'' +
                ", postCode='" + postCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    @Override
    public Map<String, String> toNameValueMap() {
        Map<String, String> nameValueMap = new Hashtable<>();

        nameValueMap.put("zipNumber", postCode);
        nameValueMap.put("place", city);
        nameValueMap.put("street", street);
        nameValueMap.put("addressNumber", streetNumber);

        return nameValueMap;
    }
}
