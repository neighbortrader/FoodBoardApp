package com.github.neighbortrader.foodboardapp;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.crashlytics.android.Crashlytics;
import com.github.neighbortrader.foodboardapp.clientmodel.Grocery;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.requests.GroceryCategoryHandler;
import com.github.neighbortrader.foodboardapp.requests.OfferHandler;
import com.github.neighbortrader.foodboardapp.requests.OnEventListener;
import com.github.neighbortrader.foodboardapp.requests.RequestTyps;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    public static String TAG = MainActivity.class.getSimpleName();

    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fabGetOffers = findViewById(R.id.fab_GET_ALL_OFFERS);
        FloatingActionButton fabCreateOffer = findViewById(R.id.fa_CREATE_NEW_OFFERS);
        FloatingActionButton fabGetCatagories = findViewById(R.id.fab_GET_ALL_CATS);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        TextView textView = findViewById(R.id.textView2);
        textView.setKeyListener(null);

        fabGetOffers.setOnClickListener(view -> {
            Snackbar.make(view, "Get all Offers request", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();

            OfferHandler.builder(RequestTyps.GET_ALL_OFFERS, getApplicationContext(), new OnEventListener<Offer>() {
                @Override
                public void onResponse(List<Offer> receivedOffers) {
                    StringBuffer editTextWithAllReceivedOffers = new StringBuffer();

                    for (Offer offer : receivedOffers) {
                        editTextWithAllReceivedOffers.append(offer.getDescription()).append("\n").append(offer.getPrice().getFormattedPrice());
                    }

                    textView.setText(editTextWithAllReceivedOffers.toString());
                }

                @Override
                public void onFailure(Exception e) {
                    textView.setText(e.getMessage());
                }

                @Override
                public void onProgress(String progressUpdate) {

                }
            }).execute();
        });

        fabCreateOffer.setOnClickListener(view -> {
            Snackbar.make(view, "Create new Offer (with dummy data) Request", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();

            Offer offerToPost =  null;

            OfferHandler.builder(RequestTyps.CREATE_NEW_OFFER, getApplicationContext(), new OnEventListener<Offer>() {
                @Override
                public void onResponse(List<Offer> receivedOffers) {

                }

                @Override
                public void onFailure(Exception e) {
                    textView.setText(e.getMessage());
                }

                @Override
                public void onProgress(String progressUpdate) {

                }
            }, offerToPost).execute();
        });

        fabGetCatagories.setOnClickListener(view -> {
            Snackbar.make(view, "Get all categories request", Snackbar.LENGTH_SHORT)
                    .setAction("Action", null).show();

            StringBuffer editTextWithAllReceivedOffers = new StringBuffer();

            GroceryCategoryHandler.builder(RequestTyps.GET_ALL_CATEGORIES, getApplicationContext(), new OnEventListener<Grocery>() {

                @Override
                public void onResponse(List<Grocery> responseList) {
                    for (Grocery grocery: responseList) {
                        editTextWithAllReceivedOffers.append(grocery.getGroceryName() + "\n");
                    }

                    textView.setText(editTextWithAllReceivedOffers.toString());
                }


                @Override
                public void onFailure(Exception e) {
                    editTextWithAllReceivedOffers.append(e.getMessage());
                    textView.setText(editTextWithAllReceivedOffers.toString());
                }

                @Override
                public void onProgress(String progressUpdate) {
                    editTextWithAllReceivedOffers.append(progressUpdate + "\n");
                    textView.setText(editTextWithAllReceivedOffers.toString());
                }

            }).execute();
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
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
    }
}
