package com.appropel.schuss.dao.impl;

import com.appropel.schuss.dao.RentalProviderDao;
import com.appropel.schuss.model.impl.RentalProviderImpl;
import com.appropel.schuss.model.read.RentalProvider;
import com.google.common.collect.ImmutableList;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.jdo.JDOQLTypedQuery;
import javax.jdo.PersistenceManager;

/**
 * Implementation of rental provider DAO.
 */
@Transactional
@Repository
@SuppressWarnings("checkstyle:DesignForExtension")
// Cannot be final for AOP enhancement
public class RentalProviderDaoImpl extends DaoBase<RentalProviderImpl> implements RentalProviderDao
{
    @Override
    public List<RentalProvider> getRentalProviders()
    {
        final PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
        final JDOQLTypedQuery<RentalProviderImpl> query = pm.newJDOQLTypedQuery(RentalProviderImpl.class);
        return ImmutableList.copyOf(query.executeList());
    }
}
