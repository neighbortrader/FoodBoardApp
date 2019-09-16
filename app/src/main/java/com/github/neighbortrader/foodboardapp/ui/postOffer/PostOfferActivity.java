package com.github.neighbortrader.foodboardapp.ui.postOffer;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.github.neighbortrader.foodboardapp.R;

public class PostOfferActivity extends Activity {

    public static String TAG = PostOfferActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.createoffer);
    }
}
