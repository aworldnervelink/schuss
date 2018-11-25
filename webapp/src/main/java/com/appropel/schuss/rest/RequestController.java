package com.appropel.schuss.rest;

import com.appropel.schuss.dao.ProfileDao;
import com.appropel.schuss.dao.RentalProviderDao;
import com.appropel.schuss.dao.RequestDao;
import com.appropel.schuss.model.impl.ProfileImpl;
import com.appropel.schuss.model.impl.RentalProviderImpl;
import com.appropel.schuss.model.impl.RequestImpl;
import com.appropel.schuss.model.impl.UserImpl;
import com.appropel.schuss.model.read.Profile;
import com.appropel.schuss.model.read.Request;
import com.appropel.schuss.model.read.User;

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
import javax.servlet.http.HttpServletResponse;

import static com.google.common.base.Preconditions.checkState;

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

    /** Get path. */
    public static final String GET_METHOD = "/get";

    /** Rental provider ID. */
    public static final String RENTAL_PROVIDER_ID_PARAM = "rentalProviderId";

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

    /**
     * Returns requests for the provider associated with the current user.
     * @param request HTTP request
     * @param response HTTP response
     * @throws IOException
     */
    @RequestMapping(value = REQUEST_PATH + GET_METHOD, method = RequestMethod.GET)
    public void getRequests(final HttpServletRequest request, final HttpServletResponse response) throws IOException
    {
        final UserImpl user = getCurrentUser(request);
        if (user.getRole() == User.Role.WORKER)
        {
            final RentalProviderImpl rentalProvider = (RentalProviderImpl) user.getRentalProvider();
            checkState(rentalProvider != null, "User must have a rental provider");
            final Set<RequestImpl> requests = requestDao.getRequestsForProvider(rentalProvider);
            writeAsJson(response.getOutputStream(), requests);
        }
    }
}
