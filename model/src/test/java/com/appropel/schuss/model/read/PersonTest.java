package com.appropel.schuss.model.read;

import org.junit.Test;

public class PersonTest
{
    private final Address address = ImmutableAddress.builder()
            .id(1)
            .addressLine1("12 Captain's Way")
            .addressLine2("Suite 100")
            .city("West Ossipee")
            .stateProvince("NH")
            .postalCode("03890-0388")
            .build();

    @Test
    public void testNormal()
    {
        ImmutablePerson.builder()
                .id(42)
                .firstName("Kevin")
                .lastName("Roll")
                .guardianFirstName("Ken")
                .guardianLastName("Roll")
                .address(address)
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
                .address(address)
                .phoneNumber("1234567")
                .emailAddress("")
                .build();
    }
}
