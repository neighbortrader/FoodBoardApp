package com.github.neighbortrader.foodboardapp.clientmodel;

import com.github.neighbortrader.foodboardapp.clientmodel.Address;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class User {

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


}
