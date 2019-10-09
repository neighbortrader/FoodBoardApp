package com.github.neighbortrader.foodboardapp.ui.offerOverview;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.ViewModel;

import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.clientmodel.User;
import com.github.neighbortrader.foodboardapp.handler.clientmodelHandler.GroceryHandler;
import com.github.neighbortrader.foodboardapp.handler.clientmodelHandler.UserHandler;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.GroceryRequestHandler;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.OfferRequestHandler;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.OnRequestEventListener;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.RequestTyps;
import com.github.neighbortrader.foodboardapp.handler.tokenHandler.TokenHandler;

import java.util.ArrayList;

public class OfferOverviewModel extends ViewModel {
    public String TAG = OfferOverviewModel.class.getSimpleName();

    private OfferOverviewController offerOverviewController;
    private Context context = ContextHandler.getAppContext();

    private ArrayList<Offer> currentOffers = new ArrayList<>();

    public OfferOverviewModel(OfferOverviewController offerOverviewController) {
        this.offerOverviewController = offerOverviewController;
    }

    public ArrayList<Offer> getCurrentOffers() {
        return currentOffers;
    }

    public void destroy() {
        User currentUser = UserHandler.getCurrentUserInstance();

        if (currentUser != null) {
            TokenHandler.removeToken();
            UserHandler.saveToSharedPreferences(currentUser);
            GroceryHandler.saveCurrentGroceriesToSharedPreferences();
        }
    }

    public void updateGroceryCategories() {
        Log.d(TAG, "updateGroceryCategories()");

        GroceryRequestHandler.builder(RequestTyps.GET_ALL_CATEGORIES, context, new OnRequestEventListener<GroceryRequestHandler>() {
            @Override
            public void onResponse(GroceryRequestHandler groceryRequestController) {

            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "onFailure() in updateGroceryCategories()");
                offerOverviewController.invokeUiUpdate(e, context.getResources().getString(R.string.general_unableToUpdaterGroceries));
            }

            @Override
            public void onProgress(String progressUpdate) {
            }
        }).execute();
    }

    public void updateOffers() {
        Log.d(TAG, "updateOffers()");

        OfferRequestHandler.builder(RequestTyps.GET_ALL_OFFERS, context, new OnRequestEventListener<OfferRequestHandler>() {
            @Override
            public void onResponse(OfferRequestHandler offerRequestHandler) {
                currentOffers = offerRequestHandler.getReceivedOffers();
                offerOverviewController.invokeUiUpdate();
            }

            @Override
            public void onFailure(Exception e) {
                Log.d(TAG, "onFailure() in updateOffers()");
                offerOverviewController.invokeUiUpdate(e, context.getResources().getString(R.string.general_unableToUpdateOffers));
                offerOverviewController.setProgressBarRefreshing(false);
            }

            @Override
            public void onProgress(String progressUpdate) {

            }
        }).execute();
    }
}
