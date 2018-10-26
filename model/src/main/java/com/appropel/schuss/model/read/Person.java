package com.appropel.schuss.model.read;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;

import org.immutables.value.Value;

import java.io.Serializable;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value.Immutable
@JsonDeserialize(builder = ImmutablePerson.Builder.class)
public abstract class Person implements Serializable
{
    /** Regular expression for a telephone number. */
    public static final String TELEPHONE_REGEX = "^\\(?([0-9]{3})\\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$|^$";

    /** Telephone number pattern. */
    public static final Pattern TELEPHONE_PATTERN = Pattern.compile(TELEPHONE_REGEX);

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

    /**
     * Returns the person's address.
     */
    public abstract Address getAddress();

    /**
     * Returns the person's e-mail address.
     */
    public abstract String getEmailAddress();

    /**
     * Returns the person's contact phone number.
     */
    public abstract String getPhoneNumber();

    /**
     * Returns a Set of all Profiles for this Person.
     */
    public abstract Set<Profile> getProfiles();

    @Value.Check
    protected void check()
    {
        final Matcher telephoneMatcher = TELEPHONE_PATTERN.matcher(getPhoneNumber());
        Preconditions.checkState(telephoneMatcher.matches());
    }
}
