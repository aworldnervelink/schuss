package com.appropel.schuss.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.appropel.schuss.R;
import com.appropel.schuss.controller.SchussController;
import com.appropel.schuss.dagger.DaggerWrapper;
import com.appropel.schuss.model.read.DownhillProfile;
import com.appropel.schuss.model.read.Person;
import com.appropel.schuss.model.read.Profile;
import com.appropel.schuss.view.util.ViewSerializationUtils;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Fragment for editing a {@link com.appropel.schuss.model.read.DownhillProfile}.
 */
public final class DownhillProfileFragment extends Fragment
{
    /** Key for finding a Person in argument Bundle. */
    public static final String PERSON_KEY = "person";

    /** First name. */
    @BindView(R.id.first_name)
    TextView firstNameEditText;

    /** Last name. */
    @BindView(R.id.last_name)
    TextView lastNameEditText;

    /** Skier type. */
    @BindView(R.id.skier_type)
    @JsonProperty("skierType")
    Spinner skierTypeSpinner;

    /** Boot size. */
    @BindView(R.id.boot_size)
    @JsonProperty("bootSize")
    Spinner bootSize;

    /** Ski length. */
    @BindView(R.id.ski_length)
    @JsonProperty("skiSize")
    Spinner skiLength;

    /** Pole length. */
    @BindView(R.id.pole_length)
    @JsonProperty("skiPoleLength")
    Spinner poleLength;

    /** Helmet size. */
    @BindView(R.id.helmet_size)
    @JsonProperty("helmetSize")
    Spinner helmetSize;

    /** OK button. */
    @BindView(R.id.ok_button)
    Button okButton;

    /** View unbinder. */
    private Unbinder unbinder;

    /** Controller. */
    @Inject
    SchussController controller;

    /** Person this profile is connected to. */
    Person person;

    /** Identifier of profile being edited. */
    @JsonProperty("id")
    long id;

    /** Type of profile. */
    @JsonProperty("profileType")
    final Profile.Type type = Profile.Type.DOWNHILL;

    /** Class being serialized. */
    @JsonProperty("@type")
    final String typeName = "DownhillProfile";  // NOPMD: cannot be static, Jackson won't work!

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.downhill_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        DaggerWrapper.INSTANCE.getComponent().inject(this);
        person = (Person) getArguments().getSerializable(PERSON_KEY);
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        firstNameEditText.setText(person.getFirstName());
        lastNameEditText.setText(person.getLastName());
    }

    /**
     * Handler for when the user clicks the 'OK' button.
     */
    @OnClick(R.id.ok_button)
    public void onOkButtonClicked()
    {
        final DownhillProfile downhillProfile = ViewSerializationUtils.readValue(this, DownhillProfile.class);
        controller.updateProfile(person, downhillProfile);
    }

    @Override
    public void onDestroyView()
    {
        unbinder.unbind();
        super.onDestroyView();
    }
}
