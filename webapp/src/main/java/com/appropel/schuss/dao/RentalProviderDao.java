package com.appropel.schuss.dao;

import com.appropel.schuss.model.read.RentalProvider;

import java.util.List;

/**
 * DAO methods for rental providers.
 */
public interface RentalProviderDao extends Dao<RentalProvider>
{
    /**
     * Returns a list of rental providers.
     * @return list of providers
     */
    // TODO: this should accept a location and sort by nearest.
    List<RentalProvider> getRentalProviders();
}
