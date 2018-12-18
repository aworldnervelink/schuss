package com.appropel.schuss.model.read;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

import java.io.Serializable;

@Value.Immutable
@JsonDeserialize(builder = ImmutableRentalProvider.Builder.class)
public interface RentalProvider extends Serializable
{
    /**
     * Returns the identifier.
     */
    long getId();

    /**
     * Returns the name of the facility.
     */
    String getName();

    /**
     * Returns a URL pointing to the facility's logo.
     */
    String getLogoUrl();

    /**
     * Returns a URL pointing to the facility's background image.
     * @return
     */
    String getBackgroundUrl();
}
