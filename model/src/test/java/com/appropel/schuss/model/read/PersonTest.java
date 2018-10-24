package com.appropel.schuss.model.read;

import org.junit.Test;

public class PersonTest
{
    @Test
    public void testNormal()
    {
        ImmutablePerson.builder()
                .id(42)
                .firstName("Kevin")
                .lastName("Roll")
                .guardianFirstName("Ken")
                .guardianLastName("Roll")
                .phoneNumber("1234567890")
                .emailAddress("kroll@appropel.com")
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void testBadPhoneNumber()
    {
        ImmutablePerson.builder()
                .id(0)
                .firstName("Kevin")
                .lastName("Roll")
                .guardianFirstName("")
                .guardianLastName("")
                .phoneNumber("1234567")
                .emailAddress("")
                .build();
    }
}
