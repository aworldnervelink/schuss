package com.appropel.schuss.model.impl;

import com.appropel.schuss.model.read.Address;
import com.appropel.schuss.model.read.Person;
import com.appropel.schuss.model.read.Profile;
import com.google.common.collect.ImmutableSet;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;

import javax.jdo.annotations.Column;
import javax.jdo.annotations.Element;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;

/**
 * Person with a rental need.
 */
@SuppressWarnings("PMD")
@PersistenceCapable(identityType = IdentityType.APPLICATION, table = "person", detachable = "true")
public final class PersonImpl extends Person implements Comparable<Person>
{
    /** Object unique identifier. */
    @Persistent(primaryKey = "true", valueStrategy = IdGeneratorStrategy.INCREMENT)
    private long id;

    /** First name. */
    @Column(name = "first_name", length = 64)
    private String firstName;

    /** Last name. */
    @Column(name = "last_name", length = 64)
    private String lastName;

    /** Guardian first name. */
    @Column(name = "guardian_first_name", length = 64)
    private String guardianFirstName;

    /** Guardian last name. */
    @Column(name = "guardian_last_name", length = 64)
    private String guardianLastName;

    /** Address. */
    @Column(name = "address_id")
    private AddressImpl address;

    /** E-mail address. */
    @Column(name = "email", length = 256)
    private String email;

    /** Phone number. */
    @Column(name = "phone_number", length = 24)
    private String phoneNumber;

    /** Profiles. */
    @Persistent(defaultFetchGroup = "true")
    @Element(column = "person_id")
    private Set<ProfileImpl> profiles = new TreeSet<ProfileImpl>();

    @Override
    public long getId()
    {
        return id;
    }

    @Override
    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(final String firstName)
    {
        this.firstName = firstName;
    }

    @Override
    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(final String lastName)
    {
        this.lastName = lastName;
    }

    @Override
    public String getGuardianFirstName()
    {
        return guardianFirstName;
    }

    public void setGuardianFirstName(final String guardianFirstName)
    {
        this.guardianFirstName = guardianFirstName;
    }

    @Override
    public String getGuardianLastName()
    {
        return guardianLastName;
    }

    public void setGuardianLastName(final String guardianLastName)
    {
        this.guardianLastName = guardianLastName;
    }

    @Override
    public Address getAddress()
    {
        return address;
    }

    public void setAddress(final AddressImpl address)
    {
        this.address = address;
    }

    @Override
    public String getEmailAddress()
    {
        return email;
    }

    public void setEmailAddress(final String email)
    {
        this.email = email;
    }

    @Override
    public String getPhoneNumber()
    {
        return phoneNumber;
    }

    /**
     * Sets the phone number. The number will be reformatted to (###) ###-####.
     * @param phoneNumber phone number
     */
    public void setPhoneNumber(final String phoneNumber)
    {
        if (StringUtils.hasText(phoneNumber))
        {
            final Matcher telephoneMatcher = TELEPHONE_PATTERN.matcher(phoneNumber);
            this.phoneNumber = telephoneMatcher.replaceAll("($1) $2-$3");
        }
        else
        {
            this.phoneNumber = "";
        }
    }

    public Set<Profile> getProfiles()
    {
        return ImmutableSet.copyOf(profiles);
    }

    /**
     * Adds a new profile.
     * @param profile profile
     */
    public void addProfile(final ProfileImpl profile)
    {
        profiles.add(profile);
    }

    @Override
    public int compareTo(final Person other)
    {
        return new CompareToBuilder()
                .append(getFirstName(), other.getFirstName())
                .append(getLastName(), other.getLastName())
                .append(getId(), other.getId())
                .toComparison();
    }
}
