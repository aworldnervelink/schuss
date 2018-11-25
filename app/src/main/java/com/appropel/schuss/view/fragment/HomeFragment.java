package com.appropel.schuss.view.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.appropel.schuss.R;
import com.appropel.schuss.common.util.EventBusFacade;
import com.appropel.schuss.common.util.Preferences;
import com.appropel.schuss.controller.SchussController;
import com.appropel.schuss.controller.UserInterface;
import com.appropel.schuss.dagger.DaggerWrapper;
import com.appropel.schuss.model.read.Person;
import com.appropel.schuss.model.read.Request;
import com.appropel.schuss.model.read.User;
import com.appropel.schuss.view.util.PersonItem;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xwray.groupie.GroupAdapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Fragment which holds the home screen for the user.
 */
public final class HomeFragment extends Fragment
{
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(HomeFragment.class);

    /** Key for finding a User in argument Bundle. */
    public static final String USER_KEY = "user";

    /** List of Persons. */
    @BindView(R.id.person_list)
    RecyclerView personView;

    /** List of rental requests. */
    @BindView(R.id.rental_list)
    ListView rentalView;

    /** User interface. */
    @Inject
    UserInterface userInterface;

    /** Controller. */
    @Inject
    SchussController controller;

    /** Event bus. */
    @Inject
    EventBusFacade eventBus;

    /** Preferences. */
    @Inject
    Preferences preferences;

    /** Object mapper. */
    @Inject
    ObjectMapper objectMapper;

    /** View unbinder. */
    private Unbinder unbinder;

    /** View adapter. */
    private GroupAdapter personAdapter;

    /** Request view adapter. */
    private ArrayAdapter<Request> requestAdapter;

    /** User data. */
    private User user;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.home, container, false);
        unbinder = ButterKnife.bind(this, view);
        DaggerWrapper.INSTANCE.getComponent().inject(this);

        personAdapter = new GroupAdapter();
        personView.setAdapter(personAdapter);
        personView.setLayoutManager(new LinearLayoutManager(getActivity()));

        requestAdapter = new RequestAdapter(getActivity(), android.R.layout.simple_list_item_1);
        rentalView.setAdapter(requestAdapter);

        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
//        eventBus.register(this);

        try
        {
            user = objectMapper.readValue(preferences.getUser(), User.class);
        }
        catch (IOException e)
        {
            LOGGER.error("JSON exception", e);
        }
        personAdapter.clear();
        for (Person person : user.getPersons())
        {
            personAdapter.add(new PersonItem(person, userInterface, getActivity()));
        }
        requestAdapter.clear();
        requestAdapter.addAll(user.getRequests());
        requestAdapter.notifyDataSetChanged();
    }

    /**
     * Handler for when the user clicks 'add a person'.
     */
    @OnClick(R.id.add_person_button)
    public void onAddPersonClicked()
    {
        userInterface.showEditPersonScreen();
    }

    /**
     * Handler for when the user clicks 'add a request'.
     */
    @OnClick(R.id.add_request_button)
    public void onAddRentalRequest()
    {
        userInterface.showRentalRequestScreen(user);
    }

    @Override
    public void onStop()    // NOPMD: may use bus again in the future
    {
//        eventBus.unregister(this);
        super.onStop();
    }

    @Override
    public void onDestroyView()
    {
        unbinder.unbind();
        super.onDestroyView();
    }

    /** Quick and dirty adapter to show rental requests. */
    public static final class RequestAdapter extends ArrayAdapter<Request>
    {
        /**
         * Constructs a new {@code RequestAdapter}.
         * @param context Android Context
         * @param resource resource ID
         */
        RequestAdapter(final Context context, final int resource)
        {
            super(context, resource);

        }

        @Override
        public View getView(final int position, final View convertView, final ViewGroup parent)
        {
            final Request request = getItem(position);
            final TextView view = (TextView) super.getView(position, convertView, parent);
            view.setText(request.getRentalProvider().getName());
            return view;
        }
    }
}
