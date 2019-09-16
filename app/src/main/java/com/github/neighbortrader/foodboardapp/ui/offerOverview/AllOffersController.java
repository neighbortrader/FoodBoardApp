package com.github.neighbortrader.foodboardapp.ui.offerOverview;

import android.content.Context;

import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.handler.toastHandler.ToastHandler;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.OfferRequestHandler;

import java.util.ArrayList;

public class AllOffersController {

    AllOffersModel model;
    AllOffersActivity allOffersActivity;
    Context context;

    private boolean waitingForResponse = false;

    public AllOffersController(AllOffersActivity allOffersActivity) {
        this.model = new AllOffersModel(this);
        this.allOffersActivity = allOffersActivity;
        context = ContextHandler.getAppContext();

        model.initialize();
    }

    public void invokeOfferUpdate() {
        if (waitingForResponse) {
            return;
        } else {
            waitingForResponse = true;
            model.updateOffers();
        }
    }

    public ArrayList<Offer> getCurrentOffers(){
        return model.getCurrentOffers();
    }

    public void invokeUiUpdate(OfferRequestHandler offerRequestHandler){
        allOffersActivity.updateUi(offerRequestHandler.getReceivedOffers());
        waitingForResponse = false;
        allOffersActivity.setRefreshing(false);
    }

    public void invokeUiUpdate(Exception e) {
        ToastHandler.buildErrorToastHandler(e).errorToastWithCostumeMassage(ContextHandler.getAppContext().getResources().getString(R.string.unableToUpdate));
        waitingForResponse = false;
        allOffersActivity.setRefreshing(false);
    }

    public void destroy() {
        model.destroy();
    }
}
