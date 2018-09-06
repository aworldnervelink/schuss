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
}
