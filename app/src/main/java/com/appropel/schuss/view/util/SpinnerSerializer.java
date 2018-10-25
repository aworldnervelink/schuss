package com.appropel.schuss.view.util;

import android.widget.Spinner;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Serializes an Android Spinner.
 */
public final class SpinnerSerializer extends StdSerializer<Spinner>
{
    /**
     * Constructs a new {@code SpinnerSerializer}.
     */
    SpinnerSerializer()
    {
        super(Spinner.class);
    }

    @Override
    public void serialize(final Spinner value, final JsonGenerator gen, final SerializerProvider provider)
            throws IOException
    {
        gen.writeString(value.getSelectedItem().toString());
    }
}
