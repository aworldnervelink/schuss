package com.appropel.schuss.dao;

import com.appropel.schuss.model.impl.RentalProviderImpl;

import java.util.List;

/**
 * DAO methods for rental providers.
 */
public interface RentalProviderDao extends Dao<RentalProviderImpl>
{
    /**
     * Returns a list of rental providers.
     * @return list of providers
     */
    // TODO: this should accept a location and sort by nearest.
    List<RentalProviderImpl> getRentalProviders();
}
