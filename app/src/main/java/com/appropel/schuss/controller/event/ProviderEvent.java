package com.appropel.schuss.controller.event;

import com.appropel.schuss.model.read.RentalProvider;

import org.immutables.value.Value;

import java.util.List;

/**
 * Event posted when Provider data is retrieved from the server.
 */
@Value.Immutable
public abstract class ProviderEvent
{
    /**
     * Returns a list of RentalProvider.
     * @return list of RentalProvider
     */
    public abstract List<RentalProvider> getRentalProviders();

    /**
     * Returns an initialized event.
     * @param list list of RentalProvider
     * @return event
     */
    public static ProviderEvent of(final List<RentalProvider> list)
    {
        return ImmutableProviderEvent.builder().addAllRentalProviders(list).build();
    }
}
