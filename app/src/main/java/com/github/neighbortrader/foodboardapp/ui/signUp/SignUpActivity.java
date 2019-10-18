package com.github.neighbortrader.foodboardapp.ui.signUp;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.clientmodel.Address;
import com.github.neighbortrader.foodboardapp.clientmodel.User;
import com.github.neighbortrader.foodboardapp.ui.createOffer.CreateOfferActivity;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.editText_username)
    TextInputEditText usernameEditText;

    @BindView(R.id.editText_email)
    TextInputEditText emailEditText;

    @BindView(R.id.editText_password)
    TextInputEditText passwordEditText;

    @BindView(R.id.editText_street)
    TextInputEditText streetEditText;

    @BindView(R.id.editText_number)
    TextInputEditText numberEditText;

    @BindView(R.id.editText_postcode)
    TextInputEditText postCodeEditText;

    @BindView(R.id.editText_town)
    TextInputEditText townEditText;

    @BindView(R.id.stay_signedin)
    MaterialCheckBox staySignedInCheckBox;

    SignUpController controller;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        ButterKnife.bind(this);

        controller = new SignUpController(this);

        getSupportActionBar().setTitle(getString(R.string.sign_up_headline));
    }

    @OnClick(R.id.signupButton)
    public void invokeSignUp() {
        User userToSignUp = createUserFromInput();

        controller.invokeSignUp(userToSignUp);
    }

    public void setProgressbarState(CreateOfferActivity.progressBarStates state) {
        if (state == CreateOfferActivity.progressBarStates.LOADING) {
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.VISIBLE);
        } else if (state == CreateOfferActivity.progressBarStates.NOT_LOADING) {
            progressBar.setVisibility(View.INVISIBLE);
            progressBar.setIndeterminate(false);
            progressBar.setVisibility(View.GONE);
        } else if (state == CreateOfferActivity.progressBarStates.FINISHED) {
            progressBar.setIndeterminate(false);
            progressBar.setVisibility(View.VISIBLE);
        } else if (state == CreateOfferActivity.progressBarStates.ERROR) {
            progressBar.setVisibility(View.INVISIBLE);
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.GONE);
        }
    }

    private User createUserFromInput() {
        String username = usernameEditText.getEditableText().toString();
        String email = emailEditText.getEditableText().toString();
        String password = passwordEditText.getEditableText().toString();
        String street = streetEditText.getEditableText().toString();
        String number = numberEditText.getEditableText().toString();
        String postCode = postCodeEditText.getEditableText().toString();
        String town = townEditText.getEditableText().toString();
        boolean staySigendIn = staySignedInCheckBox.isChecked();

        Address address = new Address(street, number, postCode, town);

        return User.userBuilder(username, password, email, address, staySigendIn);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

