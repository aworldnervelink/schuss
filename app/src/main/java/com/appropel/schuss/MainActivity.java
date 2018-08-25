package com.appropel.schuss;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.appropel.schuss.dagger.DaggerWrapper;
import com.appropel.schuss.model.read.RentalProvider;
import com.appropel.schuss.service.SchussService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MainActivity.class);

    @Inject
    SchussService service;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DaggerWrapper.INSTANCE.getComponent().inject(this);
        service.getRentalProviders()
                .enqueue(new Callback<List<RentalProvider>>()
                {
                    @Override
                    public void onResponse(final Call<List<RentalProvider>> call, final Response<List<RentalProvider>> response)
                    {
                        for (RentalProvider provider : response.body())
                        {
                            LOGGER.info(provider.getName());
                        }
                    }

                    @Override
                    public void onFailure(final Call<List<RentalProvider>> call, final Throwable t)
                    {
                        LOGGER.error("Efic fail", t);
                    }
                });

    }
}
