package com.appropel.schuss.rest;

import com.appropel.schuss.dao.UserDao;
import com.appropel.schuss.model.impl.UserImpl;
import com.appropel.schuss.security.JwtTokenService;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;

/**
 * Base class for controllers.
 */
@SuppressWarnings("checkstyle:DesignForExtension")  // Cannot be final for AOP enhancement
public abstract class BaseController
{
    /** Jackson object mapper. */
    private ObjectMapper objectMapper;

    /** JWT service. */
    private JwtTokenService jwtTokenService;

    /** User DAO. */
    private UserDao userDao;

    @Autowired
    public void setObjectMapper(final ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }

    @Autowired
    public void setJwtTokenService(final JwtTokenService jwtTokenService)
    {
        this.jwtTokenService = jwtTokenService;
    }

    @Autowired
    public void setUserDao(final UserDao userDao)
    {
        this.userDao = userDao;
    }

    /**
     * Write object as prettified json into stream using default project's Jackson ObjectMapper.
     *
     * @param stream output stream.
     * @param object which would be serialized.
     * @throws IOException exception rises when problems occur in the process of the serialization.
     */
    void writeAsJson(final OutputStream stream, final Object object) throws IOException
    {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(stream, object);
    }

    /**
     * Write object as json into stream using given view.
     *
     * @param stream output stream.
     * @param object which would be serialized.
     * @param view   view class.
     * @throws IOException exception rises when problems occur in the process of the serialization.
     */
    void writeAsJson(final OutputStream stream, final Object object, final Class view) throws IOException
    {
        objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION).writerWithView(view).writeValue(stream, object);
    }

    /**
     * Returns the current user.
     * @param request HTTP request that contains a JWT in the headers
     * @return current user or null if not located
     */
    protected UserImpl getCurrentUser(final HttpServletRequest request)
    {
        final String userId = jwtTokenService.getUserId(request.getHeader(ProtocolHeaders.TOKEN.toString()));
        return userDao.findUser(userId);
    }
}
