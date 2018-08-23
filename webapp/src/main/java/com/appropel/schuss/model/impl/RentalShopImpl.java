package com.appropel.schuss.model.impl;

import com.appropel.schuss.model.read.RentalShop;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * Implementation of RentalShop.
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, table = "rental_shop", detachable = "true")
public final class RentalShopImpl implements RentalShop
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
    @Column(length = 256)
    private String logoUrl;

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
}
