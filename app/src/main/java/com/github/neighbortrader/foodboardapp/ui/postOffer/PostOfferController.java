package com.github.neighbortrader.foodboardapp.ui.postOffer;

import android.content.Context;

import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;

public class PostOfferController {
    PostOfferModel model;
    PostOfferActivity postOfferActivity;
    Context context;

    public PostOfferController(PostOfferActivity postOfferActivity) {
        this.model = new PostOfferModel(this);
        this.postOfferActivity = postOfferActivity;
        context = ContextHandler.getAppContext();
    }

    public void invokePostOffer(Offer offer) {
        model.postOffer(offer);
    }

    public void invokeFinish() {
        postOfferActivity.finish();
    }
}
