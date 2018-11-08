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
import com.appropel.schuss.model.read.Person;
import com.appropel.schuss.model.read.RentalProvider;
import com.appropel.schuss.model.read.User;
import com.appropel.schuss.view.util.PersonRentalItem;
import com.appropel.schuss.view.util.RentalProviderItem;
import com.xwray.groupie.GroupAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Fragment for adding a new rental request.
 */
public final class RentalRequestFragment extends Fragment
{
    /** Key for finding a User in argument Bundle. */
    public static final String USER_KEY = "user";

    /** View that holds a list of rental providers. */
    @BindView(R.id.rental_provider_view)
    RecyclerView providerView;

    /** View that holds a list of people. */
    @BindView(R.id.person_view)
    RecyclerView personView;

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

    /** View adapter. */
    private GroupAdapter personAdapter;     // NOPMD

    /** User data. */
    private User user;      // NOPMD

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.rental_request, container, false);
        unbinder = ButterKnife.bind(this, view);
        DaggerWrapper.INSTANCE.getComponent().inject(this);

        providerAdapter = new GroupAdapter();
        providerView.setAdapter(providerAdapter);
        providerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        personAdapter = new GroupAdapter();
        personView.setAdapter(personAdapter);
        personView.setLayoutManager(new LinearLayoutManager(getActivity()));

        user = (User) getArguments().getSerializable(USER_KEY);
        for (Person person : user.getPersons())
        {
            personAdapter.add(new PersonRentalItem(person));
        }

        onWhereButtonClicked();     // Start at 'Where' screen

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

    /**
     * Handler for when the user clicks the 'Where?' button.
     */
    @OnClick(R.id.where_button)
    public void onWhereButtonClicked()
    {
        providerView.setVisibility(View.VISIBLE);
        personView.setVisibility(View.INVISIBLE);
    }

    /**
     * Handler for when the user clicks the 'Who/What?' button.
     */
    @OnClick(R.id.who_what_button)
    public void onWhoWhatButtonClicked()
    {
        providerView.setVisibility(View.INVISIBLE);
        personView.setVisibility(View.VISIBLE);
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
