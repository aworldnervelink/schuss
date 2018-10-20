package com.appropel.schuss.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.appropel.schuss.R;
import com.appropel.schuss.controller.SchussController;
import com.appropel.schuss.dagger.DaggerWrapper;
import com.appropel.schuss.view.validation.ValidationAlertView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Pattern;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

/**
 * Fragment for editing a {@link com.appropel.schuss.model.read.Person}.
 */
public final class EditPersonFragment extends ValidatableFragment implements Validator.ValidationListener
{
    /** First name. */
    @Length(min = 1, max = 63)
    @BindView(R.id.first_name)
    @ValidationAlertView(R.id.first_name_validation)
    EditText firstNameEditText;

    /** Last name. */
    @Length(min = 1, max = 63)
    @BindView(R.id.last_name)
    @ValidationAlertView(R.id.last_name_validation)
    EditText lastNameEditText;

    /** Guardian first name. */
    @Length(min = 0, max = 63)
    @BindView(R.id.guardian_first_name)
    @ValidationAlertView(R.id.guardian_first_name_validation)
    EditText guardianFirstNameEditText;

    /** Guardian last name. */
    @Length(min = 0, max = 63)
    @BindView(R.id.guardian_last_name)
    @ValidationAlertView(R.id.guardian_last_name_validation)
    EditText guardianLastNameEditText;

    /** E-mail address. */
    @Length(min = 0, max = 255)
    @Email
    @BindView(R.id.email_address)
    @ValidationAlertView(R.id.email_address_validation)
    EditText emailAddressEditText;

    /** Phone number. */
    @Pattern(regex = "^\\(?([0-9]{3})\\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$")
    @BindView(R.id.phone_number)
    @ValidationAlertView(R.id.phone_number_validation)
    EditText phoneNumberEditText;

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
        final View view = inflater.inflate(R.layout.person, container, false);
        unbinder = ButterKnife.bind(this, view);
        DaggerWrapper.INSTANCE.getComponent().inject(this);

        // TODO: implement autocomplete for e-mail address

        return view;    // NOPMD TODO
    }

    /**
     * Calls the validator to update the state of the UI when user typing login or password.
     */
    @OnTextChanged({
            R.id.first_name,
            R.id.last_name,
            R.id.guardian_first_name,
            R.id.guardian_last_name,
            R.id.email_address,
            R.id.phone_number
    })
    protected void validate()
    {
        validateChange();
    }

    @Override
    public void onValidationSucceeded()
    {
        okButton.setEnabled(true);
    }

    @Override
    public void onValidationFailed(final List<ValidationError> errors)
    {
        okButton.setEnabled(false);
    }

    /**
     * Handler for when the user clicks the 'OK' button.
     */
    @OnClick(R.id.ok_button)
    public void onOkButtonClicked()
    {
//        final Person person = Person.of(
//                0,
//                firstNameEditText.getText().toString(),
//                lastNameEditText.getText().toString(),
//        )
//        controller.updatePerson(person);
    }

    @Override
    public void onDestroyView()
    {
        unbinder.unbind();
        super.onDestroyView();
    }
}
