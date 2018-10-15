package com.appropel.schuss.rest;

import com.appropel.schuss.logic.UserLogic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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

    /** Email parameter. */
    public static final String EMAIL_PARAM = "email";

    /** Password parameter. */
    public static final String PASSWORD_PARAM = "password";

    /** Advertising ID parameter. */
    public static final String ADVERTISING_ID_PARAM = "advertisingId";

    /** Create new account parameter. */
    public static final String NEW_ACCOUNT_PARAM = "newAccount";

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
        userLogic.signIn(email, password, advertisingId, newAccount);
    }
}
