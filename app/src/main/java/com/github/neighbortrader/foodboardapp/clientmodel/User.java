package com.github.neighbortrader.foodboardapp.clientmodel;

import com.github.neighbortrader.foodboardapp.clientmodel.Address;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import okhttp3.FormBody;

public class User implements ToFormBody{

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
    public FormBody toFormBody() {
        Map<String, String> nameValueMap= new Hashtable<>();

        nameValueMap.put("password", password);
        nameValueMap.put("email", password);
        nameValueMap.put("address", address.toString());

        FormBody.Builder formBodyBuilder = new FormBody.Builder();

        nameValueMap.forEach((k, v) ->{
            formBodyBuilder.add(k, v);
        });

        return  formBodyBuilder.build();
    }

}
