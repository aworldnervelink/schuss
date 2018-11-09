package com.appropel.schuss.view.util;

import android.support.annotation.NonNull;
import android.widget.CompoundButton;

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

    /** Checked flag. */
    boolean checked = true;

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
        viewBinding.includeCheckBox.setChecked(checked);
        viewBinding.includeCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(final CompoundButton buttonView, final boolean isChecked)
            {
                checked = isChecked;
            }
        });
    }

    @Override
    public int getLayout()
    {
        return R.layout.person_rental;
    }

    public boolean isChecked()
    {
        return checked;
    }

    public Person getPerson()
    {
        return person;
    }
}
