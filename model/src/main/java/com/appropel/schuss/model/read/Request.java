package com.appropel.schuss.model.read;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

import java.util.Date;
import java.util.List;

/**
 * A request for rental.
 */
@Value.Immutable
@JsonDeserialize(builder = ImmutableRequest.Builder.class)
public interface Request
{
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
                .rentalProvider(rentalProvider)
                .profiles(profiles)
                .arrivalTime(arrivalTime)
                .build();
    }
}
