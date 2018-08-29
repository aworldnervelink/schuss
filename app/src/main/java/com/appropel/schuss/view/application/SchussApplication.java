package com.appropel.schuss.view.application;

import android.app.Application;

import com.appropel.schuss.dagger.DaggerWrapper;

/**
 * Application which initializes Dagger injection.
 */
public final class SchussApplication extends Application
{
    @Override
    public void onCreate()
    {
        super.onCreate();
        DaggerWrapper.INSTANCE.init(this);
    }
}
