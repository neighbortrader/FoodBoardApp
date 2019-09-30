package com.github.neighbortrader.foodboardapp.ui.offerOverview;

import android.content.Context;

import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.OfferRequestHandler;
import com.github.neighbortrader.foodboardapp.handler.toastHandler.ToastHandler;

import java.util.ArrayList;

public class AllOffersController {

    private AllOffersModel model;
    private AllOffersActivity allOffersActivity;
    Context context;

    public AllOffersController(AllOffersActivity allOffersActivity) {
        this.model = new AllOffersModel(this);
        this.allOffersActivity = allOffersActivity;
        context = ContextHandler.getAppContext();

        model.initialize();
    }

    public void invokeUpdate() {
        model.updateGroceryCategories();
        model.updateOffers();
    }

    public ArrayList<Offer> getCurrentOffers() {
        return model.getCurrentOffers();
    }

    public void invokeUiUpdate(OfferRequestHandler offerRequestHandler) {
        allOffersActivity.updateUi(offerRequestHandler.getReceivedOffers());
        setProgressBarRefreshing(false);
    }

    public void invokeUiUpdate(Exception e, String message) {
        ToastHandler.buildErrorToastHandler(e).errorToastWithCostumeMassage(message);
    }

    public void setProgressBarRefreshing(boolean isRefresing){
        allOffersActivity.setRefreshing(isRefresing);
    }

    public void destroy() {
        model.destroy();
    }
}
