package com.github.neighbortrader.foodboardapp.ui.signIn;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.github.neighbortrader.foodboardapp.clientmodel.User;
import com.github.neighbortrader.foodboardapp.handler.clientmodelHandler.UserHandler;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.OnRequestEventListener;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.RequestTyps;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.UserRequestHandler;

public class SignInModel {

    public static String TAG = SignInModel.class.getSimpleName();
    SignInController controller;
    Context context;

    public SignInModel(SignInController controller) {
        this.controller = controller;
        context = ContextHandler.getAppContext();
    }

    public void invokeSignIn(User userToSignIn) {
        UserRequestHandler.builder(RequestTyps.GET_JWT_TOKEN, context, new OnRequestEventListener<UserRequestHandler>() {
            @Override
            public void onResponse(UserRequestHandler userRequestController) {
                UserHandler.setCurrentUserInstance(userToSignIn);
                Toast toast = Toast.makeText(context, String.format("successfully logged in and set current user instance"), Toast.LENGTH_LONG);
                toast.show();
                Log.d(TAG, "successfully logged in and set current user instance");
                controller.onSuccess();
            }

            @Override
            public void onFailure(Exception e) {
                Log.e(TAG, "Exception while trying to sign in", e);

                // TODO: add handling of all variations
            }

            @Override
            public void onProgress(String progressUpdate) {
            }
        }, userToSignIn).execute();
    }
}
