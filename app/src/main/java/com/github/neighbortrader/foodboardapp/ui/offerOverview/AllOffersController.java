package com.github.neighbortrader.foodboardapp.ui.offerOverview;

import android.content.Context;
import android.widget.Toast;

import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.OfferRequestHandler;

public class AllOffersController {

    public static String TAG = AllOffersController.class.getSimpleName();

    AllOffersModel allOffersModel;
    AllOffersActivity allOffersActivity;

    Context context;

    private boolean waitingForResponse = false;

    public AllOffersController(AllOffersActivity allOffersActivity) {
        this.allOffersModel = new AllOffersModel(this);
        this.allOffersActivity = allOffersActivity;
        context = ContextHandler.getAppContext();

        allOffersModel.iniAppDataAndUser();
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
        if (offerRequestHandler.isWasSuccessful()) {
            allOffersActivity.updateUi(offerRequestHandler.getReceivedOffers());
        } else {
            errorToast(new Exception("Unknown error occurred"));
        }

        waitingForResponse = false;
    }

    public void invokeUiUpdate(Exception e) {
        errorToast(e);
        waitingForResponse = false;
    }

    public void errorToast(Exception e) {
        CharSequence text = e.getMessage();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


    public void destroy() {
        allOffersModel.destroy();
    }

    public AllOffersModel getAllOffersModel() {
        return allOffersModel;
    }
}
