package com.github.neighbortrader.foodboardapp.ui.offerOverview;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import androidx.appcompat.widget.Toolbar;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.crashlytics.android.Crashlytics;
import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;

import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;


import com.github.neighbortrader.foodboardapp.ui.createOffer.CreateOfferActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class OfferOverviewActivity extends AppCompatActivity {

    public static String TAG = OfferOverviewActivity.class.getSimpleName();

    private OfferOverviewController controller;

    @BindView(R.id.recyclerView)
    RecyclerView offerRecyclerView;

    @BindView(R.id.createNewOfferFAB)
    FloatingActionButton createNewOfferFloatingActionButton;
    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout pullToRefreshLayout;

    private LinearLayoutManager linearLayoutManager;
    private RecyclerViewAdapter adapter;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.settings:
                composeMessage();
                return true;
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        Fabric.with(this, new Crashlytics());

        super.onCreate(savedInstanceState);

        controller = new OfferOverviewController(this);

        setContentView(R.layout.showoffers);
        ButterKnife.bind(this);


        createNewOfferFloatingActionButton.setOnClickListener(view -> {
            Intent startPostOfferIntent = new Intent(OfferOverviewActivity.this, CreateOfferActivity.class);
            OfferOverviewActivity.this.startActivity(startPostOfferIntent);
        });

        offerRecyclerView = findViewById(R.id.recyclerView);

        linearLayoutManager = new LinearLayoutManager(ContextHandler.getAppContext());
        offerRecyclerView.setLayoutManager(linearLayoutManager);

        adapter = new RecyclerViewAdapter(controller.getCurrentOffers());
        offerRecyclerView.setAdapter(adapter);

        pullToRefreshLayout.setColorSchemeResources(R.color.color_primary, R.color.color_primary_variant, R.color.color_secondary_variant);

        pullToRefreshLayout.setOnRefreshListener(
                () -> {
                    pullToRefreshLayout.setRefreshing(true);
                    controller.invokeUpdate();
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
        adapter.clearOfferList();
        adapter.setOfferList(offerArrayList);
        adapter.notifyDataSetChanged();
    }

    public void setRefreshing(boolean value) {
        pullToRefreshLayout.setRefreshing(value);
    }
}
