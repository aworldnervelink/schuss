package com.appropel.schuss.rest;

import com.appropel.schuss.logic.UserLogic;
import com.appropel.schuss.logic.ServiceException;
import com.appropel.schuss.model.read.Person;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

/**
 * Controller for rental provider methods.
 */
@RestController
@Transactional
@SuppressWarnings("checkstyle:DesignForExtension")  // Cannot be final for AOP enhancement
public class UserController extends BaseController
{
    /** Path for this controller. */
    public static final String USER_PATH = "/user";

    /** Sign in method. */
    public static final String SIGN_IN_METHOD = "/signIn";

    /** Update person method. */
    public static final String UPDATE_PERSON_METHOD = "/updatePerson";

    /** Email parameter. */
    public static final String EMAIL_PARAM = "email";

    /** Password parameter. */
    public static final String PASSWORD_PARAM = "password";

    /** Advertising ID parameter. */
    public static final String ADVERTISING_ID_PARAM = "advertisingId";

    /** Create new account parameter. */
    public static final String NEW_ACCOUNT_PARAM = "newAccount";

    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    /** User business logic. */
    private UserLogic userLogic;

    @Autowired
    public void setUserLogic(final UserLogic userLogic)
    {
        this.userLogic = userLogic;
    }

    /**
     * Creates new user or logs in an existing one.
     *
     * @param email           email
     * @param password        encrypted password
     * @param advertisingId   advertising ID
     * @param newAccount      new account flag
     * @param response        servlet response
     * @throws IOException .
     */
    @RequestMapping(value = USER_PATH + SIGN_IN_METHOD, method = RequestMethod.POST)
    public void signIn(@RequestParam(value = EMAIL_PARAM) final String email,
                       @RequestParam(value = PASSWORD_PARAM) final String password,
                       @RequestParam(value = ADVERTISING_ID_PARAM) final String advertisingId,
                       @RequestParam(value = NEW_ACCOUNT_PARAM) final boolean newAccount,
                       final HttpServletResponse response) throws IOException
    {
        try
        {
            writeAsJson(response.getOutputStream(), userLogic.signIn(email, password, advertisingId, newAccount));
        }
        catch (ServiceException e)
        {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            writeAsJson(response.getOutputStream(), e.getServiceError());
        }
    }

    /**
     * Creates or updates a Person connected to the given User.
     * @param person person
     * @param response servlet response
     * @throws IOException .
     */
    @RequestMapping(value = USER_PATH + UPDATE_PERSON_METHOD, method = RequestMethod.POST)
    public void updatePerson(@RequestBody final Person person,
                             final HttpServletResponse response) throws IOException
    {
        LOGGER.info("person {}", person);
        LOGGER.info("person class {}", person.getClass().getName());
    }
}
