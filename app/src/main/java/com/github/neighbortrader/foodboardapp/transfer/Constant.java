package com.github.neighbortrader.foodboardapp.transfer;

import android.app.Application;
import android.content.Context;

import com.github.neighbortrader.foodboardapp.MainActivity;
import com.github.neighbortrader.foodboardapp.MyApplication;
import com.github.neighbortrader.foodboardapp.R;

public class Constant extends Application {
    private static Context context = MyApplication.getAppContext();

    public final static String BASE_URL = context.getResources().getString(R.string.BASE_URL);
    public final static String ENDPOINT_GET_ALL_OFFERS_REQUEST = context.getResources().getString(R.string.GET_ALL_OFFERS_URL);
}
