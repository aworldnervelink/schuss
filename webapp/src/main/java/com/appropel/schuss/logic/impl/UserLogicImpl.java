package com.appropel.schuss.logic.impl;

import com.appropel.schuss.dao.UserDao;
import com.appropel.schuss.logic.UserLogic;
import com.appropel.schuss.model.impl.DeviceImpl;
import com.appropel.schuss.model.impl.UserImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import at.favre.lib.crypto.bcrypt.BCrypt;

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

    @Autowired
    public void setUserDao(final UserDao userDao)
    {
        this.userDao = userDao;
    }

    @Override
    public void createUser(final String emailAddress, final String password, final String advertisingId)
    {
        final String encryptedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
        final UserImpl user = new UserImpl(emailAddress, encryptedPassword);
        final DeviceImpl device = new DeviceImpl(advertisingId);
        user.addDevice(device);
        userDao.add(user);
    }
}
