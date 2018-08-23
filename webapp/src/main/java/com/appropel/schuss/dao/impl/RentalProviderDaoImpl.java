package com.appropel.schuss.dao.impl;

import com.appropel.schuss.dao.RentalProviderDao;
import com.appropel.schuss.model.impl.RentalProviderImpl;
import com.appropel.schuss.model.read.RentalProvider;
import com.google.common.collect.ImmutableList;

import java.util.List;

import javax.jdo.JDOQLTypedQuery;
import javax.jdo.PersistenceManager;

public final class RentalProviderDaoImpl extends DaoBase<RentalProviderImpl> implements RentalProviderDao
{
    @Override
    public List<RentalProvider> getRentalProviders()
    {
        final PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
        final JDOQLTypedQuery<RentalProviderImpl> query = pm.newJDOQLTypedQuery(RentalProviderImpl.class);
        return ImmutableList.copyOf(query.executeList());
    }
}
