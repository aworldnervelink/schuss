package com.appropel.schuss.security;

import com.appropel.schuss.rest.ProtocolHeaders;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Security interceptor that checks if user token is valid.
 */
@Component
public final class SecurityInterceptor extends HandlerInterceptorAdapter
{
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(SecurityInterceptor.class);

    /** JWT token service. */
    private JwtTokenService tokenService;

    @Autowired
    public void setTokenService(final JwtTokenService tokenService)
    {
        this.tokenService = tokenService;
    }

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response, final Object handler)
    {
        String userToken = request.getHeader(ProtocolHeaders.TOKEN.toString());
        if (userToken == null)
        {
            LOGGER.warn("Authentication failed. User's token is null.");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return false;
        }

        String advertisingId = request.getHeader(ProtocolHeaders.ADVERTISING_ID.toString());
        if (advertisingId == null)
        {
            LOGGER.warn("Authentication failed. User's Advertising ID is null.");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return false;
        }

        String userId = request.getHeader(ProtocolHeaders.EMAIL_ADDRESS.toString());
        if (userId == null)
        {
            LOGGER.warn("Authentication failed. User ID is null.");
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return false;
        }

        return tokenService.isTokenValid(userToken, userId, advertisingId);
        // TODO: if false, return a specific error code?
    }
}