package com.appropel.schuss.dao.impl;

import com.appropel.schuss.dao.ProfileDao;
import com.appropel.schuss.model.impl.ProfileImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of User DAO.
 */
@Transactional
@Repository
@SuppressWarnings("checkstyle:DesignForExtension")
// Cannot be final for AOP enhancement
public class ProfileDaoImpl extends DaoBase<ProfileImpl> implements ProfileDao
{
}
