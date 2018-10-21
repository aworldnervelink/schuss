package com.appropel.schuss.view.util;

import android.widget.TextView;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * Serializes an Android TextView.
 */
public final class TextViewSerializer extends StdSerializer<TextView>
{
    /**
     * Constructs a new {@code TextViewSerializer}.
     */
    public TextViewSerializer()
    {
        this(null);
    }

    /**
     * Constructs a new {@code TextViewSerializer}.
     * @param handledType class
     */
    public TextViewSerializer(final Class<TextView> handledType)
    {
        super(handledType);
    }

    @Override
    public void serialize(final TextView value, final JsonGenerator jgen, final SerializerProvider provider)
            throws IOException
    {
        jgen.writeString(value.getText().toString());
    }
}
