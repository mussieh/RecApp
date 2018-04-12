package com.mussieh.recapp.fragment;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.ListPreference;
import android.support.v7.preference.Preference;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mussieh.recapp.R;
import com.mussieh.recapp.RecappActivity;
import com.mussieh.recapp.data.SharedPreferencesHelper;

/**
 * Fragment for hosting and displaying Settings Fragment data
 * Note: This Fragment is functional but not complete
 */
public class SettingsFragment extends PreferenceFragmentCompat {
    private static final String TAG = SettingsFragment.class.getSimpleName();
    public static final String KEY_SUBJECT_LIST = "subject_list";
    public static final String KEY_SUMMARY = "summary";
    public static final String SUMMARY_MESSAGE = "Select a subject in Computer Science";

    // Values for ListPreference in Settings
    private static final String ALGORITHMS_AND_DATA_STRUCTURES = "algorithms_and_data_structures";
    private static final String ANDROID_DEVELOPMENT = "android_development";
    private static final String INTERVIEW_PREPARATION = "interview_preparation";
    private static final String WEB_DEVELOPMENT = "web_development";

    private String sharedPrefFile = SharedPreferencesHelper.SHARED_PREF_FILE;
    private SharedPreferences mPreferences;

    // Required empty public constructor
    public SettingsFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        String userChoice = mPreferences.getString(KEY_SUMMARY, SUMMARY_MESSAGE);
        Preference preference = this.findPreference(KEY_SUBJECT_LIST);
        preference.setSummary(userChoice);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideBackButton();

        if (this.getActivity() != null) {
            mPreferences = this.getActivity().getSharedPreferences(sharedPrefFile,
                    Context.MODE_PRIVATE);

            if (mPreferences != null) {
                Preference preference = this.findPreference(KEY_SUBJECT_LIST);
                preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener()
                {
                    @Override
                    public boolean onPreferenceChange(Preference preference, Object newValue) {
                        SharedPreferences.Editor preferenceEditor = mPreferences.edit();
                        String userChoice = newValue.toString();

                        switch (userChoice) {
                            case ALGORITHMS_AND_DATA_STRUCTURES:
                                preference.setSummary(R.string.pref_algorithms_and_data_structures);
                                preferenceEditor.putString("summary",
                                        getString(R.string.pref_algorithms_and_data_structures)).
                                        apply();
                                break;
                            case ANDROID_DEVELOPMENT:
                                preference.setSummary(R.string.pref_android_development);
                                preferenceEditor.putString("summary",
                                        getString(R.string.pref_android_development)).apply();
                                break;
                            case INTERVIEW_PREPARATION:
                                preference.setSummary(R.string.pref_interview_preparation);
                                preferenceEditor.putString("summary",
                                        getString(R.string.pref_interview_preparation)).apply();
                                break;
                            case WEB_DEVELOPMENT:
                                preference.setSummary(R.string.pref_web_development);
                                preferenceEditor.putString("summary",
                                        getString(R.string.pref_web_development)).apply();
                                break;
                        }

                        return true;
                    }
                });
            }
        }
    }

    /**
     * Hides the back button
     */
    private void hideBackButton() {
        if (getActivity() instanceof RecappActivity) {
            android.support.v7.app.ActionBar supportActionBar =
                    ((RecappActivity) getActivity()).getSupportActionBar();
            if (supportActionBar != null) {
                supportActionBar.setDisplayHomeAsUpEnabled(false);
            }
        }
    }

    /**
     * Returns an instance of SettingsFragment
     * @return the SettingsFragment
     */
    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }

}
