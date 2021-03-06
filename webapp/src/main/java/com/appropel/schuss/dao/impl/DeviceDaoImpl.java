package com.appropel.schuss.dao.impl;

import com.appropel.schuss.dao.DeviceDao;
import com.appropel.schuss.model.impl.DeviceImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of Device DAO.
 */
@Transactional
@Repository
@SuppressWarnings("checkstyle:DesignForExtension")
// Cannot be final for AOP enhancement
public class DeviceDaoImpl extends DaoBase<DeviceImpl> implements DeviceDao
{
}
