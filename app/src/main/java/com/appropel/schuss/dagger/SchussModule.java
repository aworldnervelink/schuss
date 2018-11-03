package com.appropel.schuss.dagger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;
import android.text.TextUtils;

import com.appropel.schuss.BuildConfig;
import com.appropel.schuss.common.util.ContextUtils;
import com.appropel.schuss.common.util.EventBusFacade;
import com.appropel.schuss.common.util.EventBusWrapper;
import com.appropel.schuss.common.util.Preferences;
import com.appropel.schuss.controller.UserInterface;
import com.appropel.schuss.controller.DefaultSchussController;
import com.appropel.schuss.controller.SchussController;
import com.appropel.schuss.rest.ProtocolHeaders;
import com.appropel.schuss.service.SchussService;
import com.appropel.schuss.view.fragment.DefaultUserInterface;
import com.appropel.schuss.view.util.DefaultContextUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.orange_box.storebox.StoreBox;

import org.apache.commons.lang3.StringUtils;
import org.greenrobot.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Module for Dagger injection.
 */
@Module
public final class SchussModule
{
    /** Logger. */
    static final Logger LOGGER = LoggerFactory.getLogger(SchussModule.class);

    /** Application context. */
    @NonNull
    private final Context appContext;

    /**
     * Parameterized constructor.
     *
     * @param appContext application context.
     */
    SchussModule(@NonNull final Context appContext)
    {
        this.appContext = checkNotNull(appContext);
    }

    /**
     * Provides the Android application Context.
     *
     * @return context.
     */
    @Provides
    @Singleton
    public Context provideContext()
    {
        return appContext;
    }

    /**
     * Provides the event bus.
     *
     * @return event bus.
     */
    @Provides
    @Singleton
    public EventBusFacade provideEventBus()
    {
        return new EventBusWrapper(new EventBus());
    }

    /**
     * Provides predefined Jackson Object Mapper.
     *
     * @return Jackson Object Mapper.
     */
    @Provides
    @Singleton
    public ObjectMapper provideObjectMapper()
    {
        return new ObjectMapper();
    }

    /**
     * Provides a preferences interface which is backed by the StoreBox library.
     * See: https://github.com/martino2k6/StoreBox .
     *
     * @return preferences interface.
     */
    @Provides
    @Singleton
    @VisibleForTesting
    public Preferences providePreferences()
    {
        return StoreBox.create(appContext, Preferences.class);
    }

    /**
     * Provides Android Context utilities.
     *
     * @return context utilities
     */
    @Provides
    @Singleton
    public ContextUtils provideContextUtils()
    {
        return new DefaultContextUtils(appContext);
    }

    /**
     * Provides the remote interface to the app server.
     *
     * @param objectMapper Jackson ObjectMapper
     * @param preferences  preferences
     * @return Retrofit REST interface.
     */
    @Provides
    @Singleton
    public SchussService provideRetrofitService(final ObjectMapper objectMapper, final Preferences preferences)
    {
        final OkHttpClient.Builder httpClientBuilder = new OkHttpClient()
                .newBuilder()
                .retryOnConnectionFailure(true)
                .connectTimeout(15, TimeUnit.SECONDS)
                .readTimeout(15, TimeUnit.SECONDS);

        final HttpLoggingInterceptor.Logger httpLogger = new HttpLoggingInterceptor.Logger()
        {
            @Override
            public void log(final String message)
            {
                LOGGER.debug("OkHttp: {}", message);
            }
        };

        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(httpLogger);
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClientBuilder.addInterceptor(httpLoggingInterceptor);
        httpClientBuilder.addNetworkInterceptor(new Interceptor()
        {
            @Override
            public Response intercept(final Chain chain) throws IOException
            {
                final Request.Builder builder = chain.request().newBuilder();
                if (preferences.getUserToken() != null)
                {
                    builder.addHeader(ProtocolHeaders.TOKEN.toString(), preferences.getUserToken());

                }
                if (preferences.getAdvertisingId() != null)
                {
                    builder.addHeader(ProtocolHeaders.ADVERTISING_ID.toString(), preferences.getAdvertisingId());
                }
                if (StringUtils.isNotBlank(preferences.getEmailAddress()))
                {
                    builder.addHeader(ProtocolHeaders.EMAIL_ADDRESS.toString(),
                            String.valueOf(preferences.getEmailAddress()));
                }
                return chain.proceed(builder.build());
            }
        });

        final String appServerUrl = BuildConfig.APP_SERVER_HOST;
        LOGGER.info("App server URL: {}", appServerUrl);
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(appServerUrl)
                .client(httpClientBuilder.build())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();

        return retrofit.create(SchussService.class);
    }

    /**
     * Provides the user interface/screen switcher.
     *
     * @param eventBus event bus.
     * @return event bus.
     */
    @Provides
    @Singleton
    public UserInterface provideUserInterface(final EventBusFacade eventBus)
    {
        return new DefaultUserInterface(eventBus);
    }

    /**
     * Provides the main Controller.
     *
     * @param eventBus       event bus.
     * @param contextUtils   context utils
     * @param preferences    preferences
     * @param service        remote service
     * @param objectMapper   object mapper
     * @param userInterface  user interface
     * @return controller.
     */
    @Provides
    @Singleton
    public SchussController provideController(final EventBusFacade eventBus,
                                              final ContextUtils contextUtils,
                                              final Preferences preferences,
                                              final SchussService service,
                                              final ObjectMapper objectMapper,
                                              final UserInterface userInterface)
    {
        return new DefaultSchussController(eventBus,
                                           contextUtils,
                                           preferences,
                                           service,
                                           objectMapper,
                                           userInterface);
    }
}
