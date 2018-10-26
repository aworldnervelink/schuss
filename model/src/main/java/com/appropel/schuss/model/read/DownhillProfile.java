package com.appropel.schuss.model.read;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import org.immutables.value.Value;

/**
 * A profile for downhill skiing.
 */
@Value.Immutable
@JsonDeserialize(builder = ImmutableDownhillProfile.Builder.class)
@JsonTypeName("DownhillProfile")
public abstract class DownhillProfile implements Profile
{
    /** Returns the skier type (experience level). */
    public abstract String getSkierType();

    /** Returns the skier's boot size. */
    public abstract String getBootSize();

    /** Returns the skier's ski size. */
    public abstract String getSkiSize();

    /** Returns the skier's pole length. */
    public abstract String getSkiPoleLength();

    /** Returns the skier's helmet size. */
    public abstract String getHelmetSize();
}
