package com.appropel.schuss.controller;

import com.appropel.schuss.common.util.EventBusFacade;
import com.appropel.schuss.controller.event.AppServerRequestFailure;
import com.appropel.schuss.logic.ServiceError;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.appropel.schuss.logic.ServiceError.COMMUNICATION_ERROR;
import static com.appropel.schuss.logic.ServiceError.INTERNAL_SERVER_ERROR;

/**
 * Schuss app server request callback.
 *
 * {@link #onRequestSuccess(Object)} is called if server response is successful.
 *
 * {@link AppServerRequestFailure} is posted if server response is received but it's not successful
 * (e.g. status code is 500)
 *
 * @param <T> response type
 */
abstract class SchussServiceCallback<T> implements Callback<T>
{
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SchussServiceCallback.class);

    /** Event bus. */
    private final EventBusFacade eventBus;  // NOPMD: TODO

    /** Jakson Object Mapper. */
    private final ObjectMapper objectMapper;  // NOPMD: TODO

    /**
     * Constructs a new {@code SchussServiceCallback}.
     *
     * @param eventBus eventBus.
     * @param objectMapper objectMapper.
     */
    SchussServiceCallback(final EventBusFacade eventBus, final ObjectMapper objectMapper)
    {
        this.eventBus = eventBus;
        this.objectMapper = objectMapper;
    }

    /**
     * Method to be called in case of request success.
     *
     * @param response response body
     */
    abstract void onRequestSuccess(T response);

    @Override
    public void onResponse(final Call<T> call, final Response<T> response)
    {
        if (response.isSuccessful())
        {
            onRequestSuccess(response.body());
        }
        else
        {
            LOGGER.error("Server request failed with status " + response.code());
            try
            {
                final String responseBody = response.errorBody().string();
                final ServiceError error = parseServerErrorResponse(responseBody);
                eventBus.post(new AppServerRequestFailure(error));
            }
            catch (IOException ex)
            {
                LOGGER.error("Cannot parse app server error response", ex);
                eventBus.post(new AppServerRequestFailure(COMMUNICATION_ERROR));
            }
        }
    }

    @Override
    public void onFailure(final Call<T> call, final Throwable throwable)
    {
        LOGGER.error("Cannot connect to app server", throwable);
        eventBus.post(new AppServerRequestFailure(COMMUNICATION_ERROR));
    }

    /**
     * Parses server error response.
     *
     * @param responseBody response body
     * @return error object
     * @throws IOException if cannot parse the response
     */
    private ServiceError parseServerErrorResponse(final String responseBody) throws IOException
    {
        try
        {
            // first trying to parse the response as a known error

            final ServiceError error = objectMapper.readValue(responseBody, ServiceError.class);
            LOGGER.warn("App server request failed: " + error.toString());
            return error;
        }
        catch (JsonMappingException ex)
        {
            // if the response is not a known error it may be an internal server error

            final InternalErrorResponse error = objectMapper.readValue(responseBody,
                                                                       InternalErrorResponse.class);
            LOGGER.error("App server request failed: " + error.message);
            return INTERNAL_SERVER_ERROR;
        }
    }

    /** ========== Inner Classes. ========== */

    /**
     * Response sent by server in case of an internal error.
     */
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class InternalErrorResponse
    {
        /** Message. */
        public String message;
    }
}
