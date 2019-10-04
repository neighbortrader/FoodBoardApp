package com.github.neighbortrader.foodboardapp.ui.offerDetail;

import android.content.Context;

import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;

public class OfferDetailModel {
    public String TAG = OfferDetailModel.class.getSimpleName();

    private OfferDetailController offerDetailController;
    private Context context = ContextHandler.getAppContext();

    public OfferDetailModel(OfferDetailController offerDetailController) {
        this.offerDetailController = offerDetailController;
    }
}
