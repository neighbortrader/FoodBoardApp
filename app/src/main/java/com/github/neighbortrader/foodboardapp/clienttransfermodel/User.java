package com.github.neighbortrader.foodboardapp.clienttransfermodel;

import com.github.neighbortrader.foodboardapp.clientmodel.Address;
import com.github.neighbortrader.foodboardapp.transfer.Header;

import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;

public class User implements Header {

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
    public String toHeader() {
        return null;
    }
}
