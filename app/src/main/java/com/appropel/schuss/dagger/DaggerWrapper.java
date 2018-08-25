package com.appropel.schuss.dagger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Holds the Dagger component needed for injecting view components.
 */
public enum DaggerWrapper
{
    /** Singleton instance. */
    INSTANCE;

    /** OM component. */
    @Nullable
    private SchussComponent component;

    /**
     * Mandatory Dagger wrapper initialization.
     *
     * @param appContext application context
     */
    public void init(final Context appContext)
    {
        init(new SchussModule(appContext));
    }

    /**
     * Mandatory Dagger wrapper initialization. Use this method if your test needs to replace
     * the default Dagger module.
     *
     * @param module module
     */
    public void init(final SchussModule module)
    {
        component = DaggerSchussComponent.builder()
            .schussModule(module)
            .build();
    }

    /**
     * Returns Dagger component.
     *
     * @return Dagger component
     */
    @NonNull
    public SchussComponent getComponent()
    {
        if (component == null)
        {
            throw new IllegalStateException("DaggerWrapper must be initialized before use");
        }
        return component;
    }
}
