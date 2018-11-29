package com.appropel.schuss.controller;

import com.appropel.schuss.model.read.Person;
import com.appropel.schuss.model.read.Profile;
import com.appropel.schuss.model.read.Request;

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
    void signIn(String emailAddress, String password, boolean newAccount);

    /**
     * Creates or updates a person.
     * @param person person
     */
    void updatePerson(Person person);

    /**
     * Fetches the current User.
     */
    void getUser();

    /**
     * Updates a Profile.
     * @param person person
     * @param profile profile
     */
    void updateProfile(Person person, Profile profile);

    /**
     * Fetches the list of rental providers.
     */
    void getRentalProviders();

    /**
     * Creates a new rental request.
     * @param request request
     */
    void createRequest(Request request);

    /**
     * Fetches requests for the current user/rental provider.
     */
    void getRequests();

    /**
     * Updates the status of a particular request.
     * @param requestId request ID
     * @param status status
     */
    void updateRequest(long requestId, Request.Status status);
}
