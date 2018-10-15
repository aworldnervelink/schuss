package com.appropel.schuss.controller.event;

import com.appropel.schuss.logic.ServiceError;

/**
 * Event to be posted if the response received from app server was not successful.
 */
public final class AppServerRequestFailure
{
    /** Error sent be the server. */
    private final ServiceError error;

    /**
     * Constructs a new {@code AppServerRequestFailure}.
     *
     * @param error error.
     */
    public AppServerRequestFailure(final ServiceError error)
    {
        this.error = error;
    }

    public ServiceError getError()
    {
        return error;
    }
}
