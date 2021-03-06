package com.appropel.schuss.rest;

import com.appropel.schuss.dao.RentalProviderDao;
import com.appropel.schuss.model.impl.RentalProviderImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletResponse;

/**
 * Controller for rental provider methods.
 */
@RestController
@Transactional
@SuppressWarnings("checkstyle:DesignForExtension")  // Cannot be final for AOP enhancement
public class RentalProviderController extends BaseController
{
    /** Path for this controller. */
    public static final String RENTAL_PROVIDER_PATH = "/rentalProvider";

    /** DAO for rentals. */
    private RentalProviderDao rentalProviderDao;

    @Autowired
    public void setRentalProviderDao(final RentalProviderDao rentalProviderDao)
    {
        this.rentalProviderDao = rentalProviderDao;
    }

    /**
     * Get method for rental providers.
     *
     * @param response servlet response
     * @throws IOException .
     */
    @RequestMapping(value = RENTAL_PROVIDER_PATH, method = RequestMethod.GET)
    public void getRentalProviders(final ServletResponse response)
            throws IOException
    {
        final List<RentalProviderImpl> rentalProviders = rentalProviderDao.getRentalProviders();
        writeAsJson(response.getOutputStream(), rentalProviders);
    }
}
