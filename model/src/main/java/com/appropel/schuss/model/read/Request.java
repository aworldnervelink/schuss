package com.appropel.schuss.model.read;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.google.common.base.Preconditions;

import org.immutables.value.Value;

import java.util.Date;
import java.util.List;

import static com.google.common.base.Preconditions.checkState;

/**
 * A request for rental.
 */
@Value.Immutable
@JsonDeserialize(builder = ImmutableRequest.Builder.class)
public interface Request
{
    /** Comparison date in November 2018. */
    static final Date CHECK_DATE = new Date(1541876067094L);

    /**
     * Returns the identifier.
     */
    long getId();

    /** Returns the shop providing the equipment. */
    RentalProvider getRentalProvider();

    /** Returns the profiles describing what will be rented. */
    List<Profile> getProfiles();

    /** Returns the point in time when this request was created. */
    @Value.Default
    default Date getCreationTime()
    {
        return new Date();
    }

    /** Returns the expected time of arrival. */
    Date getArrivalTime();

    static Request of(RentalProvider rentalProvider,
                      List<Profile> profiles,
                      Date arrivalTime
                      )
    {
        return ImmutableRequest.builder()
                .id(0)
                .rentalProvider(rentalProvider)
                .profiles(profiles)
                .arrivalTime(arrivalTime)
                .build();
    }

    @Value.Check
    default void check()
    {
        checkState(getCreationTime().after(CHECK_DATE));
        checkState(getArrivalTime().after(CHECK_DATE));
    }
}
