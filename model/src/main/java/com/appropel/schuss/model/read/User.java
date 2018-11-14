package com.appropel.schuss.model.read;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;
import org.immutables.value.internal.$processor$.meta.$GsonMirrors;

import java.io.Serializable;
import java.util.Set;

/**
 * Represents a user of the application - can also be thought of as an account.
 */
@Value.Immutable
@JsonDeserialize(builder = ImmutableUser.Builder.class)
public interface User extends Serializable
{
    /**
     * Returns the identifier.
     */
    long getId();

    /**
     * Returns the user's e-mail address.
     * @return e-mail address
     */
    String getEmail();

    /**
     * Returns the user's (encrypted) password.
     * @return encrypted password
     */
    @Value.Default
    default String getPassword()
    {
        return "";
    }

    /**
     * Returns the devices that the user has run the application on.
     * @return immutable set of devices.
     */
    @JsonIgnore
    Set<Device> getDevices();

    /**
     * Returns a set of all Persons associated to this user account.
     * @return immutable set of persons
     */
    Set<Person> getPersons();

    /**
     * Returns a set of the user's requests.
     * @return immutable set of requests
     */
    Set<Request> getRequests();
}
