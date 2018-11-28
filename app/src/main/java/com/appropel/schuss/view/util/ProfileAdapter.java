package com.appropel.schuss.view.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appropel.schuss.R;
import com.appropel.schuss.model.read.DownhillProfile;
import com.appropel.schuss.model.read.Profile;

/**
 * Adapter that displays profile info.
 */
// TODO: make this a RecyclerView/dynamically adapt to various types of profiles. Downhill is currently assumed.
public final class ProfileAdapter extends ArrayAdapter<Profile>
{
    /**
     * Constructs a new {@code ProfileAdapter}.
     * @param context Android Context
     */
    public ProfileAdapter(final Context context)
    {
        super(context, R.layout.downhill_profile_detail);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent)
    {
        final DownhillProfile profile = (DownhillProfile) getItem(position);
            // TODO: currently assumed to be downhill always!

        final View view = convertView != null ? convertView
                : LayoutInflater.from(getContext()).inflate(R.layout.downhill_profile_detail, parent, false);

        final TextView firstNameView = view.findViewById(R.id.first_name);
        firstNameView.setText(profile.getPerson().getFirstName());
        final TextView lastNameView = view.findViewById(R.id.last_name);
        lastNameView.setText(profile.getPerson().getLastName());
        final TextView phoneNumberView = view.findViewById(R.id.phone_number);
        phoneNumberView.setText(profile.getPerson().getPhoneNumber());

        final TextView skierTypeView = view.findViewById(R.id.skier_type);
        skierTypeView.setText(profile.getSkierType());
        final TextView bootSizeView = view.findViewById(R.id.boot_size);
        bootSizeView.setText(profile.getBootSize());
        final TextView skiLengthView = view.findViewById(R.id.ski_length);
        skiLengthView.setText(profile.getSkiSize());
        final TextView poleLengthView = view.findViewById(R.id.pole_length);
        poleLengthView.setText(profile.getSkiPoleLength());
        final TextView helmetSizeView = view.findViewById(R.id.helmet_size);
        helmetSizeView.setText(profile.getHelmetSize());

        // TODO: develop way to use Jackson to do all this automatically via serialization!

        return view;
    }
}
