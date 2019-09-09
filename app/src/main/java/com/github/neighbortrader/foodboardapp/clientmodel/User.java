package com.github.neighbortrader.foodboardapp.clientmodel;

import android.content.SharedPreferences;

import com.auth0.android.jwt.JWT;
import com.github.neighbortrader.foodboardapp.MyApplication;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import static android.content.Context.MODE_PRIVATE;

public class User implements ToNameValueMap {

    private static final String SHARED_PREFERENCES_FILE_USER_INFO = "userInfo";

    private static User userInstance;

    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String userId;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private Address address;
    @Getter
    @Setter
    private ArrayList<Offer> offerList;

    @Getter
    @Setter
    private JWT jwtToken;

    private static void createUserInstance(User userInstance) {
        User.userInstance = userInstance;
    }

    public static User getCurrentUserInstance() {
        return User.userInstance;
    }

    public User(String username, String password, String userId, String email, Address address, ArrayList<Offer> offerList, JWT jwtToken) {
        this.username = username;
        this.password = password;
        this.userId = userId;
        this.email = email;
        this.address = address;
        this.offerList = offerList;
        this.jwtToken = jwtToken;
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

    public static void saveToSharedPreferences(User user) {
        Gson gson = new Gson();
        String userToSaveAsJsonString = gson.toJson(user);

        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(SHARED_PREFERENCES_FILE_USER_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SHARED_PREFERENCES_FILE_USER_INFO, userToSaveAsJsonString);
        editor.commit();
    }

    public static User loadFromSharedPreferences() {
        SharedPreferences sharedPreferences = MyApplication.getAppContext().getSharedPreferences(SHARED_PREFERENCES_FILE_USER_INFO, MODE_PRIVATE);

        String userToSaveAsJsonString = sharedPreferences.getString(SHARED_PREFERENCES_FILE_USER_INFO, "");

        Gson gson = new Gson();
        User user = gson.fromJson(userToSaveAsJsonString, User.class);

        return  user;
    }
}
