package com.github.neighbortrader.foodboardapp.clientmodel;

import android.content.SharedPreferences;
import android.util.Log;

import com.auth0.android.jwt.JWT;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.handler.gsonHandler.GsonHandler;
import com.google.gson.Gson;

import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

import static android.content.Context.MODE_PRIVATE;

public class User implements ToNameValueMap {
    public static final String TAG = User.class.getSimpleName();

    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private Address address;

    private User(String username, String password, String email, Address address) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
    }

    public static User userBuilder(String username, String password, String email, Address address){
        return new User(username, password, email, address);
    }

    @Override
    public Map<String, String> toNameValueMap() {
        Map<String, String> nameValueMap = new Hashtable<>();

        nameValueMap.put("username", username);
        nameValueMap.put("password", password);
        nameValueMap.put("email", email);

        nameValueMap.putAll(address.toNameValueMap());

        return nameValueMap;
    }
}
