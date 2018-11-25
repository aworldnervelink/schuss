package com.appropel.schuss.dao;

import com.appropel.schuss.model.impl.RentalProviderImpl;
import com.appropel.schuss.model.impl.RequestImpl;

import java.util.Set;

/**
 * DAO methods for Request.
 */
public interface RequestDao extends Dao<RequestImpl>
{
    /**
     * Returns the set of requests for the given rental provider.
     * @param rentalProvider rental provider
     * @return requests
     */
    Set<RequestImpl> getRequestsForProvider(RentalProviderImpl rentalProvider);
}
