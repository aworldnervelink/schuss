package com.appropel.schuss.common.util;

/**
 * Interface to an Event Bus. Using a facade allows us to easily create a test implementation.
 */
public interface EventBusFacade
{
    /**
     * Registers the given subscriber to receive events. Subscribers must call {@link #unregister(Object)} once they
     * are no longer interested in receiving events.
     *
     * @param subscriber object subscribing.
     */
    void register(Object subscriber);

    /**
     * Unregisters the given subscriber from all event classes.
     *
     * @param subscriber object unsubscribing.
     */
    void unregister(Object subscriber);

    /**
     * checks if the given subscriber is registered.
     *
     * @param subscriber object to be checked.
     * @return true if registered.
     */
    boolean isRegistered(Object subscriber);

    /**
     * Posts the given event to the event bus.
     *
     * @param event event being posted.
     */
    void post(Object event);
}
