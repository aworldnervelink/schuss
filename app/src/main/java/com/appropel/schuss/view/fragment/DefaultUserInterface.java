package com.appropel.schuss.view.fragment;

import com.appropel.schuss.common.util.EventBusFacade;
import com.appropel.schuss.controller.UserInterface;
import com.appropel.schuss.model.read.Person;
import com.appropel.schuss.model.read.User;
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
    public void showHomeScreen()
    {
        eventBus.post(ChangeFragmentEvent.of(HomeFragment.class));
    }

    @Override
    public void showEditPersonScreen()
    {
        eventBus.post(ChangeFragmentEvent.of(EditPersonFragment.class));
    }

    @Override
    public void showDownhillProfileScreen(final Person person)
    {
        eventBus.post(
                ChangeFragmentEvent.of(DownhillProfileFragment.class, DownhillProfileFragment.PERSON_KEY, person));
    }

    @Override
    public void showRentalRequestScreen(final User user)
    {
        eventBus.post(ChangeFragmentEvent.of(RentalRequestFragment.class, RentalRequestFragment.USER_KEY, user));
    }
}
