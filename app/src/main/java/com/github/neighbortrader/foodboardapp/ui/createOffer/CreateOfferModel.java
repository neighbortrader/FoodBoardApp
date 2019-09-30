package com.github.neighbortrader.foodboardapp.ui.createOffer;

import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.OfferRequestHandler;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.OnRequestEventListener;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.RequestTyps;
import com.github.neighbortrader.foodboardapp.handler.toastHandler.ToastHandler;

public class CreateOfferModel {

    public CreateOfferController controller;

    public CreateOfferModel(CreateOfferController controller) {
        this.controller = controller;
    }

    public void postOffer(Offer offer) {
        OfferRequestHandler.builder(RequestTyps.POST_NEW_OFFER, ContextHandler.getAppContext(), new OnRequestEventListener() {
            @Override
            public void onResponse(Object object) {
                ToastHandler.buildToastHandler().makeToast(ContextHandler.getAppContext().getResources().getString(R.string.postOffer_SuccessfullPostedOffer));
                controller.invokeFinish();
            }

            @Override
            public void onFailure(Exception e) {
                ToastHandler.buildErrorToastHandler(e).errorToastWithCostumeMassage(ContextHandler.getAppContext().getResources().getString(R.string.postOffer_UnsuccessfullPostedOffer));
                controller.onError();
            }

            @Override
            public void onProgress(String progressUpdate) {

            }
        }, offer).execute();
    }
}
