package com.github.neighbortrader.foodboardapp.ui.offerOverview;

import android.content.Context;

import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.handler.toastHandler.ToastHandler;

import java.util.ArrayList;

public class OfferOverviewController {

    Context context;
    private OfferOverviewModel model;
    private OfferOverviewFragment offerOverviewActivity;

    public OfferOverviewController(OfferOverviewFragment offerOverviewActivity) {
        this.model = new OfferOverviewModel(this);
        this.offerOverviewActivity = offerOverviewActivity;
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
        offerOverviewActivity.updateUi(model.getCurrentOffers());
        setProgressBarRefreshing(false);
    }

    public void invokeUiUpdate(Exception e, String message) {
        ToastHandler.buildErrorToastHandler(e).errorToastWithCostumeMassage(message);
    }

    public void setProgressBarRefreshing(boolean isRefresing) {
        offerOverviewActivity.setRefreshing(isRefresing);
    }

    public void destroy() {
        model.destroy();
    }
}
