package com.appropel.schuss.model.read;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

import java.util.Set;

/**
 * Represents a user of the application.
 */
@Value.Immutable
@JsonDeserialize(builder = ImmutableUser.Builder.class)
public interface User
{
    /**
     * Returns the user's e-mail address.
     * @return e-mail address.
     */
    String getEmail();

    /**
     * Returns the devices that the user has run the application on.
     * @return set of devices.
     */
    @JsonIgnore
    Set<Device> getDevices();
}
