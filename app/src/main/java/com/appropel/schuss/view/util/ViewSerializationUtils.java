package com.appropel.schuss.view.util;

import android.widget.Spinner;
import android.widget.TextView;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility methods for serializing JSON in and out of Android Views.
 */
public final class ViewSerializationUtils
{
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(ViewSerializationUtils.class);

    /** Object mapper. */
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static
    {
        OBJECT_MAPPER.disable(MapperFeature.AUTO_DETECT_CREATORS,
                MapperFeature.AUTO_DETECT_FIELDS,
                MapperFeature.AUTO_DETECT_GETTERS,
                MapperFeature.AUTO_DETECT_IS_GETTERS);
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        SimpleModule module = new SimpleModule();
        module.addSerializer(TextView.class, new TextViewSerializer());
        module.addSerializer(Spinner.class, new SpinnerSerializer());
        OBJECT_MAPPER.registerModule(module);
    }

    /**
     * Utility class.
     */
    private ViewSerializationUtils()
    {
        // Utility class.
    }

    /**
     * Reads a value from a target object.
     * @param target target object
     * @param valueType type of value to extract
     * @param <T> type of value to extract
     * @return read object
     */
    public static <T> T readValue(final Object target, final Class<T> valueType)
    {
        try
        {
            final String json = OBJECT_MAPPER.writeValueAsString(target);
            LOGGER.debug("JSON read from View: {}", json);
            return OBJECT_MAPPER.readValue(json, valueType);
        }
        catch (final Exception e)
        {
            LOGGER.error("Error extracting JSON from View", e);
            return null;
        }
    }
}
