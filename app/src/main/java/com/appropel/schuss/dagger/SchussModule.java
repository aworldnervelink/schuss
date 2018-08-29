package com.appropel.schuss.dagger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.VisibleForTesting;

import com.appropel.schuss.common.util.Preferences;
import com.appropel.schuss.service.SchussService;
import com.fasterxml.jackson.databind.ObjectMapper;

import net.orange_box.storebox.StoreBox;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
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

    /** URL to app server. */
    // TODO: configurable
    public static final String APP_SERVER_URL = "http://192.168.1.4:8080/";

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
     * Provides the remote interface to the app server.
     *
     * @param objectMapper Jackson ObjectMapper
     * @return Retrofit REST interface.
     */
    @Provides
    @Singleton
    public SchussService provideRetrofitService(final ObjectMapper objectMapper)
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

        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APP_SERVER_URL)
                .client(httpClientBuilder.build())
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .build();

        return retrofit.create(SchussService.class);
    }
}
