package com.appropel.schuss.model.read;

/**
 * Base class for all profiles.
 */
public interface Profile
{
    /** Enumerates the types of profiles in the system. */
    public enum Type
    {
        DOWNHILL;
    }

    /**
     * Returns the identifier.
     */
    long getId();

    /**
     * Returns the type of profile.
     */
    Type getProfileType();
}
