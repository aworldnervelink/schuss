package com.appropel.schuss.logic.impl;

import com.appropel.schuss.dao.DeviceDao;
import com.appropel.schuss.dao.UserDao;
import com.appropel.schuss.logic.UserLogic;
import com.appropel.schuss.model.impl.DeviceImpl;
import com.appropel.schuss.model.impl.UserImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of user logic.
 */
@Transactional
@Component
@SuppressWarnings({"checkstyle:DesignForExtension", "PMD.GodClass"})  // Cannot be final for AOP enhancement
public class UserLogicImpl implements UserLogic
{
    /** User DAO. */
    private UserDao userDao;

    /** Device DAO. */
    private DeviceDao deviceDao;

    @Autowired
    public void setUserDao(final UserDao userDao)
    {
        this.userDao = userDao;
    }

    @Autowired
    public void setDeviceDao(final DeviceDao deviceDao)
    {
        this.deviceDao = deviceDao;
    }

    @Override
    public void createUser(final String emailAddress, final String advertisingId)
    {
        final DeviceImpl device = new DeviceImpl(advertisingId);
        deviceDao.add(device);

        final UserImpl user = new UserImpl(emailAddress);
        user.addDevice(device);
        userDao.add(user);
    }
}
