package com.github.neighbortrader.foodboardapp.ui.createOffer;

import android.content.Context;

import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;

public class CreateOfferController {
    private CreateOfferModel model;
    private CreateOfferActivity createOfferActivity;
    Context context;

    public CreateOfferController(CreateOfferActivity createOfferActivity) {
        this.model = new CreateOfferModel(this);
        this.createOfferActivity = createOfferActivity;
        context = ContextHandler.getAppContext();
    }

    public void invokePostOffer(Offer offer) {
        model.postOffer(offer);
        createOfferActivity.setProgressbarState(CreateOfferActivity.progressBarStates.LOADING);
    }

    public void onError(){
        createOfferActivity.setProgressbarState(CreateOfferActivity.progressBarStates.EROOR);
    }

    public void invokeFinish() {
        createOfferActivity.setProgressbarState(CreateOfferActivity.progressBarStates.FINISHED);
        createOfferActivity.finish();
    }


}
