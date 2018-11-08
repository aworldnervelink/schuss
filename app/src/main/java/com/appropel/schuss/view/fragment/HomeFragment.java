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
import com.appropel.schuss.controller.UserInterface;
import com.appropel.schuss.dagger.DaggerWrapper;
import com.appropel.schuss.model.read.Person;
import com.appropel.schuss.model.read.User;
import com.appropel.schuss.view.util.PersonItem;
import com.xwray.groupie.GroupAdapter;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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
    /** List of Persons. */
    @BindView(R.id.person_list)
    RecyclerView personView;

    /** User interface. */
    @Inject
    UserInterface userInterface;

    /** Controller. */
    @Inject
    SchussController controller;

    /** Event bus. */
    @Inject
    EventBusFacade eventBus;

    /** View unbinder. */
    private Unbinder unbinder;

    /** View adapter. */
    private GroupAdapter adapter;

    /** User data. */
    private User user;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.home, container, false);
        unbinder = ButterKnife.bind(this, view);
        DaggerWrapper.INSTANCE.getComponent().inject(this);

        adapter = new GroupAdapter();
        personView.setAdapter(adapter);
        personView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        eventBus.register(this);
        controller.getUser();
    }

    /**
     * Handler for when person data arrives from the server.
     * @param event event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUserEvent(final User event)
    {
        adapter.clear();
        for (Person person : event.getPersons())
        {
            adapter.add(new PersonItem(person, userInterface, getActivity()));
        }
        user = event;
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
