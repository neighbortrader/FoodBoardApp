package com.github.neighbortrader.foodboardapp.ui.view;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.ui.model.AllOffersModel;
import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.ui.controller.AllOffersController;

import io.fabric.sdk.android.Fabric;

public class AllOffersActivity extends AppCompatActivity {

    public static String TAG = AllOffersActivity.class.getSimpleName();

    AllOffersController allOffersController;
    AllOffersModel allOffersModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        Fabric.with(this, new Crashlytics());

        super.onCreate(savedInstanceState);

        allOffersController = new AllOffersController(this);
        allOffersModel = allOffersController.getAllOffersModel();

        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop()");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy()");

        allOffersController.destroy();
    }

    public void updateUi(){
        Log.d(TAG, "updateUi()");

        for (Offer offer : allOffersModel.getCurrentOffers()){

        }
    }
}
