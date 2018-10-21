package com.appropel.schuss.service;

import com.appropel.schuss.model.read.Person;
import com.appropel.schuss.model.read.RentalProvider;
import com.appropel.schuss.rest.RentalProviderController;
import com.appropel.schuss.rest.UserController;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Schuss REST API Retrofit service.
 */
public interface SchussService
{
    /**
     * Returns a list of rental providers.
     * @return list of providers
     */
    @GET(RentalProviderController.RENTAL_PROVIDER_PATH)
    Call<List<RentalProvider>> getRentalProviders();

    /**
     * Creates new user or logs in existing user.
     *
     * @param email           e-mail address
     * @param password        hashed password
     * @param advertisingId   advertising ID (unique, user-resettable ID, provided by Google Play services)
     * @param newAccount      flag indicating a new account
     * @return JWT if successful
     */
    @POST(UserController.USER_PATH + UserController.SIGN_IN_METHOD)
    Call<String> signIn(@Query(UserController.EMAIL_PARAM) String email,
                        @Query(UserController.PASSWORD_PARAM) String password,
                        @Query(UserController.ADVERTISING_ID_PARAM) String advertisingId,
                        @Query(UserController.NEW_ACCOUNT_PARAM) boolean newAccount);

    /**
     * Creates or updates a Person attached to the given User.
     * @param person person
     * @return void
     */
    @POST(UserController.USER_PATH + UserController.UPDATE_PERSON_METHOD)
    Call<Void> updatePerson(@Body Person person);
}
