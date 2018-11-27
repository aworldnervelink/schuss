package com.appropel.schuss.model.read;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;

import org.immutables.value.Value;

import java.io.Serializable;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value.Immutable
@JsonDeserialize(builder = ImmutablePerson.Builder.class)
public interface Person extends Serializable
{
    /** Regular expression for a telephone number. */
    String TELEPHONE_REGEX = "^\\(?([0-9]{3})\\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$|^$";

    /** Telephone number pattern. */
    Pattern TELEPHONE_PATTERN = Pattern.compile(TELEPHONE_REGEX);

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

    /**
     * Returns the person's address.
     */
    Address getAddress();

    /**
     * Returns the person's e-mail address.
     */
    String getEmailAddress();

    /**
     * Returns the person's contact phone number.
     */
    String getPhoneNumber();

    /**
     * Returns a Set of all Profiles for this Person.
     */
    Set<Profile> getProfiles();

    @Value.Check
    default void check()
    {
        final Matcher telephoneMatcher = TELEPHONE_PATTERN.matcher(getPhoneNumber());
        Preconditions.checkState(telephoneMatcher.matches());
    }
}
