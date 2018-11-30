package com.appropel.schuss.view.application;

import android.app.Application;

import com.appropel.schuss.R;
import com.appropel.schuss.dagger.DaggerWrapper;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

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
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("GlacialIndifference-Regular.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
    }
}
