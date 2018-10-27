package com.appropel.schuss.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.appropel.schuss.R;
import com.appropel.schuss.common.util.EventBusFacade;
import com.appropel.schuss.controller.SchussController;
import com.appropel.schuss.controller.event.AppServerRequestFailure;
import com.appropel.schuss.dagger.DaggerWrapper;
import com.appropel.schuss.service.SchussService;
import com.appropel.schuss.view.event.ChangeFragmentEvent;
import com.appropel.schuss.view.fragment.LoginFragment;
import com.appropel.schuss.view.util.FragmentHolder;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Main activity.
 */
public final class MainActivity extends AppCompatActivity
{
    /** Fragment holder. */
    @BindView(R.id.fragment_holder)
    FragmentHolder fragmentHolder;

    /** Event bus. */
    @Inject
    EventBusFacade eventBus;

    /** Remote service. */
    @Inject
    SchussService service;

    /** Controller. */
    @Inject
    SchussController controller;

    @Override
    protected void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        DaggerWrapper.INSTANCE.getComponent().inject(this);

        // Request advertising ID.
        controller.requestAdvertisingId();
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        ButterKnife.bind(this);
        eventBus.register(this);

        onChangeFragmentEvent(ChangeFragmentEvent.of(LoginFragment.class));
    }

    /**
     * Event handler that fires when a different fragment should be displayed.
     *
     * @param event event.
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onChangeFragmentEvent(final ChangeFragmentEvent event)
    {
        fragmentHolder.changeFragment(event.getFragmentClass(), event.getArguments(), true);
    }

    /**
     * Event handler that fires when an error occurs.
     * @param event event
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onAppServerRequestFailure(final AppServerRequestFailure event)
    {
        Toast.makeText(this, "Error: " + event.getError().toString(), Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop()
    {
        eventBus.unregister(this);
        super.onStop();
    }
}
