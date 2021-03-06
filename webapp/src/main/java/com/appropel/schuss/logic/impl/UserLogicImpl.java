package com.appropel.schuss.logic.impl;

import com.appropel.schuss.dao.UserDao;
import com.appropel.schuss.logic.ServiceError;
import com.appropel.schuss.logic.ServiceException;
import com.appropel.schuss.logic.UserLogic;
import com.appropel.schuss.model.impl.DeviceImpl;
import com.appropel.schuss.model.impl.PersonImpl;
import com.appropel.schuss.model.impl.UserImpl;
import com.appropel.schuss.security.JwtTokenService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;

import at.favre.lib.crypto.bcrypt.BCrypt;

/**
 * Implementation of user logic.
 */
@Transactional
@Component
@SuppressWarnings({"checkstyle:DesignForExtension", "PMD.GodClass"})  // Cannot be final for AOP enhancement
public class UserLogicImpl implements UserLogic
{
    /** UTF-8 character set. */
    private static final Charset UTF8 = Charset.forName("UTF-8");

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserLogicImpl.class);

    /** User DAO. */
    private UserDao userDao;

//    /** Address DAO. */
//    private AddressDao addressDao;
//
    /** JWT token service. */
    private JwtTokenService tokenService;

    @Autowired
    public void setUserDao(final UserDao userDao)
    {
        this.userDao = userDao;
    }

//    @Autowired
//    public void setAddressDao(final AddressDao addressDao)
//    {
//        this.addressDao = addressDao;
//    }

    @Autowired
    public void setTokenService(final JwtTokenService tokenService)
    {
        this.tokenService = tokenService;
    }

    @Override
    public UserImpl signIn(final String emailAddress,
                       final String password,
                       final String advertisingId,
                       final boolean newAccount)
    {
        final UserImpl existingUser = userDao.findUser(emailAddress);
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
                user.setToken(tokenService.getToken(user.getEmail(), advertisingId));
                return user;
            }
            else
            {
                LOGGER.info("User {} already exists, unable to create", emailAddress);
                throw new ServiceException(ServiceError.USERNAME_IS_NOT_AVAILABLE);
            }
        }
        else
        {
            if (existingUser != null)
            {
                if (BCrypt.verifyer().verify(
                        password.getBytes(UTF8), existingUser.getPassword().getBytes(UTF8)).verified)
                {
                    LOGGER.info("Logged in user {}", emailAddress);
                    existingUser.setToken(tokenService.getToken(existingUser.getEmail(), advertisingId));
                    return existingUser;

                }
                else
                {
                    LOGGER.info("Wrong password for {}", emailAddress);
                    throw new ServiceException(ServiceError.AUTHENTICATION_FAILED);
                }
            }
            else
            {
                LOGGER.info("User {} does not exist, cannot login", emailAddress);
                throw new ServiceException(ServiceError.AUTHENTICATION_FAILED);
            }
        }
    }

    @Override
    public void updatePerson(final UserImpl user, final PersonImpl person)
    {
        if (person.getId() == 0)
        {
            // This is a new Person.
//            final AddressImpl newAddress = new AddressImpl();
//            // TODO: could be a re-used address, so handle that case also
//            BeanUtils.copyProperties(person.getAddress(), newAddress);
//            addressDao.add(newAddress);

            user.addPerson(person);
            LOGGER.info("Added a new person to {}", user.getEmail());
        }
        // TODO: implement else/update case
    }
}
