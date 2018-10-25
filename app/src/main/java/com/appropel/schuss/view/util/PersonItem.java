package com.appropel.schuss.view.util;

import android.support.annotation.NonNull;
import android.view.View;

import com.appropel.schuss.R;
import com.appropel.schuss.controller.UserInterface;
import com.appropel.schuss.databinding.PersonSummaryBinding;
import com.appropel.schuss.model.read.Person;
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

    /**
     * Constructs a new {@code PersonItem}.
     * @param person person
     * @param userInterface user interface for screen switching
     */
    public PersonItem(final Person person, final UserInterface userInterface)
    {
        super();
        this.person = person;
        this.userInterface = userInterface;
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
    }

    @Override
    public int getLayout()
    {
        return R.layout.person_summary;
    }
}
