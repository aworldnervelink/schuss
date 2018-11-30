package com.appropel.schuss.dao.impl;

import com.appropel.schuss.dao.UtilityDao;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.jdo.datastore.DataStoreCache;

/**
 * Utility business methods.
 */
@Transactional
@Repository
@SuppressWarnings("checkstyle:DesignForExtension")  // Cannot be final for AOP enhancement
public class UtilityDaoImpl extends DaoBase implements UtilityDao
{
    @Override
    public void clearCache()
    {
        DataStoreCache dataStoreCache = persistenceManagerFactory.getDataStoreCache();
        dataStoreCache.evictAll();
    }
}
