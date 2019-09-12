package com.github.neighbortrader.foodboardapp.clientmodel;

import android.content.SharedPreferences;
import android.util.Log;

import com.auth0.android.jwt.JWT;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.handler.gsonHandler.GsonHandler;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

import static android.content.Context.MODE_PRIVATE;

public class User implements ToNameValueMap {
    public static final String TAG = User.class.getSimpleName();

    private static final String SHARED_PREFERENCES_FILE_USER_INFO = "userInfo";

    private static boolean currentSessionHasUser = false;
    private static User userInstance;

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

    @Getter
    @Setter
    private JWT jwtToken;

    private User(String username, String password, String userId, String email, Address address, JWT jwtToken) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.address = address;
        this.jwtToken = jwtToken;
    }

    public static void createCurrentUserInstance(User userInstance) {
        User.userInstance = userInstance;

        if (userInstance == null) {
            currentSessionHasUser = false;
        } else {
            currentSessionHasUser = true;
        }
    }

    public static User getCurrentUserInstance() {
        return User.userInstance;
    }

    public static User generateRandomUser() {
        String username = UUID.randomUUID().toString();
        String password = "adm!n9Asswor!d";
        String userId = null;
        String email = "test@testemail.com";
        Address address = new Address("Teststareße", "12a", "10315", "Berlin");

        return new User(username, password, userId, email, address, null);
    }

    public static void saveToSharedPreferences(User user) {
        Log.d(TAG, "saveToSharedPreferences()");

        if (user != null) {
            Gson gson = GsonHandler.getGsonInstance();
            // FIXME Caused by: java.lang.IllegalArgumentException: class java.text.DecimalFormat declares multiple JSON fields named maximumFractionDigits #23
            String userToSaveAsJsonString = null; //gson.toJson(user);

            SharedPreferences sharedPreferences = ContextHandler.getAppContext().getSharedPreferences(SHARED_PREFERENCES_FILE_USER_INFO, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(SHARED_PREFERENCES_FILE_USER_INFO, userToSaveAsJsonString);
            editor.commit();
        }
    }

    public static User loadFromSharedPreferences() {
        Log.d(TAG, "loadFromSharedPreferences()");

        SharedPreferences sharedPreferences = ContextHandler.getAppContext().getSharedPreferences(SHARED_PREFERENCES_FILE_USER_INFO, MODE_PRIVATE);

        String userToSaveAsJsonString = sharedPreferences.getString(SHARED_PREFERENCES_FILE_USER_INFO, "");

        Gson gson = GsonHandler.getGsonInstance();
        User user = gson.fromJson(userToSaveAsJsonString, User.class);

        return user;
    }

    public void deleteToken() {
        this.setJwtToken(null);
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
