package com.github.neighbortrader.foodboardapp.ui.postOffer;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.clientmodel.Grocery;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.clientmodel.Price;

import java.time.LocalDateTime;
import java.util.Calendar;

public class PostOfferActivity extends Activity {

    public static String TAG = PostOfferActivity.class.getSimpleName();
    final Calendar calender = Calendar.getInstance();
    public Button postOfferBtn;
    public Spinner categorySpinner;
    public EditText description;
    public EditText priceEditText;
    public EditText expireDate;
    PostOfferController controller;
    DatePickerDialog picker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        controller = new PostOfferController(this);

        setContentView(R.layout.createoffer);

        postOfferBtn = findViewById(R.id.createOffer);
        categorySpinner = findViewById(R.id.foodCategorySpinner);
        description = findViewById(R.id.editTextBeschreibung);
        priceEditText = findViewById(R.id.editTextPreis);
        expireDate = findViewById(R.id.editTextmhd);

        ArrayAdapter<Grocery> adapter = new ArrayAdapter<Grocery>(this,
                android.R.layout.simple_spinner_item, Grocery.getCurrentGroceries());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(adapter);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(adapter);

        postOfferBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Offer offer = createOfferFromView(v);
                controller.invokePostOffer(offer);
            }
        });

        expireDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day = calender.get(Calendar.DAY_OF_MONTH);
                int month = calender.get(Calendar.MONTH);
                int year = calender.get(Calendar.YEAR);

                picker = new DatePickerDialog(PostOfferActivity.this,
                        (view, year1, monthOfYear, dayOfMonth) -> expireDate.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year1), year, month, day);
                picker.show();
            }
        });
    }

    public Offer createOfferFromView(View v) {
        String offerDescription = description.getText().toString();
        Price price = new Price(Double.parseDouble(priceEditText.getText().toString()));
        Grocery grocery = Grocery.findGrocery(categorySpinner.getSelectedItem().toString());
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
