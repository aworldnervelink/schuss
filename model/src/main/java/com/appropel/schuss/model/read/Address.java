package com.appropel.schuss.model.read;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;

import org.immutables.value.Value;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Value.Immutable
@JsonDeserialize(builder = ImmutableAddress.Builder.class)
public interface Address extends Serializable
{
    /** Regular expression for a postal code. */
    String POSTAL_CODE_REGEX = "^[0-9]{5}(?:-[0-9]{4})?$";

    /** Postal code pattern. */
    Pattern POSTAL_CODE_PATTERN = Pattern.compile(POSTAL_CODE_REGEX);

    /**
     * Returns the identifier.
     */
    long getId();

    /**
     * Returns the first line of the address.
     */
    String getAddressLine1();

    /**
     * Returns the second line of the address.
     */
    String getAddressLine2();

    /**
     * Returns the city.
     */
    String getCity();

    /**
     * Returns the state or province abbreviation.
     */
    String getStateProvince();

    /**
     * Returns the postal code.
     */
    String getPostalCode();

    @Value.Check
    default void check()
    {
        final Matcher postalCodeMatcher = POSTAL_CODE_PATTERN.matcher(getPostalCode());
        Preconditions.checkState(postalCodeMatcher.matches());
    }
}
