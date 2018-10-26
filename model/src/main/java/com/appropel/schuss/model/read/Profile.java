package com.appropel.schuss.model.read;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Base class for all profiles.
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DownhillProfile.class, name = "DownhillProfile")
})
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
