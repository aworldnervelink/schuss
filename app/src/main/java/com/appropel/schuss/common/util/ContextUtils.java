package com.appropel.schuss.common.util;

/**
 * Android Context utilities.
 */
public interface ContextUtils
{
    /**
     * Returns user's advertising id. WARNING: this call may not be executed on the main thread.
     * @return advertising id or null on failure
     */
    String getAdvertisingId();

    /**
     * Returns the model name of the user's device.
     *
     * @return model name or an empty string.
     */
    String getModelName();
}
