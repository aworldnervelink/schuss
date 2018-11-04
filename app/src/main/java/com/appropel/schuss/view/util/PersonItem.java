package com.appropel.schuss.view.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;

import com.appropel.schuss.R;
import com.appropel.schuss.controller.UserInterface;
import com.appropel.schuss.databinding.PersonSummaryBinding;
import com.appropel.schuss.model.read.Person;
import com.appropel.schuss.model.read.Profile;
import com.xwray.groupie.databinding.BindableItem;

/**
 * Item in a list of Persons.
 */
public final class PersonItem extends BindableItem<PersonSummaryBinding>
{
    /** Person. */
    final Person person;

    /** User interface. */
    final UserInterface userInterface;

    /** Android context. */
    final Context context;

    /**
     * Constructs a new {@code PersonItem}.
     * @param person person
     * @param userInterface user interface for screen switching
     * @param context Android Context
     */
    public PersonItem(final Person person, final UserInterface userInterface, final Context context)
    {
        super();
        this.person = person;
        this.userInterface = userInterface;
        this.context = context;
    }

    @Override
    public void bind(@NonNull final PersonSummaryBinding viewBinding, final int position)
    {
        viewBinding.setPerson(person);
        viewBinding.addProfileButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(final View view)
            {
                userInterface.showDownhillProfileScreen(person);
            }
        });
        viewBinding.profiles.setText(getProfileText());
    }

    @Override
    public int getLayout()
    {
        return R.layout.person_summary;
    }

    /**
     * Returns a string that lists the various profiles a person has.
     * @return profile string
     */
    private String getProfileText()
    {
        if (person.getProfiles().isEmpty())
        {
            return "";
        }

        final String[] profileNames = context.getResources().getStringArray(R.array.profile_type);
        StringBuilder sb = new StringBuilder();
        for (final Profile profile : person.getProfiles())
        {
            if (sb.length() > 0)
            {
                sb.append(", ");
            }
            sb.append(profileNames[profile.getProfileType().ordinal()]);
        }
        return sb.toString();
    }
}
