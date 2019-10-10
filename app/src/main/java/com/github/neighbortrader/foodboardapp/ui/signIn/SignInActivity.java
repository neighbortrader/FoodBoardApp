package com.github.neighbortrader.foodboardapp.ui.signIn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.ui.createOffer.CreateOfferActivity;
import com.github.neighbortrader.foodboardapp.ui.signUp.SignUpActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignInActivity extends AppCompatActivity {
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.editText_username)
    TextInputEditText usernameEditText;
    @BindView(R.id.layout_Username)
    TextInputLayout usernameInputLayout;
    @BindView(R.id.editText_password)
    TextInputEditText passwordEditText;
    @BindView(R.id.layout_Password)
    TextInputLayout passwordInputLayout;
    @BindView(R.id.stay_signedin)
    MaterialCheckBox staySignedInRadioButton;
    @BindView(R.id.signIn)
    MaterialButton signInRadioButton;
    @BindView(R.id.signup_here)
    MaterialButton signUpRadioButton;

    public enum progressBarStates {NOT_LOADING, LOADING, FINISHED, ERROR}

    private SignInController controller;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        ButterKnife.bind(this);

        controller = new SignInController(this);

        getSupportActionBar().setTitle(getString(R.string.sign_in_headline));
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
            progressBar.setProgress(100, true);
        } else if (state == CreateOfferActivity.progressBarStates.ERROR) {
            progressBar.setVisibility(View.INVISIBLE);
            progressBar.setIndeterminate(true);
            progressBar.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.signup_here)
    public void startSignUpActivity() {
        Intent startSignUpIntent = new Intent(SignInActivity.this, SignUpActivity.class);
        SignInActivity.this.startActivity(startSignUpIntent);
    }

    @OnClick(R.id.signIn)
    public void invokeSignIn() {

    }

    public boolean checkAll() {
        return true;
    }

    public boolean checkUsername() {
        return true;
    }

    public boolean checkPassword() {
        return true;
    }
}
