package com.github.neighbortrader.foodboardapp.handler.clientmodelHandler;

import android.content.SharedPreferences;
import android.util.Log;

import com.github.neighbortrader.foodboardapp.clientmodel.Address;
import com.github.neighbortrader.foodboardapp.clientmodel.User;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.handler.gsonHandler.GsonHandler;
import com.google.gson.Gson;

import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;

public class UserHandler {

    public static final String TAG = UserHandler.class.getSimpleName();

    private static User userInstance;

    private static final String SHARED_PREFERENCES_FILE_USER_INFO = "userInfo";

    public static void setCurrentUserInstance(User userInstance) {
        UserHandler.userInstance = userInstance;
    }

    public static User getCurrentUserInstance() {
        return UserHandler.userInstance;
    }

    public static User generateRandomUser() {
        String username = UUID.randomUUID().toString();
        String password = "adm!n9Asswor!d";
        String userId = null;
        String email = "test@testemail.com";
        Address address = new Address("Teststareße", "12a", "10315", "Berlin");

        return User.userBuilder(username, password, userId, email, address, null);
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

    public static void deleteToken(User user) {
        user.setJwtToken(null);
    }
}
