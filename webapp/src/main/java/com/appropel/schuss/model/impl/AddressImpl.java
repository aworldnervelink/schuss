package com.appropel.schuss.model.impl;

import com.appropel.schuss.model.read.Address;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * Postal address.
 */
@SuppressWarnings("PMD")
@PersistenceCapable(identityType = IdentityType.APPLICATION, table = "address", detachable = "true")
public final class AddressImpl extends Address
{
    /** Object unique identifier. */
    @Persistent(primaryKey = "true", valueStrategy = IdGeneratorStrategy.INCREMENT)
    private long id;

    /** Address line 1. */
    @Column(name = "address_line_1", length = 64)
    private String addressLine1;

    /** Address line 2. */
    @Column(name = "address_line_2", length = 64)
    private String addressLine2;

    /** City. */
    @Column(name = "city", length = 32)
    private String city;

    /** State/province. */
    @Column(name = "state_province", length = 4)
    private String stateProvince;

    /** Postal code. */
    @Column(name = "postal_code", length = 12)
    private String postalCode;

    @Override
    public long getId()
    {
        return id;
    }

    @Override
    public String getAddressLine1()
    {
        return addressLine1;
    }

    public void setAddressLine1(final String addressLine1)
    {
        this.addressLine1 = addressLine1;
    }

    @Override
    public String getAddressLine2()
    {
        return addressLine2;
    }

    public void setAddressLine2(final String addressLine2)
    {
        this.addressLine2 = addressLine2;
    }

    @Override
    public String getCity()
    {
        return city;
    }

    public void setCity(final String city)
    {
        this.city = city;
    }

    @Override
    public String getStateProvince()
    {
        return stateProvince;
    }

    public void setStateProvince(final String stateProvince)
    {
        this.stateProvince = stateProvince;
    }

    @Override
    public String getPostalCode()
    {
        return postalCode;
    }

    public void setPostalCode(final String postalCode)
    {
        this.postalCode = postalCode;
    }
}
