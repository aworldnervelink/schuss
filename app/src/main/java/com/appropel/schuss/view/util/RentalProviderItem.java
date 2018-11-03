package com.appropel.schuss.view.util;

import android.app.Fragment;

import com.appropel.schuss.BuildConfig;
import com.appropel.schuss.R;
import com.appropel.schuss.dagger.SchussModule;
import com.appropel.schuss.databinding.RentalProviderBinding;
import com.appropel.schuss.model.read.RentalProvider;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.xwray.groupie.databinding.BindableItem;

import jp.wasabeef.glide.transformations.gpu.BrightnessFilterTransformation;
import jp.wasabeef.glide.transformations.gpu.ContrastFilterTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Item in a list of rental providers.
 */
public final class RentalProviderItem extends BindableItem<RentalProviderBinding>
{
    /** Rental provider. */
    private final RentalProvider rentalProvider;

    /** Fragment activity. */
    private final Fragment fragment;

    /**
     * Constructs a new {@code RentalProviderItem}.
     * @param rentalProvider rental provider that is wrapped
     * @param fragment fragment for using the Glide library
     */
    public RentalProviderItem(final RentalProvider rentalProvider, final Fragment fragment)
    {
        super();
        this.rentalProvider = rentalProvider;
        this.fragment = fragment;
    }

    @Override public void bind(final RentalProviderBinding binding, final int position)
    {
        binding.setRentalProvider(rentalProvider);

        MultiTransformation multi = new MultiTransformation(
                new CenterCrop(),
                new ContrastFilterTransformation(0.5f),
                new BrightnessFilterTransformation(0.5f));

        Glide.with(fragment)
                .load(BuildConfig.APP_SERVER_HOST + "images/" + rentalProvider.getBackgroundUrl())
                .apply(bitmapTransform(multi))
                .into(binding.backgroundView);
        Glide.with(fragment)
                .load(BuildConfig.APP_SERVER_HOST + "images/" + rentalProvider.getLogoUrl())
                .into(binding.logoView);
    }

    @Override public int getLayout()
    {
        return R.layout.rental_provider;
    }
}
