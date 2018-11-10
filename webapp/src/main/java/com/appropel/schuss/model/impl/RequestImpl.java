package com.appropel.schuss.model.impl;

import com.appropel.schuss.model.read.Profile;
import com.appropel.schuss.model.read.Request;
import com.google.common.collect.ImmutableList;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Join;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * Represents a request for rental gear.
 */
@SuppressWarnings("PMD")
@PersistenceCapable(identityType = IdentityType.APPLICATION, table = "request", detachable = "true")
public final class RequestImpl implements Request
{
    /** Object unique identifier. */
    @Persistent(primaryKey = "true", valueStrategy = IdGeneratorStrategy.INCREMENT)
    private long id;

    /** Rental provider. */
    @Persistent(defaultFetchGroup = "true")
    @Column(name = "rental_provider_id")
    private RentalProviderImpl rentalProvider;

    /** Profiles. */
    @Persistent(table = "request_profile")
    @Join(column = "request_id")
    @Element(column = "profile_id")
    private Set<ProfileImpl> profiles = new TreeSet<>();

    /** Creation time. */
    @Column(name = "creation_time")
    private Date creationTime;

    /** Arrival time. */
    @Column(name = "arrival_time")
    private Date arrivalTime;

    @Override
    public long getId()
    {
        return id;
    }

    @Override
    public RentalProviderImpl getRentalProvider()
    {
        return rentalProvider;
    }

    @Override
    public List<Profile> getProfiles()
    {
        return ImmutableList.copyOf(profiles);
    }

    @Override
    public Date getCreationTime()
    {
        return creationTime;
    }

    @Override
    public Date getArrivalTime()
    {
        return arrivalTime;
    }
}
