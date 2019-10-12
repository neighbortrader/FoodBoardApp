package com.github.neighbortrader.foodboardapp.ui.signIn;

import com.github.neighbortrader.foodboardapp.clientmodel.User;

public class SignInController {
    SignInActivity signInActivity;
    SignInModel model;

    public SignInController(SignInActivity signInActivity) {
        this.signInActivity = signInActivity;
        model = new SignInModel(this);

        signInActivity.setProgressbarState(SignInActivity.progressBarStates.NOT_LOADING);
    }

    public void invokeSignIn(User userToSignIn){
        model.invokeSignIn(userToSignIn);
        signInActivity.setProgressbarState(SignInActivity.progressBarStates.LOADING);
    }

    public void onSuccess(){
        signInActivity.setProgressbarState(SignInActivity.progressBarStates.FINISHED);
        signInActivity.finish();
    }
}
