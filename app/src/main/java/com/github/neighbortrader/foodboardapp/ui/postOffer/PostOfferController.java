package com.github.neighbortrader.foodboardapp.ui.postOffer;

import android.content.Context;

import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;

public class PostOfferController {
    private PostOfferModel model;
    private PostOfferActivity postOfferActivity;
    Context context;

    public PostOfferController(PostOfferActivity postOfferActivity) {
        this.model = new PostOfferModel(this);
        this.postOfferActivity = postOfferActivity;
        context = ContextHandler.getAppContext();
    }

    public void invokePostOffer(Offer offer) {
        model.postOffer(offer);
        postOfferActivity.setProgressbarState(PostOfferActivity.progressBarStates.LOADING);
    }

    public void onError(){
        postOfferActivity.setProgressbarState(PostOfferActivity.progressBarStates.EROOR);
    }

    public void invokeFinish() {
        postOfferActivity.setProgressbarState(PostOfferActivity.progressBarStates.FINISHED);
        postOfferActivity.finish();
    }


}
