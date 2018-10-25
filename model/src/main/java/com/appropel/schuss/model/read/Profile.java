package com.appropel.schuss.model.read;

/**
 * Base class for all profiles.
 */
public abstract class Profile
{
    /** Enumerates the types of profiles in the system. */
    public enum Type
    {
        DOWNHILL;
    }

    /**
     * Returns the type of profile.
     */
    public abstract Type getType();
}
