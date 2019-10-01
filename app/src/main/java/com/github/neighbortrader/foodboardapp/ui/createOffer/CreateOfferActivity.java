package com.github.neighbortrader.foodboardapp.ui.createOffer;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.clientmodel.Grocery;
import com.github.neighbortrader.foodboardapp.clientmodel.Offer;
import com.github.neighbortrader.foodboardapp.clientmodel.Price;
import com.github.neighbortrader.foodboardapp.handler.clientmodelHandler.GroceryHandler;
import com.github.neighbortrader.foodboardapp.handler.contextHandler.ContextHandler;
import com.github.neighbortrader.foodboardapp.handler.toastHandler.ToastHandler;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateOfferActivity extends AppCompatActivity {

    public static String TAG = CreateOfferActivity.class.getSimpleName();
    final Calendar calender = Calendar.getInstance();
    DateTimeFormatter dateTimeFormatter;

    @BindView(R.id.createOffer)
    public MaterialButton postOfferBtn;

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

    public AutoCompleteTextView catagoryChooser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        setContentView(R.layout.createoffer);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle(getString(R.string.postOffer_headline));

        dateTimeFormatter = DateTimeFormatter.ofPattern(getString(R.string.general_dateformat));

        TextInputLayout catagoryLayout = findViewById(R.id.layout_Category);

        TextInputLayout descriptionInputLayout = findViewById(R.id.layout_Description);
        description = findViewById(R.id.description_EditText);

        TextInputLayout priceInputLayout = findViewById(R.id.layout_Price);
        priceEditText = findViewById(R.id.editText_price);

        TextInputLayout expireInputLayout = findViewById(R.id.layout_ExpireDate);
        expireDate = findViewById(R.id.editText_expireDate);

        TextInputLayout purchaseInputLayout = findViewById(R.id.layout_PurchaseDate);
        purchaseDate = findViewById(R.id.editText_purchaseDate);

        controller = new CreateOfferController(this);
        setProgressbarState(progressBarStates.NOT_LOADING);

        ArrayAdapter<Grocery> adapter = new ArrayAdapter<>(
                ContextHandler.getAppContext(),
                R.layout.dropdown_menu_popup_item,
                GroceryHandler.getCurrentGroceries());

        catagoryChooser = findViewById(R.id.filled_exposed_dropdown_ctagory);
        catagoryChooser.setAdapter(adapter);

        postOfferBtn.setOnClickListener(v -> {
            Offer offer = createOfferFromUserInput();

            if (offer != null)
                controller.invokePostOffer(offer);
        });

        offerImage.setImageResource(R.drawable.food_placeholder);

        priceEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {

            } else {
                if (priceEditText.length() <= 0) {
                    priceInputLayout.setError(String.format(getString(R.string.general_canBeEmpty), getString(R.string.general_price)));
                } else {
                    priceInputLayout.setError(null);
                }
            }
        });

        description.setOnFocusChangeListener((view, hasFocus) -> {
            if (hasFocus) {

            } else {
                if (description.length() > descriptionInputLayout.getCounterMaxLength())
                    descriptionInputLayout.setError(String.format(getString(R.string.general_toLongText), descriptionInputLayout.getCounterMaxLength()));
                else if (description.length() <= 0) {
                    descriptionInputLayout.setError(String.format(getString(R.string.general_canBeEmpty), getString(R.string.general_description)));
                } else {
                    descriptionInputLayout.setError(null);
                }
            }
        });

        catagoryChooser.setDropDownWidth(600);

        expireDate.setOnClickListener(view -> {
            expireDate.requestFocus();
            createAndShowDatePickerDialog(expireDate);
        });

        expireDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                LocalDate expireDateTime = LocalDate.parse(editable,DateTimeFormatter.ofPattern(getString(R.string.general_dateformat)) );

                if (expireDateTime.isBefore(LocalDate.now())){
                    expireInputLayout.setError(getString(R.string.postOffer_DateError));
                }else{
                    expireInputLayout.setError(null);
                }
            }
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
                (view, year1, monthOfYear, dayOfMonth) -> {
                    LocalDateTime selectedLocalDateTime = LocalDateTime.of(year1,monthOfYear,dayOfMonth, 0, 0);

                    editText.setText(selectedLocalDateTime.format(DateTimeFormatter.ofPattern(getString(R.string.general_dateformat))));
                }, year, month, day);
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
        try {
            String offerDescription = description.getText().toString();
            Price price = new Price(Double.parseDouble(priceEditText.getText().toString()));
            Grocery grocery = GroceryHandler.findGrocery(catagoryChooser.getText().toString());
            LocalDateTime expireLocalDateTime = LocalDateTime.parse(expireDate.getText());
            LocalDateTime purchaseLocalDateTime = LocalDateTime.parse(purchaseDate.getText());
            return Offer.createOffer(price, grocery, offerDescription, purchaseLocalDateTime, expireLocalDateTime);
        }catch (Exception e){
            ToastHandler.buildErrorToastHandler(e).makeToast("Deppenleerzeichenmark");
        }

        return null;
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
