package com.appropel.schuss.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appropel.schuss.R;
import com.appropel.schuss.common.util.EventBusFacade;
import com.appropel.schuss.controller.SchussController;
import com.appropel.schuss.controller.event.RequestEvent;
import com.appropel.schuss.dagger.DaggerWrapper;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment which holds the home screen for employees.
 */
public final class EmployeeHomeFragment extends Fragment
{
    /** Controller. */
    @Inject
    SchussController controller;

    /** Event bus. */
    @Inject
    EventBusFacade eventBus;

    /** View unbinder. */
    private Unbinder unbinder;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.home, container, false);
        unbinder = ButterKnife.bind(this, view);
        DaggerWrapper.INSTANCE.getComponent().inject(this);

        return super.onCreateView(inflater, container, savedInstanceState);
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
        // TODO
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
