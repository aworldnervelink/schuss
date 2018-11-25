package com.appropel.schuss.dao.impl;

import com.appropel.schuss.dao.RequestDao;
import com.appropel.schuss.model.impl.QRequestImpl;
import com.appropel.schuss.model.impl.RentalProviderImpl;
import com.appropel.schuss.model.impl.RequestImpl;
import com.google.common.collect.ImmutableSortedSet;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.TreeSet;

import javax.jdo.JDOQLTypedQuery;
import javax.jdo.PersistenceManager;

/**
 * Implementation of User DAO.
 */
@Transactional
@Repository
@SuppressWarnings("checkstyle:DesignForExtension")
// Cannot be final for AOP enhancement
public class RequestDaoImpl extends DaoBase<RequestImpl> implements RequestDao
{
    @Override
    public Set<RequestImpl> getRequestsForProvider(final RentalProviderImpl rentalProvider)
    {
        final PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
        final JDOQLTypedQuery<RequestImpl> query = pm.newJDOQLTypedQuery(RequestImpl.class);
        final QRequestImpl qRequest = QRequestImpl.candidate();
        final Set<RequestImpl> results = new TreeSet<>();
        results.addAll(query.filter(qRequest.rentalProvider.eq(rentalProvider)).executeList());
        return ImmutableSortedSet.copyOf(results);
    }
}
