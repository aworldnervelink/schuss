package com.appropel.schuss.dao;

import com.appropel.schuss.model.impl.UserImpl;

/**
 * DAO methods for User.
 */
public interface UserDao extends Dao<UserImpl>
{
    /**
     * Returns the user with the given email address, or null if no such user exists.
     * @param emailAddress user's e-mail address
     * @return User or null
     */
    UserImpl findUser(String emailAddress);
}
