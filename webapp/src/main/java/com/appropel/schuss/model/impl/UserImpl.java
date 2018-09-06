package com.appropel.schuss.model.impl;

import com.appropel.schuss.model.read.Device;
import com.appropel.schuss.model.read.User;
import com.google.common.collect.ImmutableSet;

import java.util.HashSet;
import java.util.Set;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * End user of the app.
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, table = "user", detachable = "true")
public final class UserImpl implements User
{
    /** Object unique identifier. */
    @Persistent(primaryKey = "true", valueStrategy = IdGeneratorStrategy.INCREMENT)
    private long id;

    /** E-mail address. */
    @Column(name = "email", length = 256)
    private String email;

    /** Devices. */
    @Persistent(defaultFetchGroup = "true")
    @Element(column = "user_id")
    private Set<DeviceImpl> devices = new HashSet<>();

    @Override
    public long getId()
    {
        return id;
    }

    @Override
    public String getEmail()
    {
        return email;
    }

    @Override
    public Set<Device> getDevices()
    {
        return ImmutableSet.copyOf(devices);
    }
}
