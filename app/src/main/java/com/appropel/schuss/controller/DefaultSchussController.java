package com.appropel.schuss.controller;

import com.appropel.schuss.common.util.ContextUtils;
import com.appropel.schuss.common.util.EventBusFacade;
import com.appropel.schuss.common.util.Preferences;
import com.appropel.schuss.service.SchussService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Default implementation of the Controller.
 */
public final class DefaultSchussController implements SchussController
{
    /** Logger. */
    static final Logger LOGGER = LoggerFactory.getLogger(DefaultSchussController.class);

    /** Event bus. */
    final EventBusFacade eventBus;  // NOPMD: to be used

    /** Context utilities. */
    final ContextUtils contextUtils;

    /** Preferences. */
    final Preferences preferences;

    /** Remote service. */
    final SchussService service;

    /** Executor service for background tasks. */
    private final ExecutorService executorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * Constructs a new {@code DefaultSchussController}.
     *
     * @param eventBus       event bus.
     * @param contextUtils   context utilities
     * @param preferences    preferences
     * @param service        remote service
     */
    public DefaultSchussController(final EventBusFacade eventBus,
                                   final ContextUtils contextUtils,
                                   final Preferences preferences,
                                   final SchussService service)
    {
        this.eventBus = eventBus;
        this.contextUtils = contextUtils;
        this.preferences = preferences;
        this.service = service;
    }

    @Override
    public void requestAdvertisingId()
    {
        executorService.submit(new Runnable()
        {
            @Override
            public void run()
            {
                final String advertisingId = contextUtils.getAdvertisingId();
                preferences.setAdvertisingId(advertisingId);
                LOGGER.info("Advertising ID set to {}", advertisingId);
            }
        });
    }

    @Override
    public void register(final String emailAddress, final String password)
    {
        service.createUser(emailAddress,
                            password,
                            preferences.getAdvertisingId())
                .enqueue(new Callback<Void>()
                {
                    @Override
                    public void onResponse(final Call<Void> call, final Response<Void> response)
                    {
                        // TODO
                    }

                    @Override
                    public void onFailure(final Call<Void> call, final Throwable th)
                    {
                        // TODO
                    }
                });
    }
}
