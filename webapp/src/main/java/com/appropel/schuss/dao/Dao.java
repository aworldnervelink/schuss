package com.appropel.schuss.dao;

import java.util.Collection;

/**
 * Interface to generic Dao.
 *
 * @param <T> type of the persistent entity
 */
public interface Dao<T>
{
    /**
     * Returns the particular object with the given ID.
     *
     * @param id identifier
     * @return object
     */
    T getById(long id);

    /**
     * Adds a new persistent entity.
     *
     * @param object object to make persistent
     * @return persisted object
     */
    T add(T object);

    /**
     * Deletes all persistent objects in the given collection.
     *
     * @param objects objects to delete
     */
    void deleteAll(Collection objects);

    /**
     * Deletes persistent object.
     *
     * @param object object to delete
     */
    void deleteObject(Object object);
}
