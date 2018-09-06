package com.appropel.schuss.view.util;

import android.content.Context;
import android.os.Build;

import com.appropel.schuss.common.util.ContextUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;

/**
 * Implementation of context utilities.
 */
public final class DefaultContextUtils implements ContextUtils
{
    /** Context. */
    private final Context context;

    /**
     * Constructs a new {@code DefaultContextUtils}.
     *
     * @param context context.
     */
    public DefaultContextUtils(final Context context)
    {
        this.context = context;
    }

    @Override
    public String getAdvertisingId()
    {
        try
        {
            final String result = AdvertisingIdClient.getAdvertisingIdInfo(context.getApplicationContext()).getId();
            return result != null ? result : android.provider.Settings.Secure.ANDROID_ID;
        }
        catch (Exception e)
        {
            return android.provider.Settings.Secure.ANDROID_ID;
        }
    }

    @Override
    public String getModelName()
    {
        try
        {
            String manufacturer = Build.MANUFACTURER;
            String model = Build.MODEL;
            return model != null && model.startsWith(manufacturer) ? model
                    : manufacturer + " " + model;
        }
        catch (Exception e)
        {
            return "";
        }
    }
}
