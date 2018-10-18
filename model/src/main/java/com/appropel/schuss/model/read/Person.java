package com.appropel.schuss.model.read;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(builder = ImmutablePerson.Builder.class)
public interface Person
{
    /**
     * Returns the identifier.
     */
    long getId();

    /**
     * Returns the first name of the person.
     */
    String getFirstName();

    /**
     * Returns the last name of the person.
     */
    String getLastName();

    /**
     * Returns the first name of the person's guardian (may be empty).
     */
    String getGuardianFirstName();

    /**
     * Returns the last name of the person's guardian (may be empty).
     */
    String getGuardianLastName();

    // TODO: insert addresses here

    /**
     * Returns the person's e-mail address.
     */
    String getEmailAddress();

    /**
     * Returns the person's contact phone number.
     */
    String getPhoneNumber();
}
