package com.appropel.schuss.controller;

import com.appropel.schuss.common.util.ContextUtils;
import com.appropel.schuss.common.util.EventBusFacade;
import com.appropel.schuss.common.util.Preferences;
import com.appropel.schuss.controller.event.PersonEvent;
import com.appropel.schuss.model.read.Person;
import com.appropel.schuss.model.read.Profile;
import com.appropel.schuss.service.SchussService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
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
    final EventBusFacade eventBus;

    /** Context utilities. */
    final ContextUtils contextUtils;

    /** Preferences. */
    final Preferences preferences;

    /** Remote service. */
    final SchussService service;

    /** Object mapper. */
    final ObjectMapper objectMapper;

    /** User interface. */
    final UserInterface userInterface;

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
     * @param userInterface  user interface
     */
    public DefaultSchussController(final EventBusFacade eventBus,
                                   final ContextUtils contextUtils,
                                   final Preferences preferences,
                                   final SchussService service,
                                   final ObjectMapper objectMapper,
                                   final UserInterface userInterface)
    {
        this.eventBus = eventBus;
        this.contextUtils = contextUtils;
        this.preferences = preferences;
        this.service = service;
        this.objectMapper = objectMapper;
        this.userInterface = userInterface;
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
    public void signIn(final String emailAddress, final String password, final boolean newAccount)
    {
        service.signIn(emailAddress,
                       password,
                       preferences.getAdvertisingId(),
                       newAccount)
                .enqueue(new SchussServiceCallback<String>(eventBus, objectMapper)
                {
                    @Override
                    void onRequestSuccess(final String response)
                    {
                        preferences.setUserToken(response);
                        preferences.setEmailAddress(emailAddress);
                        userInterface.showHomeScreen();
                    }
                });
    }

    @Override
    public void updatePerson(final Person person)
    {
        service.updatePerson(person)
                .enqueue(new SchussServiceCallback<Void>(eventBus, objectMapper)
                {
                    @Override
                    void onRequestSuccess(final Void response)
                    {
                        userInterface.showHomeScreen();
                    }
                });
    }

    @Override
    public void getPersons()
    {
        service.getPersons()
                .enqueue(new SchussServiceCallback<List<Person>>(eventBus, objectMapper)
                {
                    @Override
                    void onRequestSuccess(final List<Person> response)
                    {
                        eventBus.post(PersonEvent.of(response));
                    }
                });
    }

    @Override
    public void updateProfile(final Person person, final Profile profile)
    {
        // TODO.
    }
}
