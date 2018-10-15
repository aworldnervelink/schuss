package com.appropel.schuss.logic;

/**
 * Business methods for managing Users.
 */
public interface UserLogic
{
    /**
     * Signs the user in. If the email address and password are correct, the user is logged in. If the
     * information is new, a new user is created. Otherwise an error occurs.
     * @param emailAddress e-mail address
     * @param password encrypted password
     * @param advertisingId Google advertising identifier
     */
    void signIn(final String emailAddress, final String password, final String advertisingId);
}
