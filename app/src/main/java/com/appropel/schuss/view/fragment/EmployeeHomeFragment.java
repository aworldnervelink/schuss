package com.appropel.schuss.view.fragment;

import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.appropel.schuss.R;
import com.appropel.schuss.common.util.EventBusFacade;
import com.appropel.schuss.common.util.Preferences;
import com.appropel.schuss.controller.SchussController;
import com.appropel.schuss.controller.event.RequestEvent;
import com.appropel.schuss.dagger.DaggerWrapper;
import com.appropel.schuss.model.read.Request;
import com.appropel.schuss.model.read.User;
import com.appropel.schuss.view.event.ChangeFragmentEvent;
import com.appropel.schuss.view.util.RequestAdapter;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.Unbinder;

/**
 * Fragment which holds the home screen for employees.
 */
public final class EmployeeHomeFragment extends Fragment
{
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeHomeFragment.class);

    /** Name of provider at top header. */
    @BindView(R.id.rental_provider_name)
    TextView providerNameView;

    /** List of rental requests. */
    @BindView(R.id.rental_list)
    ListView rentalView;

    /** Controller. */
    @Inject
    SchussController controller;

    /** Event bus. */
    @Inject
    EventBusFacade eventBus;

    /** Object mapper. */
    @Inject
    ObjectMapper objectMapper;

    /** Preferences. */
    @Inject
    Preferences preferences;

    /** Request view adapter. */
    private RequestAdapter requestAdapter;

    /** View unbinder. */
    private Unbinder unbinder;

    /** User data. */
    private User user;  // NOPMD

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.employee_home, container, false);
        unbinder = ButterKnife.bind(this, view);
        DaggerWrapper.INSTANCE.getComponent().inject(this);

        requestAdapter = new RequestAdapter(getActivity());
        rentalView.setAdapter(requestAdapter);

        try
        {
            user = objectMapper.readValue(preferences.getUser(), User.class);
        }
        catch (IOException e)
        {
            LOGGER.error("JSON exception", e);
        }
        providerNameView.setText(user.getRentalProvider().getName());

        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        eventBus.register(this);
        controller.getRequests();
    }

    /**
     * Event handler for when request data arrives.
     * @param event event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onRequestEvent(final RequestEvent event)
    {
        requestAdapter.clear();
        requestAdapter.addAll(event.getRequests());
        requestAdapter.notifyDataSetChanged();
    }

    /**
     * Event handler for when the user clicks a rental request in the list.
     * @param listView listView that generated the click
     * @param position position in the list
     */
    @OnItemClick(R.id.rental_list)
    public void onItemClick(final ListView listView, final int position)
    {
        final Request request = (Request) listView.getItemAtPosition(position);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag(RequestDetailsFragment.class.getSimpleName());
        if (prev != null)
        {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DialogFragment newFragment = new RequestDetailsFragment();
        newFragment.setArguments(ChangeFragmentEvent.createArguments(RequestDetailsFragment.REQUEST_KEY, request));
        newFragment.show(ft, RequestDetailsFragment.class.getSimpleName());
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
