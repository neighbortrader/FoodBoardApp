package com.github.neighbortrader.foodboardapp.ui.createOffer;

import android.app.ActionBar;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CreateOfferActivity extends AppCompatActivity {

    public static String TAG = CreateOfferActivity.class.getSimpleName();

    final Calendar calender = Calendar.getInstance();
    DateTimeFormatter dateTimeFormatter;

    public enum progressBarStates {NOT_LOADING, LOADING, FINISHED, ERROR}

    @BindView(R.id.progressBar)
    public ProgressBar progressBar;
    @BindView(R.id.offerImage)
    public ImageView offerImage;
    @BindView(R.id.createOffer)
    public MaterialButton postOfferButton;
    public TextInputEditText descriptionEditText;
    public TextInputLayout descriptionInputLayout;
    public TextInputEditText priceEditText;
    public TextInputLayout priceInputLayout;
    public AutoCompleteTextView categoryChooser;
    public TextInputLayout categoryLayout;
    public TextInputEditText expireDateEditText;
    public TextInputLayout expireDateInputLayout;
    public TextInputEditText purchaseDateEditText;
    public TextInputLayout purchaseDateInputLayout;

    private CreateOfferController controller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");

        super.onCreate(savedInstanceState);

        controller = new CreateOfferController(this);

        setContentView(R.layout.createoffer);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle(getString(R.string.postOffer_headline));

        dateTimeFormatter = DateTimeFormatter.ofPattern(getString(R.string.general_dateformat));

        findViews();

        categoryChooser.setHeight(purchaseDateEditText.getHeight());

        setProgressbarState(progressBarStates.NOT_LOADING);

        ArrayAdapter<Grocery> adapter = new ArrayAdapter<>(
                ContextHandler.getAppContext(),
                R.layout.dropdown_menu_popup_item,
                GroceryHandler.getCurrentGroceries());

        categoryChooser.setAdapter(adapter);
        categoryChooser.setDropDownWidth(600);

        offerImage.setImageResource(R.drawable.food_placeholder);

        setListeners();
    }

    public void findViews() {
        descriptionInputLayout = findViewById(R.id.layout_Description);
        descriptionEditText = findViewById(R.id.description_EditText);

        priceInputLayout = findViewById(R.id.layout_Price);
        priceEditText = findViewById(R.id.editText_price);

        categoryChooser = findViewById(R.id.filled_exposed_dropdown_ctagory);
        categoryLayout = findViewById(R.id.layout_Category);

        expireDateInputLayout = findViewById(R.id.layout_ExpireDate);
        expireDateEditText = findViewById(R.id.editText_expireDate);

        purchaseDateInputLayout = findViewById(R.id.layout_PurchaseDate);
        purchaseDateEditText = findViewById(R.id.editText_purchaseDate);
    }

    public static int dpToPx(int dp) {
        float density = ContextHandler.getAppContext().getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }

    public void setListeners() {
        descriptionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkDescriptionInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        descriptionEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                checkDescriptionInput();
            }
        });

        priceEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkPriceInput();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        priceEditText.setOnFocusChangeListener((view, hasFocus) -> {
            if (!hasFocus) {
                checkPriceInput();
            }
        });

        categoryChooser.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                checkCategoryInput();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                checkCategoryInput();
            }
        });

        categoryChooser.setOnDismissListener(() -> {
            checkCategoryInput();
        });

        expireDateEditText.setOnClickListener(view -> {
            expireDateEditText.requestFocus();
            createAndShowDatePickerDialog(expireDateEditText);
        });

        expireDateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkExpireDate(editable);
            }
        });

        purchaseDateEditText.setOnClickListener(view -> {
            purchaseDateEditText.requestFocus();
            createAndShowDatePickerDialog(purchaseDateEditText);
        });

        purchaseDateEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                checkPurchaseDate(editable);
            }
        });

        postOfferButton.setOnClickListener(v -> {
            Offer offer = createOfferFromUserInput();

            if (offer != null) {
                controller.invokePostOffer(offer);
            }
        });
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

    private void createAndShowDatePickerDialog(EditText editText) {
        int day = calender.get(Calendar.DAY_OF_MONTH);
        int month = calender.get(Calendar.MONTH);
        int year = calender.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(CreateOfferActivity.this,
                (view, year1, monthOfYear, dayOfMonth) -> {
                    monthOfYear += 1;
                    LocalDateTime selectedLocalDateTime = LocalDateTime.of(year1, monthOfYear, dayOfMonth, 0, 0);

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
            progressBar.setVisibility(View.GONE);
        } else if (state == progressBarStates.FINISHED) {
            progressBar.setIndeterminate(false);
            progressBar.setVisibility(View.VISIBLE);
            progressBar.setProgress(100, true);
        } else if (state == progressBarStates.ERROR) {
            progressBar.setVisibility(View.INVISIBLE);
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.GONE);
        }
    }

    public boolean checkAll() {
        checkDescriptionInput();
        checkPriceInput();
        checkCategoryInput();
        checkExpireDate(expireDateEditText.getEditableText());
        checkPurchaseDate(purchaseDateEditText.getEditableText());

        return checkDescriptionInput() && checkPriceInput() && checkCategoryInput() && checkPurchaseDate(purchaseDateEditText.getEditableText()) && checkExpireDate(expireDateEditText.getEditableText());
    }

    public boolean checkDescriptionInput() {
        if (descriptionEditText.length() > descriptionInputLayout.getCounterMaxLength())
            descriptionInputLayout.setError(String.format(getString(R.string.general_toLongText), descriptionInputLayout.getCounterMaxLength()));
        else if (descriptionEditText.length() <= 0) {
            descriptionInputLayout.setError(String.format(getString(R.string.general_canBeEmpty), getString(R.string.general_description)));
        } else {
            descriptionInputLayout.setError(null);
            return true;
        }

        return false;
    }

    public boolean checkCategoryInput() {
        if(!categoryChooser.hasSelection()){
            categoryLayout.setError(getString(R.string.postOffer_categoryIsNeeded));
            return false;
        }else{
            categoryLayout.setError(null);
        }

        return true;
    }

    public boolean checkPriceInput() {
        if (priceEditText.length() <= 0) {
            priceInputLayout.setError(String.format(getString(R.string.general_canBeEmpty), getString(R.string.general_price)));
        } else {
            priceInputLayout.setError(null);
            return true;
        }

        return false;
    }

    public boolean checkPurchaseDate(Editable editable) {
        if (editable.length() > 0) {
            LocalDate purchaseDateTime = LocalDate.parse(editable, DateTimeFormatter.ofPattern(getString(R.string.general_dateformat)));
            if (purchaseDateTime.isAfter(LocalDate.now())) {
                purchaseDateInputLayout.setError(getString(R.string.postOffer_DateError));
            } else {
                purchaseDateInputLayout.setError(null);
                return true;
            }
        } else {
            purchaseDateInputLayout.setError(String.format(getString(R.string.general_canBeEmpty), getString(R.string.postOffer_PurchaseDateHint)));
        }

        return false;
    }

    public boolean checkExpireDate(Editable editable) {
        if (editable.length() > 0) {
            LocalDate expireDateTime = LocalDate.parse(editable, DateTimeFormatter.ofPattern(getString(R.string.general_dateformat)));

            if (expireDateTime.isBefore(LocalDate.now())) {
                expireDateInputLayout.setError(getString(R.string.postOffer_DateError));
            } else {
                expireDateInputLayout.setError(null);
                return true;
            }
        } else {
            expireDateInputLayout.setError(String.format(getString(R.string.general_canBeEmpty), getString(R.string.postOffer_ExpireDateHint)));
        }

        return false;
    }

    public Offer createOfferFromUserInput() {
        if (checkAll()) {
            String offerDescription = descriptionEditText.getText().toString();
            Price price = new Price(Double.parseDouble(priceEditText.getText().toString()));
            Grocery grocery = GroceryHandler.findGrocery(categoryChooser.getText().toString());
            LocalDateTime expireLocalDateTime = LocalDateTime.of(LocalDate.parse(expireDateEditText.getText(), DateTimeFormatter.ofPattern(getString(R.string.general_dateformat))), LocalTime.of(0, 0));
            LocalDateTime purchaseLocalDateTime = LocalDateTime.of(LocalDate.parse(purchaseDateEditText.getText(), DateTimeFormatter.ofPattern(getString(R.string.general_dateformat))), LocalTime.of(0, 0));
            return Offer.createOffer(price, grocery, offerDescription, purchaseLocalDateTime, expireLocalDateTime);
        }

        ToastHandler.buildToastHandler().makeToast(getString(R.string.postOffer_checkInputMessage));
        return null;
    }
}
