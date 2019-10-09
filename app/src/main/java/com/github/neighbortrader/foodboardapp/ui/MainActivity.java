package com.github.neighbortrader.foodboardapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;

import com.crashlytics.android.Crashlytics;
import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.Urls;
import com.github.neighbortrader.foodboardapp.ui.createOffer.CreateOfferActivity;
import com.github.neighbortrader.foodboardapp.ui.settings.SettingsActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    public static String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.activity_main)
    public DrawerLayout drawerLayout;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    @BindView(R.id.navigation)
    public NavigationView navigationView;
    @BindView(R.id.createNewOfferFAB)
    FloatingActionButton createNewOfferFloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        Fabric.with(this, new Crashlytics());
        Crashlytics.setString("versionName", getString(R.string.app_version));

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        createNewOfferFloatingActionButton.setOnClickListener(view -> {
            Intent startPostOfferIntent = new Intent(MainActivity.this, CreateOfferActivity.class);
            MainActivity.this.startActivity(startPostOfferIntent);
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.app_name) + getString(R.string.app_version));

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.settings:
                    startActivity(new Intent(this, SettingsActivity.class));
                    break;

                default:
                    return true;
            }

            return true;
        });

        setupSharedPreferences();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        setBaseUrl(sharedPreferences.getString(getString(R.string.settings_baseUrl_key), getString(R.string.BASE_URL)));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.settings_baseUrl_key))) {
            setBaseUrl(sharedPreferences.getString(key, getString(R.string.BASE_URL)));
        }
    }

    public void setBaseUrl(String baseUrl) {
        Urls.BASE_URL = baseUrl;
    }
}
