package com.github.neighbortrader.foodboardapp.ui.offerOverview;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.crashlytics.android.Crashlytics;
import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.handler.requestsHandler.Urls;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
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
    @BindView(R.id.pullToRefresh)
    SwipeRefreshLayout pullToRefreshLayout;
    private ArrayAdapter<String> listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        Fabric.with(this, new Crashlytics());

        super.onCreate(savedInstanceState);

        controller = new AllOffersController(this);

        setContentView(R.layout.showoffers);
        ButterKnife.bind(this);

        listAdapter = new ArrayAdapter<>(this, R.layout.simpelrow);
        offersListView.setAdapter(listAdapter);

        ImageView imageView = findViewById(R.id.logoImageView);

        imageView.setOnClickListener(view -> {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            final View dialogView = inflater.inflate(R.layout.custom_dialog, null);
            dialogBuilder.setView(dialogView);

            final EditText edt = dialogView.findViewById(R.id.edit1);

            edt.setOnClickListener(view1 -> {
                edt.setText("http://");
                edt.setSelection(7);
            });

            dialogBuilder.setTitle("IP-Adresse");
            dialogBuilder.setMessage("IP-Adresse und Port des Servers eingeben");
            dialogBuilder.setPositiveButton("Okay", (dialog, whichButton) -> {
                Urls.BASE_URL = edt.getText().toString() +"/api/";
            });
            dialogBuilder.setNegativeButton("Abbrechen", (dialog, whichButton) -> {

            });
            AlertDialog b = dialogBuilder.create();
            b.show();
        });

        pullToRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);

        pullToRefreshLayout.setOnRefreshListener(
                () -> {
                    controller.invokeGroceryUpdate();
                    controller.invokeOfferUpdate();
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
            String offerString = String.format("%s (%s)\n\n%s\n\n%s",
                    offer.getGroceryCategory().getGroceryName(),
                    offer.getCreationDate()
                            .format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.MEDIUM,
                                    FormatStyle.SHORT)),
                    offer.getDescription(),
                    offer.getPrice().getFormattedPrice());
            listAdapter.add(offerString);
        }
        listAdapter.notifyDataSetChanged();
    }

    public void setRefreshing(boolean value) {
        pullToRefreshLayout.setRefreshing(value);
    }
}
