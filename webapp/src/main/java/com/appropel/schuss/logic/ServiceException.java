package com.appropel.schuss.logic;

/**
 * Exception that wraps an enumerated failure code.
 */
public final class ServiceException extends RuntimeException
{
    /** Wrapped failure code. */
    private final ServiceError serviceError;

    /**
     * Constructs a new {@code ServiceException}.
     * @param serviceError enumerated error
     */
    public ServiceException(final ServiceError serviceError)
    {
        super();
        this.serviceError = serviceError;
    }

    public ServiceError getServiceError()
    {
        return serviceError;
    }
}
