package com.appropel.schuss.security;

import com.google.common.annotations.VisibleForTesting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

/**
 * Service for generation and validation of JWT tokens.
 */
@Scope("singleton")
@Service
public final class JwtTokenService
{
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenService.class);

    /** User's token life time. */
    private static final int TOKEN_LIFE_TIME_IN_SECONDS = 86400; // 1 Day

    /** Secret key. */
    private static final String JWT_TOKENS_KEY =
            "JRWtL4ZospKXkORY/r4Z0ABi1Tu69FsYgewceY0ZZuWey7AVxxi/wrHQ6fnvflmSP/MmkxoHc++OosLhnWDYzw==";

    /** Client type parameter key. */
    private static final String CLIENT_TYPE_PARAM_KEY = "client_type";

    /** User ID parameter key. */
    private static final String USER_ID_PARAM_KEY = "user_id";

    /** User's device Advertising ID parameter key. */
    private static final String ADVERTISING_ID_PARAM_KEY = "advertising_id";

    /** JWT token life time. */
    private final int tokenLifeTime;

    /** JWT token validation error. */
    private static final String JWT_TOKEN_VALIDATION_ERR = "JWT token validation exception: {}";

    /**
     * Default constructor.
     */
    public JwtTokenService()
    {
        this.tokenLifeTime = TOKEN_LIFE_TIME_IN_SECONDS;
    }

    /**
     * Parametrized constructor.
     *
     * @param tokenLifeTime JWT token lifetime in seconds.
     */
    @VisibleForTesting
    public JwtTokenService(final int tokenLifeTime)
    {
        this.tokenLifeTime = tokenLifeTime;
    }


    /**
     * Returns JWT token which includes VX user ID, Google Advertising ID.
     *
     * @param userId        VX user ID.
     * @param advertisingId Advertising ID.              .
     * @return              token string or null.
     */
    public String getToken(final String userId, final String advertisingId)
    {
        final Map<String, Object> tokenData = new HashMap<>();
        tokenData.put(CLIENT_TYPE_PARAM_KEY, "user");
        tokenData.put(USER_ID_PARAM_KEY, userId);
        tokenData.put(ADVERTISING_ID_PARAM_KEY, advertisingId);
        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, tokenLifeTime);
        final Claims claims = Jwts.claims(tokenData).setExpiration(calendar.getTime());
        final JwtBuilder jwtBuilder = Jwts.builder();
        jwtBuilder.setClaims(claims);
        return jwtBuilder.signWith(SignatureAlgorithm.HS512, JWT_TOKENS_KEY).compact();
    }

    /**
     * Check whether token valid or not and compare user data and token's user data.
     *
     * @param token         JWT user's token.
     * @param userId        VX user ID.
     * @param advertisingId Advertising ID.
     * @return              True if token valid and relates to user.
     */
    public boolean isTokenValid(final String token, final String userId, final String advertisingId)
    {
        try
        {
            Claims claims = Jwts.parser().setSigningKey(JWT_TOKENS_KEY).parseClaimsJws(token).getBody();
            return claims.get(USER_ID_PARAM_KEY).equals(userId)
                    && claims.get(ADVERTISING_ID_PARAM_KEY).equals(advertisingId);
        }
        catch (Exception ex)
        {
            LOGGER.error(JWT_TOKEN_VALIDATION_ERR, ex.toString());
            return false;
        }
    }

    /**
     * Returns the user ID in the given token.
     * @param token token string
     * @return user identifier
     */
    public String getUserId(final String token)
    {
        try
        {
            Claims claims = Jwts.parser().setSigningKey(JWT_TOKENS_KEY).parseClaimsJws(token).getBody();
            return claims.get(USER_ID_PARAM_KEY).toString();
        }
        catch (Exception ex)
        {
            LOGGER.error(JWT_TOKEN_VALIDATION_ERR, ex.toString());
            return null;
        }
    }
}
