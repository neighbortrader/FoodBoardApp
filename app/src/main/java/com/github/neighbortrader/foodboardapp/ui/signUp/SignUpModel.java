package com.github.neighbortrader.foodboardapp.ui.signUp;

import android.content.Context;

import com.github.neighbortrader.foodboardapp.clientmodel.User;
import com.github.neighbortrader.foodboardapp.handler.clientmodelHandler.UserHandler;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.OnRequestEventListener;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.RequestTyps;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.UserRequestHandler;

public class SignUpModel {
    SignUpController controller;
    Context context;

    public SignUpModel(SignUpController controller) {
        this.controller = controller;
        context = ContextHandler.getAppContext();
    }

    public void invokeSignUp(User userToSignUp) {
        UserRequestHandler.builder(RequestTyps.POST_NEW_USER, context, new OnRequestEventListener() {
            @Override
            public void onResponse(Object object) {
                UserHandler.setCurrentUserInstance(userToSignUp);
                controller.onSuccess();
            }

            @Override
            public void onFailure(Exception e) {
                controller.onError();
            }

            @Override
            public void onProgress(String progressUpdate) {

            }
        }, userToSignUp).execute();
    }
}
