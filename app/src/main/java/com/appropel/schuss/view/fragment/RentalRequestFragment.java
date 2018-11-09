package com.appropel.schuss.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.appropel.schuss.R;
import com.appropel.schuss.common.util.EventBusFacade;
import com.appropel.schuss.controller.SchussController;
import com.appropel.schuss.controller.event.ProviderEvent;
import com.appropel.schuss.dagger.DaggerWrapper;
import com.appropel.schuss.model.read.Person;
import com.appropel.schuss.model.read.Profile;
import com.appropel.schuss.model.read.RentalProvider;
import com.appropel.schuss.model.read.Request;
import com.appropel.schuss.model.read.User;
import com.appropel.schuss.view.util.PersonRentalItem;
import com.appropel.schuss.view.util.RentalProviderItem;
import com.xwray.groupie.GroupAdapter;
import com.xwray.groupie.Item;
import com.xwray.groupie.OnItemClickListener;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

    /** Text view that shows selected resort name. */
    @BindView(R.id.provider_name)
    TextView providerNameView;

    /** 'Where' layout. */
    @BindView(R.id.where_layout)
    View whereLayout;

    /** 'Who/What' layout. */
    @BindView(R.id.who_what_layout)
    View whoWhatLayout;

    /** 'When' layout. */
    @BindView(R.id.when_layout)
    View whenLayout;

    /** Arrival time picker. */
    @BindView(R.id.arrival_time)
    TimePicker arrivalTimePicker;

    /** Confirmation button. */
    @BindView(R.id.confirm_button)
    Button confirmButton;

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

    /** Selected resort. */
    RentalProvider rentalProvider;

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

        providerAdapter.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(@NonNull final Item item, @NonNull final View view)
            {
                final RentalProviderItem rpi = (RentalProviderItem) item;
                rentalProvider = rpi.getRentalProvider();
                providerNameView.setText(rentalProvider.getName());
                confirmButton.setEnabled(true);
            }
        });

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
        whereLayout.setVisibility(View.VISIBLE);
        whoWhatLayout.setVisibility(View.INVISIBLE);
        whenLayout.setVisibility(View.INVISIBLE);
    }

    /**
     * Handler for when the user clicks the 'Who/What?' button.
     */
    @OnClick(R.id.who_what_button)
    public void onWhoWhatButtonClicked()
    {
        whereLayout.setVisibility(View.INVISIBLE);
        whoWhatLayout.setVisibility(View.VISIBLE);
        whenLayout.setVisibility(View.INVISIBLE);
    }

    /**
     * Handler for when the user clicks the 'Who/What?' button.
     */
    @OnClick(R.id.when_button)
    public void onWhenClicked()
    {
        whereLayout.setVisibility(View.INVISIBLE);
        whoWhatLayout.setVisibility(View.INVISIBLE);
        whenLayout.setVisibility(View.VISIBLE);
    }

    /**
     * Handler for when the user clicks the Confirm button.
     */
    @OnClick(R.id.confirm_button)
    public void onConfirmClicked()
    {
        // Collect the checked profiles from the person recycler view.
        final List<Profile> profiles = new ArrayList<>();
        for (int i = 0; i < personAdapter.getItemCount(); ++i)
        {
            final PersonRentalItem item = (PersonRentalItem) personAdapter.getItem(i);
            if (item.isChecked() && !item.getPerson().getProfiles().isEmpty())
            {
                profiles.add(item.getPerson().getProfiles().iterator().next());
                // TODO: for now the assumption is that there is one profile, the Downhill. Later we will need
                // to inspect the spinner to see what the user picked.
            }
        }

        // Construct a date from the chosen arrival time.
        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, arrivalTimePicker.getHour());
        calendar.set(Calendar.MINUTE, arrivalTimePicker.getMinute());

        final Request request = Request.of(rentalProvider, profiles, calendar.getTime());
        controller.createRequest(request);
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
