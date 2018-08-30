package com.appropel.schuss.view.event;

import android.app.Fragment;
import android.support.annotation.NonNull;

import org.immutables.value.Value;

/**
 * Event which requests that the main activity change its fragment.
 */
@Value.Immutable
public interface ChangeFragmentEvent
{
    /**
     * Returns the desired fragment class.
     * @return fragment class
     */
    @NonNull
    Class<? extends Fragment> getFragmentClass();
}
