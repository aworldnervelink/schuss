package com.appropel.schuss.model.read;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

import java.util.Date;

/**
 * A request for rental.
 */
@Value.Immutable
@JsonDeserialize(builder = ImmutableRequest.Builder.class)
public interface Request
{
    /**
     * Returns the profile describing what will be rented.
     */
    Profile getProfile();

    /**
     * Returns the shop providing the equipment.
     */
    RentalProvider getRentalProvider();

    /**
     * Returns the point in time when this request was created.
     */
    Date getCreationTime();
}
