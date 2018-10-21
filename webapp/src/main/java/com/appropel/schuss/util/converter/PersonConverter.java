package com.appropel.schuss.util.converter;

import com.appropel.schuss.model.read.Person;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class PersonConverter implements Converter<String, Person>
{
    /** Object mapper. */
    private final ObjectMapper objectMapper;

    public PersonConverter(ObjectMapper objectMapper)
    {
        this.objectMapper = objectMapper;
    }

    @Override
    public Person convert(final String source)
    {
        try
        {
            return objectMapper.readValue(source, Person.class);
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }
}