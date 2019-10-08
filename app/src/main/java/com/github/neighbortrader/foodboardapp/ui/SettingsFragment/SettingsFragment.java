package com.github.neighbortrader.foodboardapp.ui.SettingsFragment;

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

import com.github.neighbortrader.foodboardapp.R;


public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}