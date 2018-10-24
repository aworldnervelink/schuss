package com.appropel.schuss.rest;

import com.appropel.schuss.logic.UserLogic;
import com.appropel.schuss.model.read.Person;
import com.appropel.schuss.model.read.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Controller for Person methods.
 */
@RestController
@Transactional
@SuppressWarnings("checkstyle:DesignForExtension")  // Cannot be final for AOP enhancement
public class PersonController extends BaseController
{
    /** Path for this controller. */
    public static final String PERSON_PATH = "/person";

    /** Update person method. */
    public static final String UPDATE_PERSON_METHOD = "/updatePerson";

    /** User business logic. */
    private UserLogic userLogic;

    @Autowired
    public void setUserLogic(final UserLogic userLogic)
    {
        this.userLogic = userLogic;
    }

    /**
     * Creates or updates a Person connected to the current User.
     * @param person person
     * @param request servlet request
     * @param response servlet response
     * @throws IOException .
     */
    @RequestMapping(value = PERSON_PATH + UPDATE_PERSON_METHOD, method = RequestMethod.POST)
    public void updatePerson(@RequestBody final Person person,
                             final HttpServletRequest request,
                             final HttpServletResponse response) throws IOException
    {
        final User user = getCurrentUser(request);
        userLogic.updatePerson(user, person);
    }
}
