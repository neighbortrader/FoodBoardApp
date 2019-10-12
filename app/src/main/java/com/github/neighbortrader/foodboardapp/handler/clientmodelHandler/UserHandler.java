package com.github.neighbortrader.foodboardapp.handler.clientmodelHandler;

import android.content.SharedPreferences;
import android.util.Log;

import com.github.neighbortrader.foodboardapp.clientmodel.User;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.handler.gsonHandler.GsonHandler;
import com.google.gson.Gson;

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
        if (userInstance != null) {
            UserHandler.userInstance = userInstance;
            callback.onUserStateChanged(true);
        } else {
            UserHandler.userInstance = null;
            callback.onUserStateChanged(false);
        }
    }

    public static void iniUserHandler(OnUserChangedListener onUserChangedListener) {
        callback = onUserChangedListener;
        callback.onUserStateChanged(false);
    }

    public static User buildLoginUser(String username, String password, boolean staySignedIn) {
        User loginUser = User.userBuilder();
        loginUser.setUsername(username);
        loginUser.setPassword(password);
        loginUser.setStaySignedIn(staySignedIn);
        return loginUser;
    }

    public static void saveUser(User user) {
        Log.d(TAG, "saveUser()");

        if (user != null && user.isStaySignedIn()) {
            toSharedPreferences(user);
        }
    }

    public static void deleteUser() {
        Log.d(TAG, "deleteUser");
        toSharedPreferences(null);
        UserHandler.setCurrentUserInstance(null);
    }

    private static void toSharedPreferences(User user) {
        Gson gson = GsonHandler.getGsonInstance();

        String userToSaveAsJsonString = gson.toJson(user);

        SharedPreferences sharedPreferences = ContextHandler.getAppContext().getSharedPreferences(SHARED_PREFERENCES_FILE_USER_INFO, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SHARED_PREFERENCES_FILE_USER_INFO, userToSaveAsJsonString);
        editor.commit();
    }

    public static User loadUser() {
        Log.d(TAG, "loadUser()");

        SharedPreferences sharedPreferences = ContextHandler.getAppContext().getSharedPreferences(SHARED_PREFERENCES_FILE_USER_INFO, MODE_PRIVATE);
        String userToSaveAsJsonString = sharedPreferences.getString(SHARED_PREFERENCES_FILE_USER_INFO, "");

        Gson gson = GsonHandler.getGsonInstance();
        User user = gson.fromJson(userToSaveAsJsonString, User.class);

        if (user != null) {
            Log.d(TAG, String.format("loaded user %s", user.toString()));
        }else{
            Log.d(TAG, "no user loaded");
        }

        return user;
    }

    public static void loadUserAndUserData() {
        callback.onUserStateChanged(false);
        User loadedUser = UserHandler.loadUser();

        if (loadedUser == null) {
            Log.d(TAG, "no user found");
        } else {
            if (loadedUser.isStaySignedIn()) {
                Log.d(TAG, "found user and added to current user instance");
                callback.onUserStateChanged(true);
                UserHandler.setCurrentUserInstance(loadedUser);
            } else if (!loadedUser.isStaySignedIn()) {
                Log.d(TAG, "found user but not added to current user instance because of isStaySignedIn equals false");
                UserHandler.setCurrentUserInstance(null);
            }
        }
    }
}
