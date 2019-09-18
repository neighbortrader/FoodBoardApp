package com.github.neighbortrader.foodboardapp.handler.clientmodelHandler;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.github.neighbortrader.foodboardapp.clientmodel.Address;
import com.github.neighbortrader.foodboardapp.clientmodel.User;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.handler.gsonHandler.GsonHandler;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.OnRequestEventListener;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.RequestTyps;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.UserRequestHandler;
import com.google.gson.Gson;

import java.util.UUID;

import static android.content.Context.MODE_PRIVATE;

public class UserHandler {

    public static final String TAG = UserHandler.class.getSimpleName();
    private static final String SHARED_PREFERENCES_FILE_USER_INFO = "userInfo";
    private static User userInstance;

    public static User getCurrentUserInstance() {
        return UserHandler.userInstance;
    }

    public static void setCurrentUserInstance(User userInstance) {
        UserHandler.userInstance = userInstance;
    }

    public static User generateRandomUser() {
        String username = UUID.randomUUID().toString();
        String password = "adm!n9Asswor!d";
        String email = username + "@dmx.com";
        Address address = new Address("Teststareße", "12a", "10315", "Berlin");

        return User.userBuilder(username, password, email, address);
    }

    public static void saveToSharedPreferences(User user) {
        Log.d(TAG, "saveCurrentGroceriesToSharedPreferences()");

        if (user != null) {
            Gson gson = GsonHandler.getGsonInstance();

            String userToSaveAsJsonString = null; //gson.toJson(user);

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
        User loadedUser = UserHandler.loadUserFromSharedPreferences();
        Context context = ContextHandler.getAppContext();

        if (loadedUser == null) {
            Log.d(TAG, "no user found, trying to register a random user");

            Toast toast = Toast.makeText(context, String.format("no user found, trying to register a random user"), Toast.LENGTH_LONG);
            toast.show();

            User randomUserToCreate = UserHandler.generateRandomUser();

            UserRequestHandler.builder(RequestTyps.POST_NEW_USER, context, new OnRequestEventListener<UserRequestHandler>() {
                @Override
                public void onResponse(UserRequestHandler userRequestController) {
                    UserHandler.setCurrentUserInstance(randomUserToCreate);
                    Toast toast = Toast.makeText(context, String.format("successfully registered user an added to current user instance"), Toast.LENGTH_LONG);
                    toast.show();
                    Log.d(TAG, "successfully registered user an added to current user instance");
                }

                @Override
                public void onFailure(Exception e) {
                    Log.e(TAG, "Exception while trying to create random user during startup", e);
                }

                @Override
                public void onProgress(String progressUpdate) {
                }
            }, randomUserToCreate).execute();
        } else {
            Log.d(TAG, "found user and added to current user instance");
            Toast toast = Toast.makeText(context, String.format("found user and added to current user instance"), Toast.LENGTH_LONG);
            toast.show();
            UserHandler.setCurrentUserInstance(loadedUser);
        }
    }

}
