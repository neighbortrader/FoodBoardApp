package com.github.neighbortrader.foodboardapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.crashlytics.android.Crashlytics;
import com.github.neighbortrader.foodboardapp.clientmodel.Price;
import com.github.neighbortrader.foodboardapp.clienttransfermodel.Offer;
import com.github.neighbortrader.foodboardapp.transfer.Constant;
import com.github.neighbortrader.foodboardapp.transfer.GetAllOffersRequest;
import com.github.neighbortrader.foodboardapp.transfer.OnEventListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    public static String TAG = MainActivity.class.getSimpleName();

    private AppBarConfiguration mAppBarConfiguration;

    BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Constant.ACTION_ALL_OFFERS)){
                Toast.makeText(getApplicationContext(),"Recived from server: " + intent.getStringExtra(Constant.EXTRA_ALL_OFFERS),Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "GetAllOffersRequest", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                new GetAllOffersRequest<Offer>(getApplicationContext(), new OnEventListener<Offer>() {
                    @Override
                    public void onResponse(List<Offer> object) {
                        Snackbar.make(view, "onResponse\n" + object, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Snackbar.make(view, "onFailure\n" + e, Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                }).execute();
            }
        });
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

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constant.ACTION_ALL_OFFERS);
        registerReceiver(broadcastReceiver, intentFilter);
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

        unregisterReceiver(broadcastReceiver);
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
