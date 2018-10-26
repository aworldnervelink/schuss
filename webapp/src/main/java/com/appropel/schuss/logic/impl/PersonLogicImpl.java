package com.appropel.schuss.logic.impl;

import com.appropel.schuss.logic.PersonLogic;
import com.appropel.schuss.model.impl.PersonImpl;
import com.appropel.schuss.model.impl.ProfileImpl;
import com.appropel.schuss.model.impl.UserImpl;
import com.appropel.schuss.model.read.Person;
import com.google.common.base.Preconditions;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of user logic.
 */
@Transactional
@Component
@SuppressWarnings({"checkstyle:DesignForExtension", "PMD.GodClass"})  // Cannot be final for AOP enhancement
public class PersonLogicImpl implements PersonLogic
{
    @Override
    public void updateProfile(final UserImpl user, final PersonImpl person, final ProfileImpl profile)
    {
        // Person must be attached to the user.
        PersonImpl existingPerson = null;
        for (Person attachedPerson : user.getPersons())
        {
            if (attachedPerson.getId() == person.getId())
            {
                existingPerson = (PersonImpl) attachedPerson;
            }
        }
        Preconditions.checkState(existingPerson != null, "Person must be attached to User!");

        if (profile.getId() == 0)
        {
            // This is a new profile
            // TODO: check to ensure user only has one profile of each type
            existingPerson.addProfile(profile);
        }
        // TODO: handle else/edit case
    }
}
