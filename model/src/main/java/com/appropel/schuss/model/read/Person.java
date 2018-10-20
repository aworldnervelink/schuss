package com.appropel.schuss.model.read;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import org.immutables.value.Value;

@Value.Immutable
@JsonDeserialize(builder = ImmutablePerson.Builder.class)
public interface Person
{
    /**
     * Returns the identifier.
     */
    long getId();

    /**
     * Returns the first name of the person.
     */
    String getFirstName();

    /**
     * Returns the last name of the person.
     */
    String getLastName();

    /**
     * Returns the first name of the person's guardian (may be empty).
     */
    String getGuardianFirstName();

    /**
     * Returns the last name of the person's guardian (may be empty).
     */
    String getGuardianLastName();

    // TODO: insert addresses here

    /**
     * Returns the person's e-mail address.
     */
    String getEmailAddress();

    /**
     * Returns the person's contact phone number.
     */
    String getPhoneNumber();

//    /**
//     * Constructs an immutable Person.
//     * @param id id
//     * @param firstName first name
//     * @param lastName last name
//     * @param guardianFirstName guardian first name
//     * @param guardianLastName guardian last name
//     * @param emailAddress e-mail address
//     * @param phoneNumber phone number
//     * @return immutable instance
//     */
//    public static Person of(final long id,
//                     final String firstName,
//                     final String lastName,
//                     final String guardianFirstName,
//                     final String guardianLastName,
//                     final String emailAddress,
//                     final String phoneNumber)
//    {
//        return ImmutablePerson.builder()
//                .id(id)
//                .firstName(firstName)
//                .lastName(lastName)
//                .guardianFirstName(guardianFirstName)
//                .guardianLastName(guardianLastName)
//                .emailAddress(emailAddress)
//                .phoneNumber(phoneNumber)
//                .build();
//    }
}
