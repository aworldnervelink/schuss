package com.appropel.schuss.dao.impl;

import com.appropel.schuss.dao.AddressDao;
import com.appropel.schuss.model.read.Address;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of User DAO.
 */
@Transactional
@Repository
@SuppressWarnings("checkstyle:DesignForExtension")
// Cannot be final for AOP enhancement
public class AddressDaoImpl extends DaoBase<Address> implements AddressDao
{
}
