package com.example.room.application;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * Created by felipe on 12/01/18.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
