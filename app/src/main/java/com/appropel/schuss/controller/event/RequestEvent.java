package com.appropel.schuss.controller.event;

import com.appropel.schuss.model.read.Request;

import org.immutables.value.Value;

import java.util.Set;

/**
 * Event posted when Request list is retrieved from the server.
 */
@Value.Immutable
public abstract class RequestEvent
{
    /**
     * Returns a set of Request.
     * @return requests
     */
    public abstract Set<Request> getRequests();

    /**
     * Returns an initialized event.
     * @param requests set of Request
     * @return event
     */
    public static RequestEvent of(final Set<Request> requests)
    {
        return ImmutableRequestEvent.builder().addAllRequests(requests).build();
    }
}
