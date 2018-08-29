package com.appropel.schuss.service;

import com.appropel.schuss.model.read.RentalProvider;
import com.appropel.schuss.rest.RentalProviderController;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

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
}
