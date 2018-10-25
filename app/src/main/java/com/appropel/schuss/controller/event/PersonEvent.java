package com.appropel.schuss.controller.event;

import com.appropel.schuss.model.read.Person;

import org.immutables.value.Value;

import java.util.List;

/**
 * Event posted when Person data is retrieved from the server.
 */
@Value.Immutable
public abstract class PersonEvent
{
    /**
     * Returns a list of Person.
     * @return list of Person
     */
    public abstract List<Person> getPersons();

    /**
     * Returns an initialized event.
     * @param list list of Person
     * @return event
     */
    public static PersonEvent of(final List<Person> list)
    {
        return ImmutablePersonEvent.builder().addAllPersons(list).build();
    }
}
