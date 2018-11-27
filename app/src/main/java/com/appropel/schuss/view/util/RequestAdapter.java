package com.appropel.schuss.view.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appropel.schuss.R;
import com.appropel.schuss.model.read.Person;
import com.appropel.schuss.model.read.Profile;
import com.appropel.schuss.model.read.Request;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * An ArrayAdapter that displays info about a rental request.
 */
// TODO: convert this to recyclerview at some point. This class is written inefficiently because the data list
// is expected to be small.
public final class RequestAdapter extends ArrayAdapter<Request>
{
    /** Time formatter. */
    private static final Format TIME_FORMAT = new SimpleDateFormat("HH:mm", Locale.getDefault());

    /**
     * Constructs a new {@code RequestAdapter}.
     * @param context Android Context
     */
    public RequestAdapter(final Context context)
    {
        super(context, R.layout.rental_request_summary);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent)
    {
        final Request request = getItem(position);
        final View view = convertView != null ? convertView
                : LayoutInflater.from(getContext()).inflate(R.layout.rental_request_summary, parent, false);

        final TextView arrivalTimeView = view.findViewById(R.id.arrival_time_digits);
        arrivalTimeView.setText(TIME_FORMAT.format(request.getArrivalTime()));
        final TextView rentersView = view.findViewById(R.id.renters);
        rentersView.setText(String.format("%d", request.getProfiles().size()));

        final TextView renterNamesView = view.findViewById(R.id.renter_names);
        StringBuilder sb = new StringBuilder();
        for (final Profile profile : request.getProfiles())
        {
            if (sb.length() > 0)
            {
                sb.append(", ");
            }
            final Person person = profile.getPerson();
            sb.append(person.getFirstName());
            sb.append(' ');
            sb.append(person.getLastName());
        }
        renterNamesView.setText(sb.toString());

        return view;
    }
}
