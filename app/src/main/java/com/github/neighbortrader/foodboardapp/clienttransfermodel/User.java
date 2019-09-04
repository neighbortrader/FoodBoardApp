package com.github.neighbortrader.foodboardapp.clienttransfermodel;

import com.github.neighbortrader.foodboardapp.clientmodel.Address;

import java.io.IOException;
import java.util.ArrayList;

import lombok.Getter;
import lombok.Setter;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class User implements Request {

    @Getter
    @Setter
    private String nickname;

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
    public String post() {
        return "";
    }

    @Override
    public String get() throws IOException {
        String url = "www.google.com";
        OkHttpClient client = new OkHttpClient();

        okhttp3.Request request = new okhttp3.Request.Builder().url(url).build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }


    }
}
