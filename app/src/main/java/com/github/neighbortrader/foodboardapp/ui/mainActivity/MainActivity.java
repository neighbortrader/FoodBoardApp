package com.github.neighbortrader.foodboardapp.ui.mainActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.preference.PreferenceManager;

import com.crashlytics.android.Crashlytics;
import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.clientmodel.User;
import com.github.neighbortrader.foodboardapp.handler.clientmodelHandler.GroceryHandler;
import com.github.neighbortrader.foodboardapp.handler.clientmodelHandler.UserHandler;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.Urls;
import com.github.neighbortrader.foodboardapp.handler.toastHandler.ToastHandler;
import com.github.neighbortrader.foodboardapp.handler.tokenHandler.TokenHandler;
import com.github.neighbortrader.foodboardapp.ui.createOffer.CreateOfferActivity;
import com.github.neighbortrader.foodboardapp.ui.offerOverview.OfferOverviewFragment;
import com.github.neighbortrader.foodboardapp.ui.settings.SettingsActivity;
import com.github.neighbortrader.foodboardapp.ui.signIn.SignInActivity;
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

    MainActivityController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        Fabric.with(this, new Crashlytics());
        Crashlytics.setString("versionName", getString(R.string.app_version));

        super.onCreate(savedInstanceState);
        setupSharedPreferences();

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        OfferOverviewFragment offerOverviewFragment = (OfferOverviewFragment) getSupportFragmentManager().findFragmentById(R.id.offerOverviewFragment);
        controller = new MainActivityController(this, offerOverviewFragment.getController());

        startup();

        createNewOfferFloatingActionButton.setOnClickListener(view -> {
            if (UserHandler.getCurrentUserInstance() != null) {
                Intent startPostOfferIntent = new Intent(MainActivity.this, CreateOfferActivity.class);
                MainActivity.this.startActivity(startPostOfferIntent);
            } else {
                ToastHandler.buildToastHandler().makeToast(getString(R.string.general_NoUserFound));
            }
        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(String.format("%s %s", getString(R.string.app_name), getString(R.string.app_version)));

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            switch (id) {
                case R.id.offerOverview:
                    onBackPressed();
                    break;

                case R.id.user:
                    if (UserHandler.getCurrentUserInstance() != null) {
                        ToastHandler.buildToastHandler().notImplementedPlaceHolder();
                    } else {
                        UserHandler.loadUserAndUserData();

                        if (UserHandler.getCurrentUserInstance() == null) {
                            Intent startSignUpIntent = new Intent(MainActivity.this, SignInActivity.class);
                            MainActivity.this.startActivity(startSignUpIntent);
                        }
                    }
                    break;

                case R.id.issueReporting:
                    Intent intent = new Intent(Intent.ACTION_SENDTO);
                    intent.setType("text/plain");
                    intent.setData(Uri.parse(getString(R.string.feedback_email)));
                    intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_email_subject));

                    startActivity(Intent.createChooser(intent, getString(R.string.sendFeedback_title)));
                    break;

                case R.id.faq:
                    ToastHandler.buildToastHandler().notImplementedPlaceHolder();

                case R.id.about:
                    ToastHandler.buildToastHandler().notImplementedPlaceHolder();
                    break;

                case R.id.settings:
                    startActivity(new Intent(this, SettingsActivity.class));
                    break;

                default:
                    ToastHandler.buildToastHandler().notImplementedPlaceHolder();
                    return true;
            }

            return true;
        });
    }

    public void startup() {
        GroceryHandler.loadGroceriesFromSharedPreferences();

        userHandlerStartup();
        UserHandler.loadUserAndUserData();

        controller.invokeGroceryUpdate();
        controller.invokeOfferUpdate();
    }

    private void userHandlerStartup() {
        UserHandler.initUserStatusListener(this::onUserStatusChanged);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        onDestroyUserHandling();

        GroceryHandler.saveCurrentGroceriesToSharedPreferences();

        androidx.preference.PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    protected void onDestroyUserHandling() {
        User currentUser = UserHandler.getCurrentUserInstance();

        if (currentUser != null) {
            if (currentUser.isStaySignedIn()) {
                TokenHandler.removeToken();
                UserHandler.saveUser(currentUser);
            } else {
                UserHandler.deleteUser();
            }
        } else {
            UserHandler.deleteUser();
        }
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

        if (sharedPreferences.getBoolean("is_started_first_time", true)) {
            Log.d(TAG, "App is started the first time");
            firstTimeMessage();
            sharedPreferences.edit().putBoolean("is_started_first_time", false).commit();
        }

        setBaseUrl(sharedPreferences.getString(getString(R.string.settings_baseUrl_key), getString(R.string.BASE_URL)));
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.settings_baseUrl_key))) {
            setBaseUrl(sharedPreferences.getString(key, getString(R.string.BASE_URL)));
        } else if (key.equals(getString(R.string.settings_staySignedIn_Key))) {
            if (UserHandler.getCurrentUserInstance() != null) {
                boolean staySignedIn = sharedPreferences.getBoolean(getString(R.string.settings_staySignedIn_Key), true);
                UserHandler.getCurrentUserInstance().setStaySignedIn(staySignedIn);
            }
        }
    }

    public void setBaseUrl(String baseUrl) {
        Urls.BASE_URL = baseUrl;
    }

    public void firstTimeMessage() {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.custom_dialog2, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(getString(R.string.postOffer_OfferingMessageTitle));
        dialogBuilder.setMessage(getString(R.string.postOffer_OfferingMessage));
        dialogBuilder.setPositiveButton(getString(R.string.postOffer_understand), (dialog, whichButton) -> {
        });
        dialogBuilder.setNegativeButton(getString(R.string.postOffer_abort), (dialog, whichButton) -> {
        });
        AlertDialog b = dialogBuilder.create();
        b.show();
    }

    private void onUserStatusChanged(User currentUser, boolean hasUser) {
        TextView userState = navigationView.getHeaderView(0).findViewById(R.id.user_state);
        TextView userName = navigationView.getHeaderView(0).findViewById(R.id.username);
        TextView userPassword = navigationView.getHeaderView(0).findViewById(R.id.password);
        TextView userEmail = navigationView.getHeaderView(0).findViewById(R.id.email);
        TextView userAddress = navigationView.getHeaderView(0).findViewById(R.id.address);

        int userNavigatorItemIndex = 1;

        MenuItem userItem = navigationView.getMenu().getItem(userNavigatorItemIndex);

        userState.setText("has User: " + hasUser);

        if (hasUser) {
            userName.setText("Username: " + currentUser.getUsername());
            userPassword.setText("Password: " + currentUser.getPassword());

            if (currentUser.getEmail() != null) {
                userEmail.setText("Email: " + currentUser.getEmail());
            } else {
                userEmail.setText("Email: not able to get");
            }

            if (currentUser.getAddress() != null) {
                userAddress.setText("Address: " + currentUser.getAddress().getFormattedSting());
            } else {
                userAddress.setText("Address: not able to get");
            }

            userItem.setTitle(getString(R.string.general_modifie_user));

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

            String key = getString(R.string.settings_staySignedIn_Key);
            boolean isStaySigendIn = currentUser.isStaySignedIn();
            sharedPreferences.edit().putBoolean(key, isStaySigendIn).commit();
        } else {
            userName.setText("Username:");
            userPassword.setText("Password");
            userEmail.setText("Email:");
            userAddress.setText("Address:");

            userItem.setTitle(getString(R.string.general_signUp_singIn_User));
        }
    }
}
