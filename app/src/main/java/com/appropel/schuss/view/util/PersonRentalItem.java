package com.appropel.schuss.view.util;

import android.support.annotation.NonNull;

import com.appropel.schuss.R;
import com.appropel.schuss.databinding.PersonRentalBinding;
import com.appropel.schuss.model.read.Person;
import com.xwray.groupie.databinding.BindableItem;

/**
 * Item in a list of Persons for rental selection.
 */
public final class PersonRentalItem extends BindableItem<PersonRentalBinding>
{
    /** Person. */
    final Person person;

    /**
     * Constructs a new {@code PersonItem}.
     * @param person person
     */
    public PersonRentalItem(final Person person)
    {
        super();
        this.person = person;
    }

    @Override
    public void bind(@NonNull final PersonRentalBinding viewBinding, final int position)
    {
        viewBinding.setPerson(person);
    }

    @Override
    public int getLayout()
    {
        return R.layout.person_rental;
    }
}
