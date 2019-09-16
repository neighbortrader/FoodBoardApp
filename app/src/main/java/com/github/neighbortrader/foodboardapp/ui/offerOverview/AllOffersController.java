package com.github.neighbortrader.foodboardapp.ui.offerOverview;

import android.content.Context;

import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.handler.errorHandler.ErrorHandler;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.OfferRequestHandler;

public class AllOffersController {

    AllOffersModel allOffersModel;
    AllOffersActivity allOffersActivity;
    Context context;

    private boolean waitingForResponse = false;

    public AllOffersController(AllOffersActivity allOffersActivity) {
        this.allOffersModel = new AllOffersModel(this);
        this.allOffersActivity = allOffersActivity;
        context = ContextHandler.getAppContext();

        allOffersModel.initialize();
    }

    public void invokeOfferUpdate() {
        if (waitingForResponse) {
            return;
        } else {
            waitingForResponse = true;
            allOffersModel.updateOffers();
        }
    }

    public void invokeUiUpdate(OfferRequestHandler offerRequestHandler) {
        allOffersActivity.updateUi(offerRequestHandler.getReceivedOffers());
        waitingForResponse = false;
    }

    public void invokeUiUpdate(Exception e) {
        ErrorHandler.buildErrorHandler(e).errorToast();
    }

    public void destroy() {
        allOffersModel.destroy();
    }
}
