package com.github.neighbortrader.foodboardapp.ui.offerOverview;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.github.neighbortrader.foodboardapp.clientmodel.User;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.GroceryRequestHandler;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.OfferRequestHandler;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.OnRequestEventListener;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.RequestTyps;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.UserRequestHandler;

public class AllOffersModel extends ViewModel {
    public String TAG = AllOffersModel.class.getSimpleName();

    private AllOffersController allOffersController;
    private Context context = ContextHandler.getAppContext();

    public AllOffersModel(AllOffersController allOffersController) {
        this.allOffersController = allOffersController;
    }

    // TODO: doesn't really belong here
    public void initialize() {
        updateGroceryCategories();
        loadUserAndUserData();
    }

    public void destroy() {
        if (User.getCurrentUserInstance() != null) {
            User.getCurrentUserInstance().deleteToken();
            User.saveToSharedPreferences(User.getCurrentUserInstance());
        }
    }

    private void updateGroceryCategories() {
        GroceryRequestHandler.builder(RequestTyps.GET_ALL_CATEGORIES, context, new OnRequestEventListener<GroceryRequestHandler>() {
            @Override
            public void onResponse(GroceryRequestHandler groceryRequestController) {

            }

            @Override
            public void onFailure(Exception e) {

            }

            @Override
            public void onProgress(String progressUpdate) {
            }
        }).execute();
    }

    public void updateOffers() {
        OfferRequestHandler.builder(RequestTyps.GET_ALL_OFFERS, context, new OnRequestEventListener<OfferRequestHandler>() {
            @Override
            public void onResponse(OfferRequestHandler object) {
                allOffersController.invokeUiUpdate(object);
            }

            @Override
            public void onFailure(Exception e) {
                allOffersController.invokeUiUpdate(e);
            }

            @Override
            public void onProgress(String progressUpdate) {

            }
        }).execute();
    }

    private void loadUserAndUserData() {
        User loadedUser = User.loadFromSharedPreferences();

        if (loadedUser == null) {
            Log.d(TAG, "no user found, trying to register a random user");
            Toast toast = Toast.makeText(context, String.format("no user found, trying to register a random user"), Toast.LENGTH_LONG);
            toast.show();

            User randomUserToCreate = User.generateRandomUser();

            UserRequestHandler.builder(RequestTyps.POST_NEW_USER, context, new OnRequestEventListener<UserRequestHandler>() {
                @Override
                public void onResponse(UserRequestHandler userRequestController) {
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
