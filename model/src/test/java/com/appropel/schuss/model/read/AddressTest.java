package com.appropel.schuss.model.read;

import org.junit.Test;

public class AddressTest
{
    @Test
    public void testNormal()
    {
        ImmutableAddress.builder()
                .id(1)
                .addressLine1("12 Captain's Way")
                .addressLine2("Suite 100")
                .city("West Ossipee")
                .stateProvince("NH")
                .postalCode("03890-0388")
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void testBadPostalCode()
    {
        ImmutableAddress.builder()
                .id(1)
                .addressLine1("12 Captain's Way")
                .addressLine2("Suite 100")
                .city("West Ossipee")
                .stateProvince("NH")
                .postalCode("0389")
                .build();
    }
}
