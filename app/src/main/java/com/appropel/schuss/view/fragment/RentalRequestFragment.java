package com.appropel.schuss.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appropel.schuss.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment for adding a new rental request.
 */
public final class RentalRequestFragment extends Fragment
{
    /** View unbinder. */
    private Unbinder unbinder;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.rental_request, container, false);
        unbinder = ButterKnife.bind(this, view);
//        DaggerWrapper.INSTANCE.getComponent().inject(this);

//        adapter = new GroupAdapter();
//        personView.setAdapter(adapter);
//        personView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;    // NOPMD TODO
    }

    @Override
    public void onDestroyView()
    {
        unbinder.unbind();
        super.onDestroyView();
    }
}
