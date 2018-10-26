package com.appropel.schuss.logic;

import com.appropel.schuss.model.read.Person;
import com.appropel.schuss.model.read.Profile;
import com.appropel.schuss.model.read.User;

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
    void updateProfile(User user, Person person, Profile profile);
}
