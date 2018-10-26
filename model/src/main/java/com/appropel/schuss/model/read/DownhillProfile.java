package com.appropel.schuss.model.read;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

/**
 * A profile for downhill skiing.
 */
@Value.Immutable
@JsonDeserialize(builder = ImmutableDownhillProfile.Builder.class)
@JsonTypeName("DownhillProfile")
public interface DownhillProfile extends Profile
{
    /** Returns the skier type (experience level). */
    String getSkierType();

    /** Returns the skier's boot size. */
    String getBootSize();

    /** Returns the skier's ski size. */
    String getSkiSize();

    /** Returns the skier's pole length. */
    String getSkiPoleLength();

    /** Returns the skier's helmet size. */
    String getHelmetSize();
}
