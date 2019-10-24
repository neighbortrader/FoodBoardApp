package com.github.neighbortrader.foodboardapp.ui.mainActivity;

import com.github.neighbortrader.foodboardapp.ui.offerOverview.OfferOverviewController;

public class MainActivityController {
    MainActivityModel model;
    MainActivity mainActivity;
    OfferOverviewController offerOverviewController;

    public MainActivityController(MainActivity mainActivity, OfferOverviewController offerOverviewController) {
        this.mainActivity = mainActivity;
        this.offerOverviewController = offerOverviewController;
        model = new MainActivityModel(this, offerOverviewController);
    }

    public void invokeGroceryUpdate() {
        model.invokeGroceryUpdate();
    }

    public void invokeOfferUpdate() {
        model.invokeOfferUpdate();
    }
}
