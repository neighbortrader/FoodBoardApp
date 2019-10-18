package com.github.neighbortrader.foodboardapp.ui.signUp;

import com.github.neighbortrader.foodboardapp.clientmodel.User;
import com.github.neighbortrader.foodboardapp.handler.toastHandler.ToastHandler;
import com.github.neighbortrader.foodboardapp.ui.createOffer.CreateOfferActivity;

public class SignUpController {
    private SignUpModel model;
    private SignUpActivity signUpActivity;

    public SignUpController(SignUpActivity signUpActivity) {
        this.signUpActivity = signUpActivity;
        model = new SignUpModel(this);
        signUpActivity.setProgressbarState(CreateOfferActivity.progressBarStates.NOT_LOADING);
    }

    public void invokeSignUp(User userToSignUp) {
        model.invokeSignUp(userToSignUp);
        signUpActivity.setProgressbarState(CreateOfferActivity.progressBarStates.LOADING);
    }

    public void onSuccess() {
        signUpActivity.setProgressbarState(CreateOfferActivity.progressBarStates.FINISHED);
        signUpActivity.finish();
    }

    public void onError() {
        signUpActivity.setProgressbarState(CreateOfferActivity.progressBarStates.ERROR);
        ToastHandler.buildToastHandler().makeToast("Error while trying to create user");
    }
}
