package com.appropel.schuss.dao;

import com.appropel.schuss.model.read.User;

/**
 * DAO methods for User.
 */
public interface UserDao extends Dao<User>
{
    /**
     * Returns the user with the given email address, or null if no such user exists.
     * @param emailAddress user's e-mail address
     * @return User or null
     */
    User findUser(String emailAddress);
}
