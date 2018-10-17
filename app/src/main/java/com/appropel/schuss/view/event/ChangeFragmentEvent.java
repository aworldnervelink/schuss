package com.appropel.schuss.view.event;

import android.app.Fragment;
import android.support.annotation.NonNull;

import org.immutables.value.Value;

/**
 * Event which requests that the main activity change its fragment.
 */
@Value.Immutable
public abstract class ChangeFragmentEvent
{
    /**
     * Returns the desired fragment class.
     * @return fragment class
     */
    @NonNull
    public abstract Class<? extends Fragment> getFragmentClass();

    /**
     * Returns a ChangeFragmentEvent for the given Fragment class.
     * @param fragmentClass Fragment to change to
     * @return populated event
     */
    public static ChangeFragmentEvent of(final Class<? extends Fragment> fragmentClass) // NOPMD
    {
        return ImmutableChangeFragmentEvent.builder().fragmentClass(fragmentClass).build();
    }
}
