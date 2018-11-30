package com.appropel.schuss.rest;

import com.appropel.schuss.dao.UtilityDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Utility controller.
 */
@RestController
@Transactional
@SuppressWarnings("checkstyle:DesignForExtension")  // Cannot be final for AOP enhancement
public class UtilityController extends BaseController
{
    /** Utility path. */
    public static final String UTILITY_PATH = "/utility";

    /** Path for Datanucleus clear L2 cache functionality. */
    public static final String CLEAR_CACHE_PATH = "/clearCache";

    /**
     * Utility DAO.
     */
    private UtilityDao utilityDao;

    @Autowired
    public void setUtilityDao(final UtilityDao utilityDao)
    {
        this.utilityDao = utilityDao;
    }

    /**
     * Clear Datanucleus L2 cache.
     */
    @RequestMapping(value = UTILITY_PATH + CLEAR_CACHE_PATH, method = RequestMethod.GET)
    public void clearCache()
    {
        utilityDao.clearCache();
    }
}
