package com.appropel.schuss;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appropel.schuss.dagger.DaggerWrapper;
import com.appropel.schuss.databinding.RentalProviderBinding;
import com.appropel.schuss.model.read.RentalProvider;
import com.appropel.schuss.service.SchussService;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.databinding.BindableItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
{
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(MainActivity.class);

    @BindView(R.id.provider_view)
    RecyclerView providerView;

    @Inject
    SchussService service;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        ButterKnife.bind(this);

        final GroupAdapter adapter = new GroupAdapter();
        providerView.setAdapter(adapter);
        providerView.setLayoutManager(new LinearLayoutManager(this));

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
                            adapter.add(new RentalProviderItem(provider));
                        }
                    }

                    @Override
                    public void onFailure(final Call<List<RentalProvider>> call, final Throwable t)
                    {
                        LOGGER.error("Efic fail", t);
                    }
                });

    }

    public class RentalProviderItem extends BindableItem<RentalProviderBinding>
    {
        private RentalProvider rentalProvider;

        public RentalProviderItem(RentalProvider rentalProvider) {
            this.rentalProvider = rentalProvider;
        }

        @Override public void bind(RentalProviderBinding binding, int position) {
            binding.setRentalProvider(rentalProvider);
        }

        @Override public int getLayout() {
            return R.layout.rental_provider;
        }
    }
}
