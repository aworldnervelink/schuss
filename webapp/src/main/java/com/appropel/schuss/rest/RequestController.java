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
import com.appropel.schuss.model.util.JsonViews;

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
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(RequestController.class);

    /** Path for this controller. */
    public static final String REQUEST_PATH = "/request";

    /** Create path. */
    public static final String CREATE_METHOD = "/create";

    /** Get path. */
    public static final String GET_METHOD = "/get";

    /** Update path. */
    public static final String UPDATE_METHOD = "/update";

    /** Rental provider ID. */
    public static final String RENTAL_PROVIDER_ID_PARAM = "rentalProviderId";

    /** Request ID. */
    public static final String REQUEST_ID_PARAM = "requestId";

    /** Status param. */
    public static final String STATUS_PARAM = "status";

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
     * @throws IOException .
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
            writeAsJson(response.getOutputStream(), requests, JsonViews.RequestQueue.class);
        }
        else
        {
            LOGGER.warn("getRequests() called but user {} is not a WORKER!", user.getId());
        }
    }

    /**
     * Updates a particular httpServletRequest.
     * @param requestId httpServletRequest ID
     * @param status status
     * @param httpServletRequest HTTP httpServletRequest
     * @param response HTTP response
     * @throws IOException .
     */
    @RequestMapping(value = REQUEST_PATH + UPDATE_METHOD, method = RequestMethod.POST)
    public void updateRequest(@RequestParam(value = REQUEST_ID_PARAM) final long requestId,
                              @RequestParam(value = STATUS_PARAM) final Request.Status status,
                              final HttpServletRequest httpServletRequest,
                              final HttpServletResponse response) throws IOException
    {
        // TODO: should we check security to ensure that the user can access the httpServletRequest?
        final RequestImpl request = requestDao.getById(requestId);
        request.setStatus(status);

        // Pass the call along to getRequests so that an updated list is returned to the user.
        getRequests(httpServletRequest, response);
    }
}
