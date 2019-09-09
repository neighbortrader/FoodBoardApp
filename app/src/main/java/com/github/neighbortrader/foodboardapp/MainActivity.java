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

            StringBuffer editTextWithAllReceivedOffers = new StringBuffer();

            OfferHandler.builder(RequestTyps.GET_ALL_OFFERS, getApplicationContext(), new OnEventListener<Offer>() {
                @Override
                public void onResponse(List<Offer> receivedOffers) {
                    for (Offer offer : receivedOffers) {
                        editTextWithAllReceivedOffers.append("Beschreibung: " + offer.getDescription()).append("\n")
                                .append("Preis: " + offer.getPrice().getFormattedPrice())
                                .append("\nKategorie: " + offer.getGroceryCategory().getGroceryName())
                                .append("\nAblaufdatum: " + offer.getExpireDate())
                                .append("\nKaufdatum: " + offer.getPurchaseDate());
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

        GroceryCategoryHandler.builder(RequestTyps.GET_ALL_CATEGORIES, getApplicationContext(), new OnEventListener<Void>() {
            @Override
            public void onResponse(List<Void> receivedOffers) {
            }

            @Override
            public void onFailure(Exception e) {
            }

            @Override
            public void onProgress(String progressUpdate) {
            }
        }).execute();
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
