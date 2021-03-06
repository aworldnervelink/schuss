package com.appropel.schuss.service;

import com.appropel.schuss.model.read.Person;
import com.appropel.schuss.model.read.Profile;
import com.appropel.schuss.model.read.RentalProvider;
import com.appropel.schuss.model.read.Request;
import com.appropel.schuss.model.read.User;
import com.appropel.schuss.rest.PersonController;
import com.appropel.schuss.rest.RentalProviderController;
import com.appropel.schuss.rest.RequestController;
import com.appropel.schuss.rest.UserController;

import java.util.List;
import java.util.Set;

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
    Call<User> signIn(@Query(UserController.EMAIL_PARAM) String email,
                      @Query(UserController.PASSWORD_PARAM) String password,
                      @Query(UserController.ADVERTISING_ID_PARAM) String advertisingId,
                      @Query(UserController.NEW_ACCOUNT_PARAM) boolean newAccount);

    /**
     * Creates or updates a Person attached to the given User.
     * @param person person
     * @return void
     */
    @POST(PersonController.PERSON_PATH + PersonController.UPDATE_PERSON_METHOD)
    Call<Void> updatePerson(@Body Person person);

    /**
     * Returns the Persons attached to the current User.
     * @return list of person
     */
    @GET(PersonController.PERSON_PATH + PersonController.GET_PERSONS_METHOD)
    Call<List<Person>> getPersons();

    /**
     * Returns full info about the current User.
     * @return User
     */
    @GET(UserController.USER_PATH + UserController.GET_USER_METHOD)
    Call<User> getUser();

    /**
     * Creates or updates a Profile attached to the given Person.
     * @param personId person ID
     * @param profile profile
     * @return void
     */
    @POST(PersonController.PERSON_PATH + PersonController.UPDATE_PROFILE_METHOD)
    Call<Void> updateProfile(@Query(PersonController.PERSON_ID_PARAM) long personId,
                             @Body Profile profile);

    /**
     * Creates a new rental request.
     * @param request request
     * @return void
     */
    @POST(RequestController.REQUEST_PATH + RequestController.CREATE_METHOD)
    Call<Void> createRequest(@Body Request request);

    /**
     * Returns the requests for the current user.
     * @return rental requests
     */
    @GET(RequestController.REQUEST_PATH + RequestController.GET_METHOD)
    Call<Set<Request>> getRequests();

    /**
     * Updates the status of a particular request.
     * @param requestId request ID
     * @param status sstatus
     * @return rental requests
     */
    @POST(RequestController.REQUEST_PATH + RequestController.UPDATE_METHOD)
    Call<Set<Request>> updateRequest(@Query(RequestController.REQUEST_ID_PARAM) long requestId,
                                     @Query(RequestController.STATUS_PARAM) Request.Status status);
}
