package com.appropel.schuss.dao.impl;

import com.appropel.schuss.dao.RequestDao;
import com.appropel.schuss.model.impl.RequestImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of User DAO.
 */
@Transactional
@Repository
@SuppressWarnings("checkstyle:DesignForExtension")
// Cannot be final for AOP enhancement
public class RequestDaoImpl extends DaoBase<RequestImpl> implements RequestDao
{
}
