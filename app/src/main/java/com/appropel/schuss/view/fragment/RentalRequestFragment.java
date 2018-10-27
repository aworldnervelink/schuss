package com.appropel.schuss.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appropel.schuss.R;
import com.appropel.schuss.common.util.EventBusFacade;
import com.appropel.schuss.controller.SchussController;
import com.appropel.schuss.controller.event.ProviderEvent;
import com.appropel.schuss.dagger.DaggerWrapper;
import com.appropel.schuss.model.read.RentalProvider;
import com.appropel.schuss.view.util.RentalProviderItem;
import com.xwray.groupie.GroupAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment for adding a new rental request.
 */
public final class RentalRequestFragment extends Fragment
{
    /** View that holds a list of rental providers. */
    @BindView(R.id.rental_providers_list)
    RecyclerView providerView;

    /** View unbinder. */
    private Unbinder unbinder;

    /** Event bus. */
    @Inject
    EventBusFacade eventBus;

    /** Controller. */
    @Inject
    SchussController controller;

    /** View adapter. */
    private GroupAdapter providerAdapter;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.rental_request, container, false);
        unbinder = ButterKnife.bind(this, view);
        DaggerWrapper.INSTANCE.getComponent().inject(this);

        providerAdapter = new GroupAdapter();
        providerView.setAdapter(providerAdapter);
        providerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;    // NOPMD TODO
    }

    @Override
    public void onStart()
    {
        super.onStart();

        eventBus.register(this);
        controller.getRentalProviders();
    }

    /**
     * Handler for when provider data arrives from the server.
     * @param event event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onProviderEvent(final ProviderEvent event)
    {
        providerAdapter.clear();
        for (RentalProvider provider : event.getRentalProviders())
        {
            providerAdapter.add(new RentalProviderItem(provider, this));
        }
    }

    @Override
    public void onStop()
    {
        eventBus.unregister(this);
        super.onStop();
    }

    @Override
    public void onDestroyView()
    {
        unbinder.unbind();
        super.onDestroyView();
    }
}
