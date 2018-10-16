package com.appropel.schuss.common.util;

import net.orange_box.storebox.annotations.method.KeyByString;

/**
 * Interface to shared preferences - the underlying implementation is handled by StoreBox.
 */
public interface Preferences
{
    // CSOFF: JavadocVariable
    String KEY_EMAIL_ADDRESS = "emailAddress";
    String KEY_ADVERTISING_ID = "advertisingId";
    String KEY_USER_TOKEN = "key_userToken";

    /**
     * Returns the user's e-mail address.
     * @return e-mail address
     */
    @KeyByString(KEY_EMAIL_ADDRESS)
    String getEmailAddress();

    /**
     * Sets the user's e-mail address.
     * @param emailAddress e-mail address
     */
    @KeyByString(KEY_EMAIL_ADDRESS)
    void setEmailAddress(String emailAddress);

    /**
     * Returns advertising ID.
     *
     * @return advertising ID
     */
    @KeyByString(KEY_ADVERTISING_ID)
    String getAdvertisingId();

    /**
     * Sets advertising ID.
     *
     * @param advertisingId advertising ID
     */
    @KeyByString(KEY_ADVERTISING_ID)
    void setAdvertisingId(String advertisingId);

    /**
     * Returns the JWT token of the currently logged-in user.
     *
     * @return token
     */
    @KeyByString(KEY_USER_TOKEN)
    String getUserToken();

    /**
     * Sets the JWT token of the currently logged-in user.
     *
     * @param userToken user JWT token
     */
    @KeyByString(KEY_USER_TOKEN)
    void setUserToken(String userToken);
}
