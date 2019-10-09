package com.github.neighbortrader.foodboardapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.crashlytics.android.Crashlytics;
import com.github.neighbortrader.foodboardapp.BuildConfig;
import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.Urls;
import com.github.neighbortrader.foodboardapp.ui.createOffer.CreateOfferActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

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

                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                    LayoutInflater inflater = this.getLayoutInflater();
                    final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
                    dialogBuilder.setView(dialogView);

                    final EditText edt = dialogView.findViewById(R.id.edit1);

                    edt.setOnClickListener(view1 -> {
                        edt.setText(Urls.BASE_URL);
                        edt.setSelection(7);
                    });

                    dialogBuilder.setTitle("IP-Adresse");
                    dialogBuilder.setMessage("IP-Adresse und Port des Servers eingeben");
                    dialogBuilder.setPositiveButton("Okay", (dialog, whichButton) -> {
                        Urls.BASE_URL = edt.getText().toString();
                    });
                    dialogBuilder.setNegativeButton("Abbrechen", (dialog, whichButton) -> {

                    });
                    AlertDialog b = dialogBuilder.create();
                    b.show();
                    break;

                default:
                    return true;
            }

            return true;
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}
