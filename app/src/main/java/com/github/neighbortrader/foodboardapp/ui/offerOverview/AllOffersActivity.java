package com.github.neighbortrader.foodboardapp.ui.offerOverview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.crashlytics.android.Crashlytics;
import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.ui.createOffer.CreateOfferActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class AllOffersActivity extends AppCompatActivity {

    public static String TAG = AllOffersActivity.class.getSimpleName();

    private AllOffersController controller;

    @BindView(R.id.offersListView)
    ListView offersListView;
    @BindView(R.id.createNewOfferFAB)
    FloatingActionButton createNewOfferFloatingActionButton;
    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout pullToRefreshLayout;
    private ArrayAdapter<String> listAdapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        Fabric.with(this, new Crashlytics());

        super.onCreate(savedInstanceState);

        controller = new AllOffersController(this);

        setContentView(R.layout.showoffers);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        createNewOfferFloatingActionButton.setOnClickListener(view -> {
            Intent startPostOfferIntent = new Intent(AllOffersActivity.this, CreateOfferActivity.class);
            AllOffersActivity.this.startActivity(startPostOfferIntent);
        });

        listAdapter = new ArrayAdapter<>(this, R.layout.simpelrow);
        offersListView.setAdapter(listAdapter);

        pullToRefreshLayout.setOnRefreshListener(
                () -> {
                    controller.invokeUpdate();
                    setRefreshing(true);
                }
        );
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

        Collections.sort(offerArrayList, new Comparator<Offer>() {
            @Override
            public int compare(Offer offer, Offer t1) {
                return offer.getCreationDate().compareTo(t1.getCreationDate()) * -1;
            }
        });

        listAdapter.clear();

        for (Offer offer : offerArrayList) {
            StringBuffer offerAsDisplayString = new StringBuffer();

            int endIndex = 16;

            if (offer.getDescription().length() < 16)
                endIndex = offer.getDescription().length();

            offerAsDisplayString.append(offer.getDescription().substring(0, endIndex))
                    .append(" " + offer.getPrice().getFormattedPrice())
                    .append(" " + offer.getGroceryCategory().getGroceryName());

            listAdapter.add(offerAsDisplayString.toString());
        }
        listAdapter.notifyDataSetChanged();
    }

    public void setRefreshing(boolean value) {
        pullToRefreshLayout.setRefreshing(value);
    }
}
