package com.appropel.schuss.model.impl;

import com.appropel.schuss.model.read.Profile;
import com.appropel.schuss.model.util.JsonViews;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonView;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.beans.BeanUtils;

import javax.jdo.annotations.Column;
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
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true", table = "profile")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
public abstract class ProfileImpl implements Profile, Comparable<ProfileImpl>
{
    /** Object unique identifier. */
    @Persistent(primaryKey = "true", valueStrategy = IdGeneratorStrategy.INCREMENT)
    private long id;

    /** Related person. */
    @Column(name = "person_id")
    private PersonImpl person;

    @Override
    public long getId()
    {
        return id;
    }

    @JsonView(JsonViews.RequestQueue.class)
    public PersonImpl getPerson()
    {
        return person;
    }

    /**
     * Returns the profile type.
     * @return profile type
     */
    @Override
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
        return new CompareToBuilder()
                .append(getProfileType(), other.getProfileType())
                .append(getPerson(), other.getPerson())
                .toComparison();
    }
}
