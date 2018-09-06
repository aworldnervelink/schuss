package com.appropel.schuss.controller.event;

/**
 * Event posted when advertising ID is changed. Normally this event is posted once
 * on the first app launch when advertising ID becomes available.
 */
public final class AdvertisingIdChangeEvent
{
    /** Advertising ID. */
    private final String advertisingId;

    /**
     * Parametrized AdvertisingIdChangeEvent constructor.
     *
     * @param advertisingId advertisingId.
     */
    public AdvertisingIdChangeEvent(final String advertisingId)
    {
        this.advertisingId = advertisingId;
    }

    public String getAdvertisingId()
    {
        return advertisingId;
    }
}
