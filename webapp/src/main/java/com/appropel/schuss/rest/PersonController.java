package com.appropel.schuss.rest;

import com.appropel.schuss.dao.PersonDao;
import com.appropel.schuss.logic.PersonLogic;
import com.appropel.schuss.logic.UserLogic;
import com.appropel.schuss.model.impl.PersonImpl;
import com.appropel.schuss.model.impl.ProfileImpl;
import com.appropel.schuss.model.impl.UserImpl;
import com.appropel.schuss.model.read.Person;
import com.appropel.schuss.model.read.Profile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    /** Get persons method. */
    public static final String GET_PERSONS_METHOD = "/getPersons";

    /** Update profile method. */
    public static final String UPDATE_PROFILE_METHOD = "/updateProfile";

    /** Parameter for Person ID. */
    public static final String PERSON_ID_PARAM = "personId";

    /** User business logic. */
    private UserLogic userLogic;

    /** Person DAO. */
    private PersonDao personDao;

    /** Person business logic. */
    private PersonLogic personLogic;

    @Autowired
    public void setUserLogic(final UserLogic userLogic)
    {
        this.userLogic = userLogic;
    }

    @Autowired
    public void setPersonDao(final PersonDao personDao)
    {
        this.personDao = personDao;
    }

    @Autowired
    public void setPersonLogic(final PersonLogic personLogic)
    {
        this.personLogic = personLogic;
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
        final UserImpl user = getCurrentUser(request);
        final PersonImpl newPerson = PersonImpl.from(person);
        userLogic.updatePerson(user, newPerson);
    }

    /**
     * Returns the list of Persons connected to the current user.
     * @param request servlet request
     * @param response servlet response
     * @throws IOException .
     */
    @RequestMapping(value = PERSON_PATH + GET_PERSONS_METHOD, method = RequestMethod.GET)
    public void getPersons(final HttpServletRequest request, final HttpServletResponse response) throws IOException
    {
        final UserImpl user = getCurrentUser(request);
        writeAsJson(response.getOutputStream(), user.getPersons());
    }

    /**
     * Creates or updates a Profile connected to the given Person.
     * @param personId person ID
     * @param profile profile
     * @param request servlet request
     */
    @RequestMapping(value = PERSON_PATH + UPDATE_PROFILE_METHOD, method = RequestMethod.POST)
    public void updateProfile(@RequestParam(value = PERSON_ID_PARAM) final long personId,
                              @RequestBody final Profile profile,
                              final HttpServletRequest request)
    {
        final UserImpl user = getCurrentUser(request);
        final PersonImpl person = personDao.getById(personId);
        final ProfileImpl newProfile = ProfileImpl.from(profile);
        personLogic.updateProfile(user, person, newProfile);
    }
}
