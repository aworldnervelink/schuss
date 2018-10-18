package com.appropel.schuss.model.impl;

import com.appropel.schuss.model.read.Person;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * Person with a rental need.
 */
@SuppressWarnings("PMD")
@PersistenceCapable(identityType = IdentityType.APPLICATION, table = "person", detachable = "true")
public final class PersonImpl implements Person
{
    /** Object unique identifier. */
    @Persistent(primaryKey = "true", valueStrategy = IdGeneratorStrategy.INCREMENT)
    private long id;

    /** First name. */
    @Column(name = "first_name", length = 64)
    private String firstName;

    /** Last name. */
    @Column(name = "last_name", length = 64)
    private String lastName;

    /** Guardian first name. */
    @Column(name = "guardian_first_name", length = 64)
    private String guardianFirstName;

    /** Guardian last name. */
    @Column(name = "guardian_last_name", length = 64)
    private String guardianLastName;

    /** E-mail address. */
    @Column(name = "email", length = 256)
    private String email;

    /** Phone number. */
    @Column(name = "phone_number", length = 24)
    private String phoneNumber;

    @Override
    public long getId()
    {
        return id;
    }

    @Override
    public String getFirstName()
    {
        return firstName;
    }

    @Override
    public String getLastName()
    {
        return lastName;
    }

    @Override
    public String getGuardianFirstName()
    {
        return guardianFirstName;
    }

    @Override
    public String getGuardianLastName()
    {
        return guardianLastName;
    }

    @Override
    public String getEmailAddress()
    {
        return email;
    }

    @Override
    public String getPhoneNumber()
    {
        return phoneNumber;
    }
}
