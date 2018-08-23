package com.appropel.schuss.dao.impl;

import com.appropel.schuss.dao.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.GenericTypeResolver;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

import javax.jdo.PersistenceManager;
import javax.jdo.PersistenceManagerFactory;
import javax.jdo.identity.LongIdentity;

/**
 * Base class for DAOs that use a PersistenceManager.
 *
 * @param <T> type of the persistent entity
 */
@Transactional
@SuppressWarnings("checkstyle:DesignForExtension")  // Cannot be final for AOP enhancement
public abstract class DaoBase<T> implements Dao<T>
{
    /** Persistence manager factory. */
    protected PersistenceManagerFactory persistenceManagerFactory;

    @Autowired
    @Qualifier("pmfProxy")
    public void setPersistenceManagerFactory(final PersistenceManagerFactory pmf)
    {
        this.persistenceManagerFactory = pmf;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T getById(final long id)
    {
        if (id < 1)
        {
            return null;    // See VX-261... a zero/negative ID is meaningless
        }
        final Class<T> genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), DaoBase.class);
        final PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
        return (T) pm.getObjectById(new LongIdentity(genericType, id));
    }

    @Override
    public T add(final T object)
    {
        final PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
        return pm.makePersistent(object);
    }

    @Override
    public void deleteAll(final Collection objects)
    {
        final PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
        pm.deletePersistentAll(objects);
    }

    @Override
    public void deleteObject(final Object object)
    {
        final PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
        pm.deletePersistent(object);
    }
}
