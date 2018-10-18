package com.appropel.schuss.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appropel.schuss.R;
import com.appropel.schuss.common.util.UserInterface;
import com.appropel.schuss.dagger.DaggerWrapper;

import javax.inject.Inject;

import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Fragment which holds the home screen for the user.
 */
public final class HomeFragment extends Fragment
{
    /** User interface. */
    @Inject
    UserInterface userInterface;

    /** View unbinder. */
    private Unbinder unbinder;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.home, container, false);
        DaggerWrapper.INSTANCE.getComponent().inject(this);

        return view;    // NOPMD TODO
    }

    /**
     * Handler for when the user clicks 'add a person'.
     */
    @OnClick(R.id.add_person_button)
    public void onAddPersonClicked()
    {
        userInterface.showEditPersonScreen();
    }

    @Override
    public void onDestroyView()
    {
        unbinder.unbind();
        super.onDestroyView();
    }
}