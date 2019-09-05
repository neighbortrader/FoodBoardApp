package com.github.neighbortrader.foodboardapp.clientmodel;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class Address {
    @Getter
    @Setter
    private String street;

    @Getter
    @Setter
    private String streetnumber;

    @Getter
    @Setter
    private String zipCode;

    @Getter
    @Setter
    private String city;

    @Getter
    @Setter
    private String country;

    public Address(String street, String streetnumber, String zipCode, String city, String country) {
        this.street = street;
        this.streetnumber = streetnumber;
        this.zipCode = zipCode;
        this.city = city;
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", streetnumber='" + streetnumber + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                '}';
    }
}
