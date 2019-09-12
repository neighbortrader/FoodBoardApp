package com.github.neighbortrader.foodboardapp.handler.requests;

import android.app.Application;
import android.content.Context;

import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.R;

public class Urls extends Application {
    private static Context context = ContextHandler.getAppContext();

    public final static String BASE_URL = context.getResources().getString(R.string.BASE_URL);
    public final static String ENDPOINT_GET_ALL_OFFERS = context.getResources().getString(R.string.GET_ALL_OFFERS_URL);
    public final static String ENDPOINT_CREATE_NEW_USER = context.getResources().getString(R.string.CREATE_NEW_USER);
    public final static String ENDPOINT_CREATE_NEW_OFFER = context.getResources().getString(R.string.CREATE_NEW_OFFER);
    public final static String ENDPOINT_GET_ALL_CATEGORIES = context.getResources().getString(R.string.GET_ALL_CATEGORIES);
    public final static String ENDPOINT_GET_JWT_TOKEN = context.getResources().getString(R.string.GET_JWT_TOKEN);
}
