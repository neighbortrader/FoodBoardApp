package com.github.neighbortrader.foodboardapp.ui.offerDetail;

import android.content.Context;

import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.ui.offerOverview.OfferOverviewFragment;

public class OfferDetailController {
    private OfferDetailModel model;
    private OfferDetailFragment offerDetailFragment;
    Context context;


    public OfferDetailController(OfferDetailFragment offerDetailFragment) {
        this.model = new OfferDetailModel(this);
        this.offerDetailFragment = offerDetailFragment;
        this.context = ContextHandler.getAppContext();
    }
}
