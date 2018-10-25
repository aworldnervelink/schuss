package com.appropel.schuss.view.event;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;

import org.immutables.value.Value;

import java.io.Serializable;

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
     * Bundle of arguments.
     * @return Bundle
     */
    @NonNull
    public abstract Bundle getArguments();

    /**
     * Returns a ChangeFragmentEvent for the given Fragment class.
     * @param fragmentClass Fragment to change to
     * @return populated event
     */
    public static ChangeFragmentEvent of(final Class<? extends Fragment> fragmentClass)
    {
        return ImmutableChangeFragmentEvent.builder()
                .fragmentClass(fragmentClass)
                .arguments(new Bundle())
                .build();
    }

    /**
     * Returns a ChangeFragmentEvent for the given Fragment class.
     * @param fragmentClass fragment to change to
     * @param argumentKey   fragment argument key.
     * @param argumentValue fragment argument value.
     * @return populated event
     */
    public static ChangeFragmentEvent of(@NonNull final Class<? extends Fragment> fragmentClass,
                                         @NonNull final String argumentKey,
                                         @NonNull final Serializable argumentValue)
    {
        return ImmutableChangeFragmentEvent.builder()
                .fragmentClass(fragmentClass)
                .arguments(createArguments(argumentKey, argumentValue))
                .build();
    }

    /**
     * Creates fragment arguments containing single item.
     *
     * @param key   argument key
     * @param value argument value
     * @return created arguments bundle
     */
    @NonNull
    private static Bundle createArguments(@NonNull final String key, @NonNull final Serializable value)
    {
        final Bundle args = new Bundle();
        args.putSerializable(key, value);
        return args;
    }
}
