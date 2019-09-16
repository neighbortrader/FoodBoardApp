package com.github.neighbortrader.foodboardapp.ui.postOffer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.crashlytics.android.Crashlytics;
import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.ui.offerOverview.AllOffersController;

import io.fabric.sdk.android.Fabric;

public class PostOfferActivity extends Activity {

    public static String TAG = PostOfferActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.generateoffer);
    }
}
