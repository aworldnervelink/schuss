package com.appropel.schuss.model.impl;

import com.appropel.schuss.model.read.Profile;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * Implementation of abstract Profile.
 */
@SuppressWarnings({"PMD", "DesignForExtension"})
@PersistenceCapable(identityType = IdentityType.APPLICATION, detachable = "true")
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public abstract class ProfileImpl implements Profile
{
    /** Object unique identifier. */
    @Persistent(primaryKey = "true", valueStrategy = IdGeneratorStrategy.INCREMENT)
    private long id;


    @Override
    public long getId()
    {
        return id;
    }
}
