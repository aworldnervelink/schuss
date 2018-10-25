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
    TextViewSerializer()
    {
        super(TextView.class);
    }

    @Override
    public void serialize(final TextView value, final JsonGenerator gen, final SerializerProvider provider)
            throws IOException
    {
        gen.writeString(value.getText().toString());
    }
}
