package com.github.neighbortrader.foodboardapp.clientmodel;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class User implements ToNameValueMap {

    @Getter
    @Setter
    private String password;

    @Getter
    @Setter
    private String email;

    @Getter
    @Setter
    private Address address;

    @Getter
    @Setter
    private ArrayList<Offer> offerList;

    @Override
    public Map<String, String> toNameValueMap() {
        Map<String, String> nameValueMap = new Hashtable<>();

        nameValueMap.put("password", password);
        nameValueMap.put("email", password);

        nameValueMap.putAll(address.toNameValueMap());

        return nameValueMap;
    }
}
