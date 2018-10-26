package com.appropel.schuss.model.impl;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSortedSet;

import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * End user of the app.
 */
@SuppressWarnings("PMD")
@PersistenceCapable(identityType = IdentityType.APPLICATION, table = "user", detachable = "true")
public final class UserImpl
{
    /** Object unique identifier. */
    @Persistent(primaryKey = "true", valueStrategy = IdGeneratorStrategy.INCREMENT)
    private long id;

    /** E-mail address. */
    @Column(name = "email", length = 256)
    private String email;

    /** Password. */
    @Column(name = "password", length = 256)
    private String password;

    /** Devices. */
    @Persistent(defaultFetchGroup = "true")
    @Element(column = "user_id")
    private Set<DeviceImpl> devices = new HashSet<>();

    /** Devices. */
    @Persistent(defaultFetchGroup = "true")
    @Element(column = "user_id")
    private Set<PersonImpl> persons = new TreeSet<>();

    /**
     * Constructs a new {@code UserImpl}.
     * @param email e-mail address
     * @param password hashed password
     */
    public UserImpl(final String email, final String password)
    {
        this.email = checkNotNull(email);
        this.password = checkNotNull(password);
    }

    public long getId()
    {
        return id;
    }

    public String getEmail()
    {
        return email;
    }

    public String getPassword()
    {
        return password;
    }

    public Set<DeviceImpl> getDevices()
    {
        return ImmutableSet.copyOf(devices);
    }

    public Set<PersonImpl> getPersons()
    {
        return ImmutableSortedSet.copyOf(persons);
    }

    /**
     * Adds a new device.
     * @param device device
     */
    public void addDevice(final DeviceImpl device)
    {
        devices.add(device);
    }

    /**
     * Adds a new person.
     * @param person person
     */
    public void addPerson(final PersonImpl person)
    {
        persons.add(person);
    }
}
