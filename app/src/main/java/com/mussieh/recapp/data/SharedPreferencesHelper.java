package com.mussieh.recapp.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.mussieh.recapp.fragment.SettingsFragment;

import java.util.HashMap;

/**
 * Created by Mussie on 3/25/2018.
 * Helper class to handle Settings data through SharedPreferences
 * Note: This class is functional but not complete. Its static context
 * might be removed
 */
public class SharedPreferencesHelper {
    private static final String TAG = SharedPreferencesHelper.class.getSimpleName();
    public static final String SHARED_PREF_FILE = "YourSharedPrefencesFileName";
    private static SharedPreferences mPreferences = null;
    private static SharedPreferences.Editor preferenceEditor = null;
    private static String previousChoice;
    private static String currentChoice;

    /**
     * Hide constructor
     */
    private SharedPreferencesHelper() {}

    /**
     * Define the SharedPreferences attributes and return its instance
     * @return the SharedPreferences instance
     */
    private static SharedPreferences getSharedPreferencesInstance(Context context) {
        if (mPreferences == null) {
            mPreferences = context.getSharedPreferences(SHARED_PREF_FILE,
                    Context.MODE_PRIVATE);
            preferenceEditor = mPreferences.edit();
            previousChoice = "";
            currentChoice = "";
        }
        return mPreferences;
    }

    /**
     * Sets a SharedPreferences value
     * @param key the key to use
     * @param value the value to set
     */
    public static void setValue(Context context, String key, String value) {
        if (mPreferences == null) {
            getSharedPreferencesInstance(context);
        }
        preferenceEditor.putString(key, value).apply();
    }

    /**
     * Returns a SharedPreferences value
     * @param key the key to use
     * @param notFoundString the default string if key not found
     * @return the SharedPreferences value
     */
    public static String getValue(Context context, String key, String notFoundString) {
        if (mPreferences == null) {
            getSharedPreferencesInstance(context);
        }
        return mPreferences.getString(key, notFoundString);
    }

    /**
     * Removes a SharedPreferences value
     * @param key the key to use
     */
    public static void removeValue(Context context, String key) {
        if (mPreferences == null) {
            getSharedPreferencesInstance(context);
        }
        preferenceEditor.remove(key).apply();
    }

    /**
     * Loads resources from user choice through FirebaseHelper
     * @param resourceType the item resource type
     * @param sortingParameter the parameter for sorting loaded data
     */
    public static void loadUserChoice(Context context, FirebaseHelper firebaseHelper,
                                      String resourceType, String sortingParameter) {
        Log.d(TAG, "userChoiceloaded");
        currentChoice = resourceType + "-" + getValue(context, SettingsFragment.KEY_SUMMARY,
                "None");
        String subject = currentChoice.split("-")[1];
        if (!currentChoice.equals(previousChoice)) {
            previousChoice = currentChoice;
            firebaseHelper.fetchAppResources(subject, resourceType, sortingParameter);
        }
    }
}
