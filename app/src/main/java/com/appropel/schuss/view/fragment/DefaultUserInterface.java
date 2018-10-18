package com.appropel.schuss.view.fragment;

import com.appropel.schuss.common.util.EventBusFacade;
import com.appropel.schuss.common.util.UserInterface;
import com.appropel.schuss.view.event.ChangeFragmentEvent;

/**
 * Implementation of user interface which can issue commands to switch screens, etc.
 */
public final class DefaultUserInterface implements UserInterface
{
    /** Event bus. */
    private final EventBusFacade eventBus;

    /**
     * Constructs a new {@code DefaultScreenSwitcher}.
     *
     * @param eventBus event bus.
     */
    public DefaultUserInterface(final EventBusFacade eventBus)
    {
        this.eventBus = eventBus;
    }

    @Override
    public void showEditPersonScreen()
    {
        // TODO
    }

    @Override
    public void showHomeScreen()
    {
        eventBus.post(ChangeFragmentEvent.of(HomeFragment.class));
    }
}
