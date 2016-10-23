package com.example.alena.tututest;

import android.support.multidex.MultiDexApplication;

import timber.log.Timber;

/**
 * Created by alena on 20.10.2016.
 */

public class TutuTestApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}

