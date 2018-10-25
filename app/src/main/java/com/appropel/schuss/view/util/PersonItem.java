package com.appropel.schuss.view.util;

import android.support.annotation.NonNull;

import com.appropel.schuss.R;
import com.appropel.schuss.databinding.PersonSummaryBinding;
import com.appropel.schuss.model.read.Person;
import com.xwray.groupie.databinding.BindableItem;

/**
 * Item in a list of Persons.
 */
public final class PersonItem extends BindableItem<PersonSummaryBinding>
{
    /** Person. */
    private final Person person;

    /**
     * Constructs a new {@code PersonItem}.
     * @param person person
     */
    public PersonItem(final Person person)
    {
        super();
        this.person = person;
    }

    @Override
    public void bind(@NonNull final PersonSummaryBinding viewBinding, final int position)
    {
        viewBinding.setPerson(person);
    }

    @Override
    public int getLayout()
    {
        return R.layout.person_summary;
    }
}
