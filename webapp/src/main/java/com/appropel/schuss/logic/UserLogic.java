package com.appropel.schuss.logic;

/**
 * Business methods for managing Users.
 */
public interface UserLogic
{
    /**
     * Creates a new user.
     * @param emailAddress e-mail address
     * @param password encrypted password
     * @param advertisingId Google advertising identifier
     */
    void createUser(final String emailAddress, final String password, final String advertisingId);
}
