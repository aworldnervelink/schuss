package com.appropel.schuss.logic;

import com.appropel.schuss.model.read.Person;
import com.appropel.schuss.model.read.User;

import java.util.List;

/**
 * Business methods for managing Users.
 */
public interface UserLogic
{
    /**
     * Signs the user in. If the email address and password are correct, the user is logged in. If the
     * information is new, a new user is created. Otherwise an error occurs.
     * @param emailAddress e-mail address
     * @param password encrypted password
     * @param advertisingId Google advertising identifier
     * @param newAccount true if a new account should be created
     * @return JWT token
     */
    String signIn(String emailAddress, String password, String advertisingId, boolean newAccount);

    /**
     * Creates or edits a Person attached to the given User.
     * @param user user
     * @param person person
     */
    void updatePerson(User user, Person person);

    /**
     * Returns a list of Persons attached to the current User.
     * @return list of Person
     */
    List<Person> getPersons();
}
