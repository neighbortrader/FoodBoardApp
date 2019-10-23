package com.github.neighbortrader.foodboardapp.ui.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import com.github.neighbortrader.foodboardapp.R;
import com.github.neighbortrader.foodboardapp.handler.clientmodelHandler.UserHandler;
import com.github.neighbortrader.foodboardapp.handler.toastHandler.ToastHandler;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
    }

    @Override
    public boolean onPreferenceTreeClick(Preference preference) {
        String key = preference.getKey();
        if (key.equals(getString(R.string.settings_feedback_key))) {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            intent.setData(Uri.parse(getString(R.string.feedback_email)));
            intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.feedback_email_subject));

            startActivity(Intent.createChooser(intent, getString(R.string.sendFeedback_title)));
        } else if (key.equals(getString(R.string.settings_signOut_Key))) {
            ToastHandler.buildToastHandler().notAvailabel();
        }
        return false;
    }
}
