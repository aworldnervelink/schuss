package com.appropel.schuss.model.impl;

import com.appropel.schuss.model.read.Profile;
import com.appropel.schuss.model.read.Request;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.ImmutableList;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.beans.BeanUtils;

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
public final class RequestImpl implements Request, Comparable<RequestImpl>
{
    /** Object unique identifier. */
    @Persistent(primaryKey = "true", valueStrategy = IdGeneratorStrategy.INCREMENT)
    private long id;

    /** User making the request. */
    @Persistent(defaultFetchGroup = "false")
    @Column(name = "user_id")
    @JsonIgnore
    private UserImpl user;

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

    public void setCreationTime(final Date creationTime)
    {
        this.creationTime = creationTime;
    }

    @Override
    public Date getArrivalTime()
    {
        return arrivalTime;
    }

    public void setArrivalTime(final Date arrivalTime)
    {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Creates a new persistent RequestImpl from the given objects.
     * @param request request data
     * @param user user
     * @param rentalProvider rental provider
     * @param profiles profiles
     * @return initialized request
     */
    public static RequestImpl from(final Request request,
                                   final UserImpl user,
                                   final RentalProviderImpl rentalProvider,
                                   final Set<ProfileImpl> profiles)
    {
        final RequestImpl newRequest = new RequestImpl();
        BeanUtils.copyProperties(request, newRequest);
        newRequest.user = user;
        newRequest.rentalProvider = rentalProvider;
        newRequest.profiles = profiles;
        return newRequest;
    }

    @Override
    public int compareTo(final RequestImpl other)
    {
        return new CompareToBuilder()
                .append(creationTime, other.creationTime)
                .append(id, other.id)
                .toComparison();
    }
}
