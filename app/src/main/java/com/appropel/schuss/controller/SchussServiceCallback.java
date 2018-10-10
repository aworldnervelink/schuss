package com.appropel.schuss.controller;

import com.appropel.schuss.common.util.EventBusFacade;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Schuss app server request callback.
 *
 * {@link #onRequestSuccess(Object)} is called if server response is successful.
 *
 * {@link VxAppServerRequestFailure} is posted if server response is received but it's not successful
 * (e.g. status code is 500)
 *
 * {@link VxAppServerCommunicationError} is posted if cannot receive server response (no connection,
 * unexpected response format etc)
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
     * @param responseBody response body
     */
    abstract void onRequestSuccess(T responseBody);

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
//            try
//            {
//                final String responseBody = response.errorBody().string();
//                final VxServiceError error = parseServerErrorResponse(responseBody);
//                eventBus.post(new VxAppServerRequestFailure(error));
//            }
//            catch (IOException ex)
//            {
//                LOGGER.error("Cannot parse VX app server error response", ex);
//                eventBus.post(new VxAppServerCommunicationError<T>(call, this));
//            }
        }
    }

    @Override
    public void onFailure(final Call<T> call, final Throwable throwable)
    {
        LOGGER.error("Cannot connect to VX app server", throwable);
//        eventBus.post(new VxAppServerCommunicationError<T>(call, this));
    }

    /**
     * Parses server error response.
     *
     * @param responseBody response body
     * @return error object
     * @throws IOException if cannot parse the response
     */
//    private VxServiceError parseServerErrorResponse(final String responseBody) throws IOException
//    {
//        try
//        {
//            // first trying to parse the response as a known error
//
//            final VxServiceError error = objectMapper.readValue(responseBody,
//                                                                VxServiceError.class);
//            LOGGER.warn("VX server request failed: " + error.toString());
//            return error;
//        }
//        catch (JsonMappingException ex)
//        {
//            // if the response is not a known error it may be an internal server error
//
//            final InternalErrorResponse error = objectMapper.readValue(responseBody,
//                                                                       InternalErrorResponse.class);
//            LOGGER.warn("VX server request failed: " + error.message);
//            return VxServiceError.INTERNAL_SERVER_ERROR;
//        }
//    }

    /** ========== Inner Classes. ========== */

    /**
     * Response sent by server in case of an internal error.
     */
//    @JsonIgnoreProperties(ignoreUnknown = true)
//    public static class InternalErrorResponse
//    {
//        /** Message. */
//        public String message;
//    }
}
