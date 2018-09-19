package com.appropel.schuss.model.read;

/**
 * HTTP headers used in the VX app protocol.
 */
public enum ProtocolHeaders
{
    /** User advertising ID. */
    ADVERTISING_ID("Advertising-Id");

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