package com.appropel.schuss.model.impl;

import com.appropel.schuss.model.read.RentalProvider;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * Implementation of RentalShop.
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, table = "rental_provider", detachable = "true")
public final class RentalProviderImpl implements RentalProvider
{
    /** Object unique identifier. */
    @Persistent(primaryKey = "true", valueStrategy = IdGeneratorStrategy.INCREMENT)
    private long id;

    /**
     * Facility name.
     */
    @Column(length = 64)
    private String name;

    /**
     * Logo URL.
     */
    @Column(name = "logo_url", length = 256)
    private String logoUrl;

    /**
     * Background URL.
     */
    @Column(name = "background_url", length = 256)
    private String backgroundUrl;

    @Override
    public long getId()
    {
        return id;
    }

    @Override
    public String getName()
    {
        return name;
    }

    @Override
    public String getLogoUrl()
    {
        return logoUrl;
    }

    @Override
    public String getBackgroundUrl()
    {
        return backgroundUrl;
    }
}
