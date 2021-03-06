package com.appropel.schuss.model.impl;

import com.appropel.schuss.model.read.DownhillProfile;
import com.appropel.schuss.model.read.Profile;
import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;

/**
 * Profile for downhill skiing.
 */
@SuppressWarnings("PMD")
@PersistenceCapable(table = "downhill_profile")
@Inheritance(strategy = InheritanceStrategy.NEW_TABLE)
@JsonTypeName("DownhillProfile")
public final class DownhillProfileImpl extends ProfileImpl implements DownhillProfile
    // NOTE: can't extend or implement DownhillProfile - hierarchy is breaking down here.
{
    /** Skier type. */
    @Column(name = "skier_type", length = 30)
    private String skierType;

    /** Boot size. */
    @Column(name = "boot_size", length = 20)
    private String bootSize;

    /** Ski size. */
    @Column(name = "ski_size", length = 20)
    private String skiSize;

    /** Pole length. */
    @Column(name = "pole_length", length = 20)
    private String skiPoleLength;

    /** Helmet size. */
    @Column(name = "helmet_size", length = 20)
    private String helmetSize;

    @Override
    public Profile.Type getProfileType()
    {
        return Profile.Type.DOWNHILL;
    }

    @Override
    public String getSkierType()
    {
        return skierType;
    }

    public void setSkierType(final String skierType)
    {
        this.skierType = skierType;
    }

    @Override
    public String getBootSize()
    {
        return bootSize;
    }

    public void setBootSize(final String bootSize)
    {
        this.bootSize = bootSize;
    }

    @Override
    public String getSkiSize()
    {
        return skiSize;
    }

    public void setSkiSize(final String skiSize)
    {
        this.skiSize = skiSize;
    }

    @Override
    public String getSkiPoleLength()
    {
        return skiPoleLength;
    }

    public void setSkiPoleLength(final String skiPoleLength)
    {
        this.skiPoleLength = skiPoleLength;
    }

    @Override
    public String getHelmetSize()
    {
        return helmetSize;
    }

    public void setHelmetSize(final String helmetSize)
    {
        this.helmetSize = helmetSize;
    }
}
