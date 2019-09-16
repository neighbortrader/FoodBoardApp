package com.github.neighbortrader.foodboardapp.handler.contextHandler;

import android.app.Application;
import android.content.Context;

public class ContextHandler extends Application {

    private static Context context;

    public static Context getAppContext() {
        return ContextHandler.context;
    }

    public void onCreate() {
        super.onCreate();
        ContextHandler.context = getApplicationContext();
    }
}