package com.appropel.schuss.rest;

import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Base class for controllers.
 */
@SuppressWarnings("checkstyle:DesignForExtension")  // Cannot be final for AOP enhancement
public abstract class BaseController
{
    /** Jackson object mapper. */
    private ObjectMapper objectMapper;

    @Autowired
    public void setObjectMapper(final ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }

    /**
     * Write object as prettified json into stream using default project's Jackson ObjectMapper.
     *
     * @param stream output stream.
     * @param object which would be serialized.
     * @throws IOException exception rises when problems occur in the process of the serialization.
     */
    void writeAsJson(final OutputStream stream, final Object object) throws IOException
    {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(stream, object);
    }

    /**
     * Write object as json into stream using given view.
     *
     * @param stream output stream.
     * @param object which would be serialized.
     * @param view   view class.
     * @throws IOException exception rises when problems occur in the process of the serialization.
     */
    void writeAsJson(final OutputStream stream, final Object object, final Class view) throws IOException
    {
        objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION).writerWithView(view).writeValue(stream, object);
    }
}
