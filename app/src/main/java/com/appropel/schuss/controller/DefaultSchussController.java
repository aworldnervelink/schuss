package com.appropel.schuss.controller;

import com.appropel.schuss.common.util.ContextUtils;
import com.appropel.schuss.common.util.EventBusFacade;
import com.appropel.schuss.common.util.Preferences;

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

    /** Executor service for background tasks. */
    private final ExecutorService executorService =
            Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    /**
     * Constructs a new {@code DefaultSchussController}.
     *
     * @param eventBus       event bus.
     * @param contextUtils   context utilities
     * @param preferences    preferences
     */
    public DefaultSchussController(final EventBusFacade eventBus,
                                   final ContextUtils contextUtils,
                                   final Preferences preferences)
    {
        this.eventBus = eventBus;
        this.contextUtils = contextUtils;
        this.preferences = preferences;
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
}
