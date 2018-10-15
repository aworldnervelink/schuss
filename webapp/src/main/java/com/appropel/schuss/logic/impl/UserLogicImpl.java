package com.appropel.schuss.logic.impl;

import com.appropel.schuss.dao.UserDao;
import com.appropel.schuss.logic.UserLogic;
import com.appropel.schuss.model.impl.DeviceImpl;
import com.appropel.schuss.model.impl.UserImpl;
import com.appropel.schuss.model.read.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserLogicImpl.class);

    /** User DAO. */
    private UserDao userDao;

    @Autowired
    public void setUserDao(final UserDao userDao)
    {
        this.userDao = userDao;
    }

    @Override
    public void signIn(final String emailAddress,
                       final String password,
                       final String advertisingId,
                       final boolean newAccount)
    {
        final User existingUser = userDao.findUser(emailAddress);
        if (newAccount)
        {
            if (existingUser == null)
            {
                // Create a new user
                final String encryptedPassword = BCrypt.withDefaults().hashToString(12, password.toCharArray());
                final UserImpl user = new UserImpl(emailAddress, encryptedPassword);
                final DeviceImpl device = new DeviceImpl(advertisingId);
                user.addDevice(device);
                userDao.add(user);
                LOGGER.info("Created new user {}.", emailAddress);
            }
            else
            {
                LOGGER.info("User {} already exists, unable to create", emailAddress);
                // TODO: asked to create but user exists. Return an error.
            }
        }
        else
        {
            if (existingUser != null)
            {
                if (BCrypt.verifyer().verify(password.getBytes(), existingUser.getPassword().getBytes()).verified)
                {
                    LOGGER.info("Logged in user {}", emailAddress);
                }
                else
                {
                    LOGGER.info("Wrong password for {}", emailAddress);
                }
            }
            else
            {
                LOGGER.info("User {} does not exist, cannot login", emailAddress);
                // TODO: logged in but user does not exist.
            }
        }
    }
}
