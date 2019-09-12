package com.github.neighbortrader.foodboardapp.view;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.crashlytics.android.Crashlytics;
import com.github.neighbortrader.foodboardapp.Model.viewmodel.AllOffersModel;
import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.Model.clientmodel.User;
import com.github.neighbortrader.foodboardapp.controller.requests.GroceryRequestController;
import com.github.neighbortrader.foodboardapp.controller.requests.OnEventListener;
import com.github.neighbortrader.foodboardapp.controller.requests.RequestTyps;
import com.github.neighbortrader.foodboardapp.controller.requests.UserRequestController;
import com.github.neighbortrader.foodboardapp.controller.view.AllOffersController;
import com.google.android.material.navigation.NavigationView;

import io.fabric.sdk.android.Fabric;

public class AllOffersActivity extends AppCompatActivity {

    public static String TAG = AllOffersActivity.class.getSimpleName();

    AllOffersModel allOffersModel;
    AllOffersController allOffersController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        allOffersModel = new AllOffersModel(this);
        allOffersController = new AllOffersController(allOffersModel,this);

        Fabric.with(this, new Crashlytics());

        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        allOffersController.iniAppDataAndUser();
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

        allOffersController.destroyAllOffers();
    }

    public void updateUi(){
        Log.d(TAG, "updateUi()");

    }
}
