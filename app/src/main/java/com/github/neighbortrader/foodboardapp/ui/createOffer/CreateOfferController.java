package com.github.neighbortrader.foodboardapp.ui.createOffer;

import android.content.Context;

import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;

public class CreateOfferController {
    Context context;
    private CreateOfferModel model;
    private CreateOfferActivity createOfferActivity;

    public CreateOfferController(CreateOfferActivity createOfferActivity) {
        this.model = new CreateOfferModel(this);
        this.createOfferActivity = createOfferActivity;
        context = ContextHandler.getAppContext();
    }

    public void invokePostOffer(Offer offer) {
        model.postOffer(offer);
        createOfferActivity.setProgressbarState(CreateOfferActivity.progressBarStates.LOADING);
        createOfferActivity.setPostOfferButtonDisabeld();
    }

    public void onError() {
        createOfferActivity.setProgressbarState(CreateOfferActivity.progressBarStates.ERROR);
        createOfferActivity.setPostOfferButtonEnabeld();
    }

    public void invokeFinish() {
        createOfferActivity.setProgressbarState(CreateOfferActivity.progressBarStates.FINISHED);
        createOfferActivity.finish();
    }
}
