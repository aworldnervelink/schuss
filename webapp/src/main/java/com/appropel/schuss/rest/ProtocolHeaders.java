package com.appropel.schuss.rest;

/**
 * HTTP headers used in the VX app protocol.
 */
public enum ProtocolHeaders
{
    /** JWT security token. */
    TOKEN("token"),

    /** User advertising ID. */
    ADVERTISING_ID("Advertising-Id"),

    /** User's e-mail address. */
    EMAIL_ADDRESS("Email-Address");

    /** HTTP Header name. */
    private String name;

    /**
     * Private parametrized constructor.
     * @param name HTTP header name.
     */
    private ProtocolHeaders(final String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return this.name;
    }
}
