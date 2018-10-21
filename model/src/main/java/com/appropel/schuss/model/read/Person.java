package com.appropel.schuss.model.read;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(builder = ImmutablePerson.Builder.class)
public abstract class Person
{
    /**
     * Returns the identifier.
     */
    public abstract long getId();

    /**
     * Returns the first name of the person.
     */
    public abstract String getFirstName();

    /**
     * Returns the last name of the person.
     */
    public abstract String getLastName();

    /**
     * Returns the first name of the person's guardian (may be empty).
     */
    public abstract String getGuardianFirstName();

    /**
     * Returns the last name of the person's guardian (may be empty).
     */
    public abstract String getGuardianLastName();

    // TODO: insert addresses here

    /**
     * Returns the person's e-mail address.
     */
    public abstract String getEmailAddress();

    /**
     * Returns the person's contact phone number.
     */
    public abstract String getPhoneNumber();
}
