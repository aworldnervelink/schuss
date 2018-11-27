package com.appropel.schuss.model.util;

/**
 * Defines views that can alter the default serialization.
 */
public interface JsonViews
{
    /**
     * View for looking at the user home.
     */
    interface RenterHome {}

    /**
     * View for looking at the request queue.
     */
    interface RequestQueue {}
}
