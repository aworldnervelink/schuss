package com.appropel.schuss.common.util;

import org.greenrobot.eventbus.EventBus;

/**
 * Wraps a given EventBus and passes along calls.
 */
public final class EventBusWrapper implements EventBusFacade
{
    /** Object being proxied. */
    private final EventBus eventBus;

    /**
     * Parametrized EventBus constructor.
     *
     * @param eventBus EventBus object.
     */
    public EventBusWrapper(final EventBus eventBus)
    {
        this.eventBus = eventBus;
    }

    @Override
    public void register(final Object subscriber)
    {
        eventBus.register(subscriber);
    }

    @Override
    public void unregister(final Object subscriber)
    {
        eventBus.unregister(subscriber);
    }

    @Override
    public boolean isRegistered(final Object subscriber)
    {
        return eventBus.isRegistered(subscriber);
    }

    @Override
    public void post(final Object event)
    {
        eventBus.post(event);
    }
}
