package com.appropel.schuss.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.appropel.schuss.R;
import com.appropel.schuss.dagger.DaggerWrapper;
import com.appropel.schuss.dagger.SchussModule;
import com.appropel.schuss.databinding.RentalProviderBinding;
import com.appropel.schuss.model.read.RentalProvider;
import com.appropel.schuss.service.SchussService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.databinding.BindableItem;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Main activity.
 */
public final class MainActivity extends AppCompatActivity
{
    /** Logger. */
    static final Logger LOGGER = LoggerFactory.getLogger(MainActivity.class);

    /** View that holds a list of rental providers. */
    @BindView(R.id.provider_view)
    RecyclerView providerView;

    /** Remote service. */
    @Inject
    SchussService service;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
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
                    public void onResponse(final Call<List<RentalProvider>> call,
                                           final Response<List<RentalProvider>> response)
                    {
                        for (RentalProvider provider : response.body())
                        {
                            LOGGER.info(provider.getName());
                            adapter.add(new RentalProviderItem(provider));
                        }
                    }

                    @Override
                    public void onFailure(final Call<List<RentalProvider>> call, final Throwable th)
                    {
                        LOGGER.error("Efic fail", th);
                    }
                });

    }

    /**
     * Individual rental provider item. TODO: refactor me out
     */
    public final class RentalProviderItem extends BindableItem<RentalProviderBinding>
    {
        /** Rental provider. */
        private final RentalProvider rentalProvider;

        /**
         * Constructs a new {@code RentalProviderItem}.
         * @param rentalProvider rental provider that is wrapped
         */
        public RentalProviderItem(final RentalProvider rentalProvider)
        {
            super();
            this.rentalProvider = rentalProvider;
        }

        @Override public void bind(final RentalProviderBinding binding, final int position)
        {
            binding.setRentalProvider(rentalProvider);

            MultiTransformation multi = new MultiTransformation(
                    new CenterCrop(),
                    new ContrastFilterTransformation(0.5f),
                    new BrightnessFilterTransformation(0.5f));

            Glide.with(MainActivity.this)
                    .load(SchussModule.APP_SERVER_URL + "images/" + rentalProvider.getBackgroundUrl())
                    .apply(bitmapTransform(multi))
                    .into(binding.backgroundView);
            Glide.with(MainActivity.this)
                    .load(SchussModule.APP_SERVER_URL + "images/" + rentalProvider.getLogoUrl())
                    .into(binding.logoView);
        }

        @Override public int getLayout()
        {
            return R.layout.rental_provider;
        }
    }
}
