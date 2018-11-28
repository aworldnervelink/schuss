package com.appropel.schuss.view.fragment;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.appropel.schuss.R;
import com.appropel.schuss.dagger.DaggerWrapper;
import com.appropel.schuss.model.read.Request;
import com.appropel.schuss.view.util.ProfileAdapter;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment that shows the details of a particular request.
 */
public final class RequestDetailsFragment extends DialogFragment
{
    /** Key for the Request being examined. */
    public static final String REQUEST_KEY = "request";

    /** Time formatter. */
    private final Format timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());

    /** View showing the arrival time. */
    @BindView(R.id.arrival_time_digits)
    TextView arrivalTimeView;

    /** List of profiles. */
    @BindView(R.id.profile_list)
    ListView profileList;

    /** Status spinner. */
    @BindView(R.id.status_spinner)
    Spinner statusSpinner;

    /** View unbinder. */
    private Unbinder unbinder;

    /** Request. */
    private Request request;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.request_details, container, false);
        unbinder = ButterKnife.bind(this, view);
        DaggerWrapper.INSTANCE.getComponent().inject(this);

        request = (Request) getArguments().getSerializable(REQUEST_KEY);

        final ProfileAdapter profileAdapter = new ProfileAdapter(getActivity());
        profileList.setAdapter(profileAdapter);
        profileAdapter.addAll(request.getProfiles());

        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        arrivalTimeView.setText(timeFormat.format(request.getArrivalTime()));
        statusSpinner.setSelection(request.getStatus().ordinal());
    }

    @Override
    public void onDestroyView()
    {
        unbinder.unbind();
        super.onDestroyView();
    }
}
