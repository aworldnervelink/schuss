package com.appropel.schuss.common.util;

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
}
