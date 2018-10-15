package com.appropel.schuss.logic;

/** Server-side errors. */
public enum ServiceError
{
    /** Username is not available (the database already contains a user with this username). */
    USERNAME_IS_NOT_AVAILABLE,
    /** Authentication failed (the database does not contain users with matching username and password). */
    AUTHENTICATION_FAILED,
    /** Internal server error. */
    INTERNAL_SERVER_ERROR,
    /** Communication error. */
    COMMUNICATION_ERROR
}
