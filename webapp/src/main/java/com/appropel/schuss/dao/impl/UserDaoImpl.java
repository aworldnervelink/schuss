package com.appropel.schuss.dao.impl;

import com.appropel.schuss.dao.UserDao;
import com.appropel.schuss.model.read.User;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of User DAO.
 */
@Transactional
@Repository
@SuppressWarnings("checkstyle:DesignForExtension")
// Cannot be final for AOP enhancement
public class UserDaoImpl extends DaoBase<User> implements UserDao
{
}
