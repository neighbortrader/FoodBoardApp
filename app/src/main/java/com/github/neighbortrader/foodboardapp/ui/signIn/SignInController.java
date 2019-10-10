package com.github.neighbortrader.foodboardapp.ui.signIn;

import com.github.neighbortrader.foodboardapp.ui.createOffer.CreateOfferActivity;

public class SignInController {
    SignInActivity signInActivity;
    SignInModel model;

    public SignInController(SignInActivity signInActivity) {
        this.signInActivity = signInActivity;
        model = new SignInModel(this);

        signInActivity.setProgressbarState(CreateOfferActivity.progressBarStates.NOT_LOADING);
    }
}
