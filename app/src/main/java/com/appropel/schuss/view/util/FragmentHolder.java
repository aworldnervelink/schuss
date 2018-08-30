package com.appropel.schuss.view.util;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Class which holds a Fragment and responds to change events.
 */
public final class FragmentHolder extends FrameLayout
{
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(FragmentHolder.class);

    /**
     * Constructs a new {@code FragmentHolder}.
     *
     * @param context Android Context.
     */
    public FragmentHolder(final Context context)
    {
        super(context);
    }

    /**
     * Constructs a new {@code FragmentHolder}.
     *
     * @param context Android Context.
     * @param attrs   attributes.
     */
    public FragmentHolder(final Context context, final AttributeSet attrs)
    {
        super(context, attrs);
    }

    /**
     * Constructs a new {@code FragmentHolder}.
     *
     * @param context  Android Context.
     * @param attrs    attributes.
     * @param defStyle style.
     */
    public FragmentHolder(final Context context, final AttributeSet attrs, final int defStyle)
    {
        super(context, attrs, defStyle);
    }

    /**
     * Changes the displayed fragment to the one specified passing no arguments.
     *
     * @param fragmentClass  fragment class to display.
     * @param addToBackStack fragment transaction will be added to back stack
     */
    public void changeFragment(final Class<? extends Fragment> fragmentClass,
                               final boolean addToBackStack)
    {
        changeFragment(fragmentClass, new Bundle(), addToBackStack);
    }

    /**
     * Changes the displayed fragment to the one specified.
     *
     * @param fragmentClass  fragment class to display.
     * @param arguments      fragment arguments.
     * @param addToBackStack fragment transaction will be added to back stack
     */
    public void changeFragment(final Class<? extends Fragment> fragmentClass,
                               final Bundle arguments,
                               final boolean addToBackStack)
    {
        final Fragment currentFragment = getCurrentFragment();
        if (currentFragment != null && fragmentClass.equals(currentFragment.getClass()))
        {
            return;
        }

        try
        {
            LOGGER.info("Changing fragment to {}", fragmentClass.getSimpleName());
            final Fragment fragment = fragmentClass.newInstance();
            fragment.setArguments(arguments);

            final Activity activity = (Activity) getContext();
            final FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            ft.replace(getId(), fragment);
            ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            if (addToBackStack)
            {
                ft.addToBackStack(null);
            }
            ft.commitAllowingStateLoss();
            // http://www.androiddesignpatterns.com/2013/08/fragment-transaction-commit-state-loss.html
        }
        catch (Exception ex)
        {
            LOGGER.error("Exception switching fragments", ex);
        }
    }

    /**
     * Returns current fragment.
     *
     * @return current fragment or null
     */
    @Nullable
    public Fragment getCurrentFragment()
    {
        final Activity activity = (Activity) getContext();
        return activity.getFragmentManager().findFragmentById(getId());
    }
}
