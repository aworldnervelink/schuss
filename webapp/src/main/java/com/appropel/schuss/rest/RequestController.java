package com.appropel.schuss.rest;

import com.appropel.schuss.dao.ProfileDao;
import com.appropel.schuss.dao.RentalProviderDao;
import com.appropel.schuss.dao.RequestDao;
import com.appropel.schuss.model.impl.ProfileImpl;
import com.appropel.schuss.model.impl.RentalProviderImpl;
import com.appropel.schuss.model.impl.RequestImpl;
import com.appropel.schuss.model.read.Profile;
import com.appropel.schuss.model.read.Request;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Set;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller for request methods.
 */
@RestController
@Transactional
@SuppressWarnings("checkstyle:DesignForExtension")  // Cannot be final for AOP enhancement
public class RequestController extends BaseController
{
    /** Path for this controller. */
    public static final String REQUEST_PATH = "/request";

    /** Create path. */
    public static final String CREATE_METHOD = "/create";

    /** Rental Provider DAO. */
    private RentalProviderDao rentalProviderDao;

    /** Profile DAO. */
    private ProfileDao profileDao;

    /** Request DAO. */
    private RequestDao requestDao;

    @Autowired
    public void setRentalProviderDao(final RentalProviderDao rentalProviderDao)
    {
        this.rentalProviderDao = rentalProviderDao;
    }

    @Autowired
    public void setProfileDao(final ProfileDao profileDao)
    {
        this.profileDao = profileDao;
    }

    @Autowired
    public void setRequestDao(final RequestDao requestDao)
    {
        this.requestDao = requestDao;
    }

    /**
     * Creates a new Request.
     * @param request new request
     * @param httpRequest HTTP request
     * @throws IOException .
     */
    @RequestMapping(value = REQUEST_PATH + CREATE_METHOD, method = RequestMethod.POST)
    public void create(@RequestBody final Request request, final HttpServletRequest httpRequest)
    {
        final RentalProviderImpl rentalProvider = rentalProviderDao.getById(request.getRentalProvider().getId());
        final Set<ProfileImpl> profiles = new TreeSet<ProfileImpl>();
        for (Profile profile : request.getProfiles())
        {
            profiles.add(profileDao.getById(profile.getId()));
        }
        final RequestImpl newRequest = RequestImpl.from(request, getCurrentUser(httpRequest), rentalProvider, profiles);
        requestDao.add(newRequest);
    }
}