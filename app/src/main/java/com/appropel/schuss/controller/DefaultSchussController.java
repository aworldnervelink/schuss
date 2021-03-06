package com.appropel.schuss.controller;

import com.appropel.schuss.common.util.ContextUtils;
import com.appropel.schuss.common.util.EventBusFacade;
import com.appropel.schuss.common.util.Preferences;
import com.appropel.schuss.controller.event.ProviderEvent;
import com.appropel.schuss.controller.event.RequestEvent;
import com.appropel.schuss.model.read.Person;
import com.appropel.schuss.model.read.Profile;
import com.appropel.schuss.model.read.RentalProvider;
import com.appropel.schuss.model.read.Request;
import com.appropel.schuss.model.read.User;
import com.appropel.schuss.service.SchussService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;
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
                .enqueue(new SchussServiceCallback<User>(eventBus, objectMapper)
                {
                    @Override
                    void onRequestSuccess(final User user)
                    {
                        preferences.setUserToken(user.getToken());
                        preferences.setEmailAddress(emailAddress);
                        try
                        {
                            preferences.setUser(objectMapper.writeValueAsString(user));
                        }
                        catch (JsonProcessingException e)
                        {
                            LOGGER.error("JSON exception", e);
                        }
                        switch (user.getRole())
                        {
                            case RENTER:
                                userInterface.showHomeScreen();
                                break;
                            case WORKER:
                                userInterface.showEmployeeHomeScreen();
                                break;
                            default:
                                LOGGER.error("Unknown role {}", user.getRole());
                                break;
                        }
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
    public void getUser()
    {
        service.getUser()
                .enqueue(new SchussServiceCallback<User>(eventBus, objectMapper)
                {
                    @Override
                    void onRequestSuccess(final User response)
                    {
                        try
                        {
                            preferences.setUser(objectMapper.writeValueAsString(response));
                        }
                        catch (JsonProcessingException e)
                        {
                            LOGGER.error("JSON exception", e);
                        }
                        eventBus.post(response);
                    }
                });
    }

    @Override
    public void updateProfile(final Person person, final Profile profile)
    {
        service.updateProfile(person.getId(), profile)
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
    public void getRentalProviders()
    {
        service.getRentalProviders()
                .enqueue(new SchussServiceCallback<List<RentalProvider>>(eventBus, objectMapper)
                {
                    @Override
                    void onRequestSuccess(final List<RentalProvider> response)
                    {
                        eventBus.post(ProviderEvent.of(response));
                    }
                });
    }

    @Override
    public void createRequest(final Request request)
    {
        service.createRequest(request)
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
    public void getRequests()
    {
        service.getRequests()
                .enqueue(new SchussServiceCallback<Set<Request>>(eventBus, objectMapper)
                {
                    @Override
                    void onRequestSuccess(final Set<Request> response)
                    {
                        eventBus.post(RequestEvent.of(response));
                    }
                });
    }

    @Override
    public void updateRequest(final long requestId, final Request.Status status)
    {
        service.updateRequest(requestId, status)
                .enqueue(new SchussServiceCallback<Set<Request>>(eventBus, objectMapper)
                {
                    @Override
                    void onRequestSuccess(final Set<Request> response)
                    {
                        eventBus.post(RequestEvent.of(response));
                    }
                });
    }
}
