package com.github.neighbortrader.foodboardapp.controller.view;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.github.neighbortrader.foodboardapp.Model.clientmodel.User;
import com.github.neighbortrader.foodboardapp.Model.viewmodel.AllOffersModel;
import com.github.neighbortrader.foodboardapp.controller.requests.GroceryRequestController;
import com.github.neighbortrader.foodboardapp.controller.requests.OnEventListener;
import com.github.neighbortrader.foodboardapp.controller.requests.RequestTyps;
import com.github.neighbortrader.foodboardapp.controller.requests.UserRequestController;
import com.github.neighbortrader.foodboardapp.etc.MyApplication;
import com.github.neighbortrader.foodboardapp.view.AllOffersActivity;

public class AllOffersController {

    public static String TAG = AllOffersController.class.getSimpleName();

    AllOffersModel allOffersModel;
    AllOffersActivity allOffersActivity;
    Context context;

    public AllOffersController(AllOffersModel allOffersModel, AllOffersActivity allOffersActivity) {
        this.allOffersModel = allOffersModel;
        this.allOffersActivity = allOffersActivity;
        context = MyApplication.getAppContext();
    }

    public void destroyAllOffers() {
        if (User.getCurrentUserInstance() != null) {
            User.getCurrentUserInstance().deleteToken();
            User.saveToSharedPreferences(User.getCurrentUserInstance());
        }
    }

    public void iniAppDataAndUser() {
        updateGroceryCategories();
        loadUserAndUserData();
    }

    private void updateGroceryCategories(){
        GroceryRequestController.builder(RequestTyps.GET_ALL_CATEGORIES, context, new OnEventListener<GroceryRequestController>() {
            @Override
            public void onResponse(GroceryRequestController groceryRequestController) {
                Toast toast = Toast.makeText(context, String.format("Successfully update grocery category"), Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onFailure(Exception e) {
                Toast toast = Toast.makeText(context, String.format("Error while updating grocery category (%s)", e.getMessage()), Toast.LENGTH_LONG);
                toast.show();
            }

            @Override
            public void onProgress(String progressUpdate) {
            }
        }).execute();
    }

    private void loadUserAndUserData(){
        User loadedUser = User.loadFromSharedPreferences();

        if (loadedUser == null) {
            Log.d(TAG, "no user found, trying to register a random user");
            Toast toast = Toast.makeText(context, String.format("no user found, trying to register a random user"), Toast.LENGTH_LONG);
            toast.show();

            User randomUserToCreate = User.generateRandomUser();

            UserRequestController.builder(RequestTyps.POST_NEW_USER, context, new OnEventListener<UserRequestController>() {
                @Override
                public void onResponse(UserRequestController userRequestController) {
                    User.createCurrentUserInstance(randomUserToCreate);
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
            User.createCurrentUserInstance(loadedUser);
        }
    }
}
