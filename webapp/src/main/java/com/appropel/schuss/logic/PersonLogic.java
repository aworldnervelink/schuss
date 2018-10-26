package com.appropel.schuss.logic;

import com.appropel.schuss.model.impl.PersonImpl;
import com.appropel.schuss.model.impl.ProfileImpl;
import com.appropel.schuss.model.impl.UserImpl;

/**
 * Business logic for managing Persons.
 */
public interface PersonLogic
{
    /**
     * Creates or updates a Profile attached to the given Person.
     * @param user user
     * @param person person
     * @param profile profile
     */
    void updateProfile(UserImpl user, PersonImpl person, ProfileImpl profile);
}
