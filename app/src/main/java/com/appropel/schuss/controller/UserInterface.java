package com.appropel.schuss.controller;

import com.appropel.schuss.model.read.Person;
import com.appropel.schuss.model.read.User;

/**
 * Interface for changing the top-level view. All transactions are added to the back
 * stack (i.e. pressing "Back" opens the previous screen) unless mentioned otherwise.
 */
public interface UserInterface
{
    /**
     * Shows the home screen.
     */
    void showHomeScreen();

    /**
     * Shows the edit person screen.
     */
    void showEditPersonScreen();

    /**
     * Shows the downhill profile screen for the given person.
     * @param person person
     */
    void showDownhillProfileScreen(Person person);

    /**
     * Shows the rental request screen.
     * @param user user
     */
    void showRentalRequestScreen(User user);
}
