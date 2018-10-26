package com.appropel.schuss.model.impl;

import com.appropel.schuss.model.read.Profile;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import org.springframework.beans.BeanUtils;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * Implementation of abstract Profile.
 */
@SuppressWarnings({"PMD", "DesignForExtension"})
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
public abstract class ProfileImpl implements Comparable<ProfileImpl>
{
    /** Object unique identifier. */
    @Persistent(primaryKey = "true", valueStrategy = IdGeneratorStrategy.INCREMENT)
    private long id;

    public long getId()
    {
        return id;
    }

    /**
     * Returns the profile type.
     * @return profile type
     */
    public abstract Profile.Type getProfileType();

    /**
     * Returns a persistent Profile based upon the given lightweight Profile.
     * @param profile profile
     * @return persistent profile
     */
    public static ProfileImpl from(final Profile profile)
    {
        final ProfileImpl newProfile;
        switch (profile.getProfileType())
        {
            case DOWNHILL:
                newProfile = new DownhillProfileImpl();
                break;
            default:
                throw new IllegalArgumentException("Unknown profile type: " + profile.getProfileType().name());
        }
        BeanUtils.copyProperties(profile, newProfile);
        return newProfile;
    }

    @Override
    public int compareTo(final ProfileImpl other)
    {
        return getProfileType().compareTo(other.getProfileType());
    }
}
