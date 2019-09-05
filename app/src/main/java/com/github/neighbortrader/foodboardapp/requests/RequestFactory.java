package com.github.neighbortrader.foodboardapp.requests;

import android.content.Context;

public class RequestFactory {
    public static AsyncRequest buildRequest(Context context, OnEventListener callback, RequestTyps model) {
        AsyncRequest asyncRequest = null;
        switch (model) {
            case GET_ALL_OFFERS:
                asyncRequest = new GetAllOffersRequest(context, callback);
                break;

            case POST_NEW_USER:
                asyncRequest = new PostNewUserRequest(context, callback);
                break;

            default:
                return null;
        }
        return asyncRequest;
    }
}
