package com.mussieh.recapp;

import android.app.Application;
import android.content.Context;

/**
 * Created by Mussie on 2/26/2018.
 *
 */

public class App extends Application {

    private static Context mContext;

    public static Context getContext() {
        return mContext;
    }

    public static void setContext(Context context) {
        mContext = context;
    }
}
