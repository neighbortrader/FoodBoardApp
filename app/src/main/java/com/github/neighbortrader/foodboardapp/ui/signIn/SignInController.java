package com.github.neighbortrader.foodboardapp.ui.signIn;

import com.github.neighbortrader.foodboardapp.clientmodel.User;
import com.github.neighbortrader.foodboardapp.handler.toastHandler.ToastHandler;

public class SignInController {
    SignInActivity signInActivity;
    SignInModel model;

    public SignInController(SignInActivity signInActivity) {
        this.signInActivity = signInActivity;
        model = new SignInModel(this);

        signInActivity.setProgressbarState(SignInActivity.progressBarStates.NOT_LOADING);
    }

    public void invokeSignIn(User userToSignIn) {
        model.invokeSignIn(userToSignIn);
        signInActivity.setProgressbarState(SignInActivity.progressBarStates.LOADING);
    }

    public void onSuccess() {
        signInActivity.setProgressbarState(SignInActivity.progressBarStates.FINISHED);
        signInActivity.finish();
    }

    public void onError(){
        signInActivity.setProgressbarState(SignInActivity.progressBarStates.ERROR);
        ToastHandler.buildToastHandler().makeToast("Error while trying to sign in");
    }
}
