package com.appropel.schuss.logic.impl;

import com.appropel.schuss.logic.PersonLogic;
import com.appropel.schuss.model.impl.DownhillProfileImpl;
import com.appropel.schuss.model.impl.PersonImpl;
import com.appropel.schuss.model.impl.ProfileImpl;
import com.appropel.schuss.model.read.Person;
import com.appropel.schuss.model.read.Profile;
import com.appropel.schuss.model.read.User;
import com.google.common.base.Preconditions;

import org.springframework.beans.BeanUtils;
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
    public void updateProfile(final User user, final Person person, final Profile profile)
    {
        // Person must be attached to the user.
        Person existingPerson = null;
        for (Person attachedPerson : user.getPersons())
        {
            if (attachedPerson.getId() == person.getId())
            {
                existingPerson = attachedPerson;
            }
        }
        Preconditions.checkState(existingPerson != null, "Person must be attached to User!");

        if (profile.getId() == 0)
        {
            // This is a new profile
            final ProfileImpl newProfile;
            switch (profile.getProfileType())
            {
                case DOWNHILL:
                    newProfile = new DownhillProfileImpl();
                    break;
                default:
                    throw new IllegalArgumentException("Unknown profile type " + profile.getProfileType());
            }
            BeanUtils.copyProperties(profile, newProfile);

            ((PersonImpl) existingPerson).addProfile(newProfile);
        }
        // TODO: handle else/edit case
    }
}
