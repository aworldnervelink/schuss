package com.appropel.schuss.model.read;

import org.immutables.value.Value;

/**
 * A profile for downhill skiing.
 */
@Value.Immutable
public abstract class DownhillProfile extends Profile
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
