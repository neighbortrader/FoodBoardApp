package com.github.neighbortrader.foodboardapp.ui.offerOverview;

import android.content.Context;

import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.OfferRequestHandler;
import com.github.neighbortrader.foodboardapp.handler.toastHandler.ToastHandler;
import com.github.neighbortrader.foodboardapp.ui.offerDetail.OfferDetailFragment;

import java.util.ArrayList;

public class OfferOverviewController {

    private OfferOverviewModel model;
    private OfferOverviewFragment offerOverviewFragment;
    Context context;

    public OfferOverviewController(OfferOverviewFragment offerOverviewFragment) {
        this.model = new OfferOverviewModel(this);
        this.offerOverviewFragment = offerOverviewFragment;
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

    public void invokeUiUpdate() {
        offerOverviewFragment.updateUi(model.getCurrentOffers());
        setProgressBarRefreshing(false);
    }

    public void invokeUiUpdate(Exception e, String message) {
        ToastHandler.buildErrorToastHandler(e).errorToastWithCostumeMassage(message);
    }

    public void setProgressBarRefreshing(boolean isRefresing){
        offerOverviewFragment.setRefreshing(isRefresing);
    }

    public void destroy() {
        model.destroy();
    }
}
