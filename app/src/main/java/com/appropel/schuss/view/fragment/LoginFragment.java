package com.appropel.schuss.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import com.appropel.schuss.BuildConfig;
import com.appropel.schuss.R;
import com.appropel.schuss.common.util.Preferences;
import com.appropel.schuss.controller.SchussController;
import com.appropel.schuss.dagger.DaggerWrapper;
import com.appropel.schuss.view.validation.ValidationAlertView;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.text.DateFormat;
import java.text.Format;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;
import butterknife.Unbinder;

/**
 * Fragment which allows the user to create a new account.
 */
public final class LoginFragment extends ValidatableFragment implements Validator.ValidationListener
{
    /** E-mail address. */
    @NotEmpty
    @Email
    @BindView(R.id.email_address_edit_text)
    @ValidationAlertView(R.id.email_validation)
    EditText emailAddressEditText;

    /** Password. */
    // https://www.lukew.com/ff/entry.asp?1653=
    @NotEmpty
    @Length(max = 70)
    @Password
    @BindView(R.id.password_edit_text)
    @ValidationAlertView(R.id.password_validation)
    EditText passwordEditText;

    /** Register button. */
    @BindView(R.id.register_button)
    Button registerButton;

    /** 'Create new' radio button. */
    @BindView(R.id.create_new_button)
    RadioButton createNewButton;

    /** 'Create new' radio button. */
    @BindView(R.id.sign_in_button)
    RadioButton signInButton;

    /** Version string. */
    @BindView(R.id.version)
    TextView versionView;

    /** Controller. */
    @Inject
    SchussController controller;

    /** Preferences. */
    @Inject
    Preferences preferences;

    /** View unbinder. */
    private Unbinder unbinder;

    /** Date formatter. */
    private final Format dateFormat = DateFormat.getDateInstance();

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.login, container, false);
        unbinder = ButterKnife.bind(this, view);
        DaggerWrapper.INSTANCE.getComponent().inject(this);

        final Date buildDate = new Date(BuildConfig.TIMESTAMP);
        versionView.setText(String.format(Locale.getDefault(), "Schuss %s (%d) %s",
                BuildConfig.VERSION_NAME, BuildConfig.VERSION_CODE, dateFormat.format(buildDate)));
        return view;
    }

    @Override
    public void onStart()
    {
        super.onStart();

        if (preferences.getEmailAddress() != null)
        {
            emailAddressEditText.setText(preferences.getEmailAddress(), TextView.BufferType.EDITABLE);
            signInButton.setChecked(true);
            passwordEditText.requestFocusFromTouch();
        }
    }

    /**
     * Calls the validator to update the state of the UI when user typing login or password.
     */
    @OnTextChanged({
            R.id.email_address_edit_text,
            R.id.password_edit_text
    })
    protected void validate()
    {
        validateChange();
    }

    @Override
    public void onValidationSucceeded()
    {
        registerButton.setEnabled(true);
    }

    @Override
    public void onValidationFailed(final List<ValidationError> errors)
    {
        registerButton.setEnabled(false);
    }

    /**
     * Handler for when the user clicks the 'Register' button.
     */
    @OnClick(R.id.register_button)
    public void onRegisterClicked()
    {
        final String emailAddress = emailAddressEditText.getText().toString();
        final String password = passwordEditText.getText().toString();
        final boolean newAccount = createNewButton.isChecked();
        controller.signIn(emailAddress, password, newAccount);
    }

    @Override
    public void onDestroyView()
    {
        unbinder.unbind();
        super.onDestroyView();
    }
}
