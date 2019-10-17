package com.github.neighbortrader.foodboardapp.ui.mainActivity;

import android.content.Context;

import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.ui.offerOverview.OfferOverviewController;

public class MainActivityModel {
    private MainActivityController controller;
    private Context context;

    private OfferOverviewController offerOverviewController;

    public static String TAG = MainActivityModel.class.getSimpleName();

    public MainActivityModel(MainActivityController controller, OfferOverviewController offerOverviewController) {
        this.controller = controller;
        this.offerOverviewController = offerOverviewController;
        context = ContextHandler.getAppContext();
    }
    
    public void invokeOfferUpdate() {
        offerOverviewController.invokeOfferUpdate();
    }

    public void invokeGroceryUpdate() {
        offerOverviewController.invokeGroceryUpdate();
    }
}
