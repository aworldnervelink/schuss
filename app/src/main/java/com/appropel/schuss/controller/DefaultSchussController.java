package com.appropel.schuss.controller;

import com.appropel.schuss.common.util.ContextUtils;
import com.appropel.schuss.common.util.EventBusFacade;
import com.appropel.schuss.common.util.Preferences;
import com.appropel.schuss.service.SchussService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    /** Object mapper. */
    final ObjectMapper objectMapper;

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
     * @param objectMapper   object mapper
     */
    public DefaultSchussController(final EventBusFacade eventBus,
                                   final ContextUtils contextUtils,
                                   final Preferences preferences,
                                   final SchussService service,
                                   final ObjectMapper objectMapper)
    {
        this.eventBus = eventBus;
        this.contextUtils = contextUtils;
        this.preferences = preferences;
        this.service = service;
        this.objectMapper = objectMapper;
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
                .enqueue(new SchussServiceCallback<Void>(eventBus, objectMapper)
                {
                    @Override
                    void onRequestSuccess(final Void responseBody)
                    {
                        // TODO
                    }
                });
    }
}
