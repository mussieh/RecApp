package com.mussieh.recapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mussieh.recapp.fragment.SettingsFragment;

/**
 * This activity is responsible for displaying user Settings
 * Note: this activity is no longer explicitly used since the Settings Fragment
 * is directly loaded with a ViewPager.
 * But it might get used in the future when more Setting items appear which is why it is still
 * here
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportFragmentManager().beginTransaction().replace(android.R.id.content,
                new SettingsFragment()).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
