package com.appropel.schuss.model.read;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

/**
 * Represents a specific device that a user runs the app on.
 */
@Value.Immutable
@JsonDeserialize(builder = ImmutableDevice.Builder.class)
public interface Device
{
    /**
     * Returns the Google Advertising ID.
     *
     * @return advertising id
     */
    String getAdvertisingId();

    /**
     * Returns the model name of the user's device.
     *
     * @return model name or an empty string.
     */
    String getModelName();

    /**
     * Returns the {@link User} that owns this device.
     * @return associated user
     */
    User getUser();
}
