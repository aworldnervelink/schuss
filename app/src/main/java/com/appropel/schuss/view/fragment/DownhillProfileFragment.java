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
import com.appropel.schuss.model.read.Person;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment for editing a {@link com.appropel.schuss.model.read.DownhillProfile}.
 */
public final class DownhillProfileFragment extends Fragment
// ValidatableFragment implements Validator.ValidationListener
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
    Spinner skiLength;

    /** Pole length. */
    @BindView(R.id.pole_length)
    Spinner poleLength;

    /** Helmet size. */
    @BindView(R.id.helmet_size)
    Spinner helmetSize;

    /** OK button. */
    @BindView(R.id.ok_button)
    Button okButton;

    /** View unbinder. */
    private Unbinder unbinder;

    /** Controller. */
    @Inject
    SchussController controller;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.downhill_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        DaggerWrapper.INSTANCE.getComponent().inject(this);
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();
        final Person person = (Person) getArguments().getSerializable(PERSON_KEY);
        firstNameEditText.setText(person.getFirstName());
        lastNameEditText.setText(person.getLastName());
    }

    @Override
    public void onDestroyView()
    {
        unbinder.unbind();
        super.onDestroyView();
    }
}
