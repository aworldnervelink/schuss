package com.appropel.schuss.model.read;

public interface RentalProvider
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
}
