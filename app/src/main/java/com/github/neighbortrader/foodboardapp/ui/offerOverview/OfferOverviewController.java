package com.github.neighbortrader.foodboardapp.ui.offerOverview;

import android.content.Context;

import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.handler.toastHandler.ToastHandler;

import java.util.ArrayList;

public class OfferOverviewController {

    Context context;
    private OfferOverviewModel model;
    private OfferOverviewFragment offerOverviewFragment;

    public OfferOverviewController(OfferOverviewFragment offerOverviewFragment) {
        this.model = new OfferOverviewModel(this);
        this.offerOverviewFragment = offerOverviewFragment;
        context = ContextHandler.getAppContext();
    }

    public void invokeUpdate() {
        invokeGroceryUpdate();
        invokeOfferUpdate();
        setProgressBarRefreshing(true);
    }

    public void invokeOfferUpdate() {
        model.updateOffers();
        setProgressBarRefreshing(true);
    }

    public void invokeGroceryUpdate() {
        model.updateGroceryCategories();
        setProgressBarRefreshing(true);
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

    public void setProgressBarRefreshing(boolean isRefresing) {
        offerOverviewFragment.setRefreshing(isRefresing);
    }

    public void destroy() {
        model.destroy();
    }
}
