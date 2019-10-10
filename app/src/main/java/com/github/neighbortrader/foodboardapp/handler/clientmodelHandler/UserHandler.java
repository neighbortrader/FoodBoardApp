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
    private static final String SHARED_PREFERENCES_FILE_USER_INFO = "userInfo";
    private static User userInstance;

    private static OnUserChangedListener callback;

    public static User getCurrentUserInstance() {
        return UserHandler.userInstance;
    }

    public static void setCurrentUserInstance(User userInstance) {
        UserHandler.userInstance = userInstance;
        callback.onUserStateChanged(true);
    }

    public static void iniUserHandler(OnUserChangedListener onUserChangedListener){
        callback = onUserChangedListener;
        callback.onUserStateChanged(false);
    }

    public static void saveToSharedPreferences(User user) {
        Log.d(TAG, "saveCurrentGroceriesToSharedPreferences()");

        if (user != null) {
            Gson gson = GsonHandler.getGsonInstance();

            String userToSaveAsJsonString = gson.toJson(user);

            SharedPreferences sharedPreferences = ContextHandler.getAppContext().getSharedPreferences(SHARED_PREFERENCES_FILE_USER_INFO, MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString(SHARED_PREFERENCES_FILE_USER_INFO, userToSaveAsJsonString);
            editor.commit();
        }
    }

    public static User loadUserFromSharedPreferences() {
        Log.d(TAG, "loadGroceriesFromSharedPreferences()");

        SharedPreferences sharedPreferences = ContextHandler.getAppContext().getSharedPreferences(SHARED_PREFERENCES_FILE_USER_INFO, MODE_PRIVATE);

        String userToSaveAsJsonString = sharedPreferences.getString(SHARED_PREFERENCES_FILE_USER_INFO, "");

        Gson gson = GsonHandler.getGsonInstance();
        User user = gson.fromJson(userToSaveAsJsonString, User.class);

        return user;
    }

    public static void loadUserAndUserData() {
        callback.onUserStateChanged(false);
        User loadedUser = UserHandler.loadUserFromSharedPreferences();

        if (loadedUser == null) {
            Log.d(TAG, "no user found");
        } else {
            Log.d(TAG, "found user and added to current user instance");
            UserHandler.setCurrentUserInstance(loadedUser);
        }
    }

}
