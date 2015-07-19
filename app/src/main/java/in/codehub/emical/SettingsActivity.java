package in.codehub.emical;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.transition.Explode;
import android.util.Log;

public class SettingsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, new SettingsFragment())
                .commit();
    }

    public static class SettingsFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public void onResume() {
            super.onResume();
            for (int i = 0; i < getPreferenceScreen().getPreferenceCount(); ++i) {
                updatePreference(getPreferenceScreen().getPreference(i));
            }
            getPreferenceScreen().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);
        }

        @Override
        public void onPause() {
            super.onPause();
            getPreferenceScreen().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        }

        private void updatePreference(Preference preference) {
            if (preference instanceof EditTextPreference) {
                EditTextPreference textPreference = (EditTextPreference) preference;
                if (textPreference.getKey().equals("file_charge_key")) {
                    String summary = String.format(getString(R.string.file_charge_summary), textPreference.getText());
                    textPreference.setSummary(summary);
                } else if (textPreference.getKey().equals("service_charge_key")) {
                    String summary = String.format(getString(R.string.service_charge_summary), textPreference.getText());
                    textPreference.setSummary(summary);
                }
            }
        }

        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
            updatePreference(findPreference(key));
        }
    }
}
