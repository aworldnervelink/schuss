package com.appropel.schuss.controller;

import com.appropel.schuss.model.read.Person;
import com.appropel.schuss.model.read.Profile;

/**
 * Interface defining the Controller layer.
 */
public interface SchussController
{
    /**
     * Request advertising ID. Posts the result as
     * {@link com.appropel.schuss.controller.event.AdvertisingIdChangeEvent}
     */
    void requestAdvertisingId();

    /**
     * Registers a new user of the application or logs in an existing user.
     * @param emailAddress e-mail address
     * @param password password
     * @param newAccount new account flag
     */
    void signIn(String emailAddress, String password, final boolean newAccount);

    /**
     * Creates or updates a person.
     * @param person person
     */
    void updatePerson(final Person person);

    /**
     * Fetches the current User.
     */
    void getUser();

    /**
     * Updates a Profile.
     * @param person person
     * @param profile profile
     */
    void updateProfile(final Person person, final Profile profile);

    /**
     * Fetches the list of rental providers.
     */
    void getRentalProviders();
}
