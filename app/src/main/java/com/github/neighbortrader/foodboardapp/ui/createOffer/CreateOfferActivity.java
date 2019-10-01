package com.github.neighbortrader.foodboardapp.ui.createOffer;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.clientmodel.Grocery;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.clientmodel.Price;
import com.github.neighbortrader.foodboardapp.handler.clientmodelHandler.GroceryHandler;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDateTime;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateOfferActivity extends AppCompatActivity {

    public static String TAG = CreateOfferActivity.class.getSimpleName();
    final Calendar calender = Calendar.getInstance();

    @BindView(R.id.createOffer)
    public MaterialButton postOfferBtn;

    @BindView(R.id.foodCategorySpinner)
    public Spinner categorySpinner;

    public TextInputEditText description;

    public TextInputEditText priceEditText;

    public TextInputEditText expireDate;

    public TextInputEditText purchaseDate;

    @BindView(R.id.progressBar)
    public ProgressBar progressBar;


    @BindView(R.id.offerImage)
    public ImageView offerImage;

    public enum progressBarStates {NOT_LOADING, LOADING, FINISHED, EROOR}

    private CreateOfferController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.createoffer);
        ButterKnife.bind(this);

        TextInputLayout descriptionInputLayout = findViewById(R.id.layout_Description);
        description = findViewById(R.id.description_EditText);

        TextInputLayout priceInputLayout = findViewById(R.id.layout_Price);
        priceEditText = new TextInputEditText(priceInputLayout.getContext());

        TextInputLayout expireInputLayout = findViewById(R.id.layout_ExpireDate);
        expireDate = findViewById(R.id.editText_expireDate);

        TextInputLayout purchaseInputLayout = findViewById(R.id.layout_PurchaseDate);
        purchaseDate = findViewById(R.id.editText_purchaseDate);

        controller = new CreateOfferController(this);
        setProgressbarState(progressBarStates.NOT_LOADING);

        ArrayAdapter<Grocery> adapter = new ArrayAdapter<Grocery>(this,
                android.R.layout.simple_spinner_item, GroceryHandler.getCurrentGroceries());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(adapter);


        postOfferBtn.setOnClickListener(v -> {
            Offer offer = createOfferFromUserInput();
            controller.invokePostOffer(offer);
        });

        offerImage.setImageResource(R.drawable.food_placeholder);

        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > descriptionInputLayout.getCounterMaxLength())
                    descriptionInputLayout.setError("Max character length is " + descriptionInputLayout.getCounterMaxLength());
                else
                    descriptionInputLayout.setError(null);
            }
        });

        expireDate.setOnClickListener(view -> {
            expireDate.requestFocus();
            createAndShowDatePickerDialog(expireDate);
        });

        purchaseDate.setOnClickListener(view -> {
            purchaseDate.requestFocus();
            createAndShowDatePickerDialog(purchaseDate);
        });

    }

    private void createAndShowDatePickerDialog(EditText editText) {
        int day = calender.get(Calendar.DAY_OF_MONTH);
        int month = calender.get(Calendar.MONTH);
        int year = calender.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(CreateOfferActivity.this,
                (view, year1, monthOfYear, dayOfMonth) -> editText.setText(dayOfMonth + "." + (monthOfYear + 1) + "." + year1), year, month, day);
        datePickerDialog.show();
    }

    public void setProgressbarState(progressBarStates state) {
        if (state == progressBarStates.LOADING) {
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);
        } else if (state == progressBarStates.NOT_LOADING) {
            progressBar.setVisibility(View.INVISIBLE);
            progressBar.setIndeterminate(false);
        } else if (state == progressBarStates.FINISHED) {
            progressBar.setIndeterminate(false);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(100, true);
        } else if (state == progressBarStates.EROOR) {
            progressBar.setVisibility(View.INVISIBLE);
            progressBar.setIndeterminate(true);
        }
    }

    public Offer createOfferFromUserInput() {
        String offerDescription = description.getText().toString();
        Price price = new Price(Double.parseDouble(priceEditText.getText().toString()));
        Grocery grocery = GroceryHandler.findGrocery(categorySpinner.getSelectedItem().toString());
        LocalDateTime expireLocalDateTime = LocalDateTime.of(calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH), 0, 0);
        LocalDateTime purchaseLocalDateTime = LocalDateTime.of(calender.get(Calendar.YEAR), calender.get(Calendar.MONTH), calender.get(Calendar.DAY_OF_MONTH), 0, 0);

        return Offer.createOffer(price, grocery, offerDescription, purchaseLocalDateTime, expireLocalDateTime);
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
