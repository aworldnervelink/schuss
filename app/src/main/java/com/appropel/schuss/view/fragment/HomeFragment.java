package com.appropel.schuss.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appropel.schuss.R;

/**
 * Fragment which holds the home screen for the user.
 */
public final class HomeFragment extends Fragment
{
    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.home, container, false);
//        unbinder = ButterKnife.bind(this, view);
//        DaggerWrapper.INSTANCE.getComponent().inject(this);

        return view;    // NOPMD TODO
    }
}
