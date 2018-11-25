package com.appropel.schuss.model.read;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;
import org.immutables.value.internal.$processor$.meta.$GsonMirrors;

import java.io.Serializable;
import java.util.Set;

import javax.annotation.Nullable;

/**
 * Represents a user of the application - can also be thought of as an account.
 */
@Value.Immutable
@JsonDeserialize(builder = ImmutableUser.Builder.class)
public interface User extends Serializable
{
    /**
     * Defines the roles that an account can play.
     */
    public enum Role
    {
        /** This account represents one or more people who rent equipment. */
        RENTER,
        /** This account is a worker who provides rental equipment. */
        WORKER;
    }

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

    /**
     * Returns the role of this account.
     * @return role
     */
    Role getRole();

    /**
     * Returns the rental provider associated with this account, if role == WORKER.
     * @return rental provider
     */
    @Nullable
    RentalProvider getRentalProvider();

    /**
     * Returns the user token associated with this account.
     * @return user token
     */
    String getToken();
}
