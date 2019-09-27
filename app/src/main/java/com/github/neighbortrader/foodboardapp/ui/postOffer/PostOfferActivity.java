package com.github.neighbortrader.foodboardapp.ui.postOffer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.clientmodel.Grocery;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.clientmodel.Price;
import com.github.neighbortrader.foodboardapp.handler.clientmodelHandler.GroceryHandler;

import java.time.LocalDateTime;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostOfferActivity extends Activity {

    public static String TAG = PostOfferActivity.class.getSimpleName();
    final Calendar calender = Calendar.getInstance();

    @BindView(R.id.createOffer)
    public Button postOfferBtn;

    @BindView(R.id.foodCategorySpinner)
    public Spinner categorySpinner;

    @BindView(R.id.createOffer_EdTxt_Description)
    public EditText description;

    @BindView(R.id.editTextPreis)
    public EditText priceEditText;

    @BindView(R.id.editTextmhd)
    public EditText expireDate;

    @BindView(R.id.progressBar)
    public ProgressBar progressBar;

    private PostOfferController controller;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.createoffer);
        ButterKnife.bind(this);

        controller = new PostOfferController(this);
        startStopProgressBar(false);

        ArrayAdapter<Grocery> adapter = new ArrayAdapter<Grocery>(this,
                android.R.layout.simple_spinner_item, GroceryHandler.getCurrentGroceries());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(adapter);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(adapter);

        postOfferBtn.setOnClickListener(v -> {
            Offer offer = createOfferFromUserInput();
            controller.invokePostOffer(offer);
        });

        expireDate.setOnClickListener(v -> {
            int day = calender.get(Calendar.DAY_OF_MONTH);
            int month = calender.get(Calendar.MONTH);
            int year = calender.get(Calendar.YEAR);

            DatePickerDialog picker = new DatePickerDialog(PostOfferActivity.this,
                    (view, year1, monthOfYear, dayOfMonth) -> expireDate.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year1), year, month, day);
            picker.show();
        });
    }

    public void startStopProgressBar(boolean value) {
        if (value) {
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setIndeterminate(true);
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            progressBar.setIndeterminate(false);
        }
    }

    public void finishProgressBar() {
        progressBar.setIndeterminate(false);
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(100, true);
    }

    public Offer createOfferFromUserInput() {
        String offerDescription = description.getText().toString();
        Price price = new Price(Double.parseDouble(priceEditText.getText().toString()));
        Grocery grocery = GroceryHandler.findGrocery(categorySpinner.getSelectedItem().toString());
        LocalDateTime expireLocalDateTime = LocalDateTime.of(calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH), 0, 0);

        return Offer.createOffer(price, grocery, offerDescription, expireLocalDateTime, expireLocalDateTime);
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
