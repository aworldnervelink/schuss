package com.appropel.schuss.model.impl;

import com.appropel.schuss.model.read.Device;
import com.appropel.schuss.model.read.User;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * Device running the application.
 */
@PersistenceCapable(identityType = IdentityType.APPLICATION, table = "device", detachable = "true")
public final class DeviceImpl implements Device
{
    /** Object unique identifier. */
    @Persistent(primaryKey = "true", valueStrategy = IdGeneratorStrategy.INCREMENT)
    private long id;

    /** Google Advertising ID. */
    @Column(length = 128, name = "advertising_id")
    private String advertisingId;

    /** Model name (manufacturer/model). */
    @Column(length = 256, name = "model_name")
    private String modelName;

    @Override
    public long getId()
    {
        return id;
    }

    @Override
    public String getAdvertisingId()
    {
        return advertisingId;
    }

    @Override
    public String getModelName()
    {
        return modelName;
    }
}
