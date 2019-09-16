package com.github.neighbortrader.foodboardapp.ui.offerOverview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.crashlytics.android.Crashlytics;
import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.ui.postOffer.PostOfferActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import io.fabric.sdk.android.Fabric;

public class AllOffersActivity extends AppCompatActivity {

    public static String TAG = AllOffersActivity.class.getSimpleName();

    AllOffersController controller;

    @BindView(R.id.mapButton)
    Button mapButton;
    @BindView(R.id.filterButton)
    Button filterButton;
    @BindView(R.id.offersListView)
    ListView offersListView;
    @BindView(R.id.createNewOfferFAB)
    FloatingActionButton createNewOfferFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        Fabric.with(this, new Crashlytics());

        super.onCreate(savedInstanceState);

        controller = new AllOffersController(this);

        setContentView(R.layout.showoffers);

        controller.invokeOfferUpdate();

        createNewOfferFloatingActionButton = findViewById(R.id.createNewOfferFAB);
        createNewOfferFloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent startPostOfferIntent = new Intent(AllOffersActivity.this, PostOfferActivity.class);
                AllOffersActivity.this.startActivity(startPostOfferIntent);
            }
        });
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

        controller.destroy();
    }

    public void updateUi(ArrayList<Offer> offerArrayList) {
        Log.d(TAG, "updateUi()");

        for (Offer offer : offerArrayList) {
            offersListView.add
        }
    }

}
