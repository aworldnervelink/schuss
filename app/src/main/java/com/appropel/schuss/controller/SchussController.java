package com.appropel.schuss.controller;

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
     * Registers a new user of the application.
     * @param emailAddress e-mail address
     * @param password password
     */
    void register(String emailAddress, String password);
}
