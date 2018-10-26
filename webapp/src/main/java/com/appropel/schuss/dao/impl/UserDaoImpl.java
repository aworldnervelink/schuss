package com.appropel.schuss.dao.impl;

import com.appropel.schuss.dao.UserDao;
import com.appropel.schuss.model.impl.QUserImpl;
import com.appropel.schuss.model.impl.UserImpl;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.jdo.JDOQLTypedQuery;
import javax.jdo.PersistenceManager;

/**
 * Implementation of User DAO.
 */
@Transactional
@Repository
@SuppressWarnings("checkstyle:DesignForExtension")
// Cannot be final for AOP enhancement
public class UserDaoImpl extends DaoBase<UserImpl> implements UserDao
{
    @Override
    public UserImpl findUser(final String emailAddress)
    {
        final PersistenceManager pm = persistenceManagerFactory.getPersistenceManager();
        final JDOQLTypedQuery<UserImpl> query = pm.newJDOQLTypedQuery(UserImpl.class);
        final QUserImpl qUser = QUserImpl.candidate();
        return query.filter(qUser.email.eq(emailAddress)).executeUnique();
    }
}
