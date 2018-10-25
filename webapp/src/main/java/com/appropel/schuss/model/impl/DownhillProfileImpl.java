package com.appropel.schuss.model.impl;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.PersistenceCapable;

/**
 * Profile for downhill skiing.
 */
@SuppressWarnings("PMD")
@PersistenceCapable(table = "downhill_profile")
public final class DownhillProfileImpl extends ProfileImpl
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
    public Type getProfileType()
    {
        return Type.DOWNHILL;
    }

    public String getSkierType()
    {
        return skierType;
    }

    public void setSkierType(final String skierType)
    {
        this.skierType = skierType;
    }

    public String getBootSize()
    {
        return bootSize;
    }

    public void setBootSize(final String bootSize)
    {
        this.bootSize = bootSize;
    }

    public String getSkiSize()
    {
        return skiSize;
    }

    public void setSkiSize(final String skiSize)
    {
        this.skiSize = skiSize;
    }

    public String getSkiPoleLength()
    {
        return skiPoleLength;
    }

    public void setSkiPoleLength(final String skiPoleLength)
    {
        this.skiPoleLength = skiPoleLength;
    }

    public String getHelmetSize()
    {
        return helmetSize;
    }

    public void setHelmetSize(final String helmetSize)
    {
        this.helmetSize = helmetSize;
    }
}
