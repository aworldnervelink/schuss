package com.appropel.schuss.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.appropel.schuss.R;
import com.appropel.schuss.dagger.DaggerWrapper;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Fragment which allows the user to create a new account.
 */
public final class CreateAccountFragment extends ValidatableFragment implements Validator.ValidationListener
{
    /** E-mail address. */
    @NotEmpty
    @Email
    @BindView(R.id.email_address_edit_text)
    EditText emailAddressEditText;

    /** View unbinder. */
    private Unbinder unbinder;

    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container, final Bundle savedInstanceState)
    {
        final View view = inflater.inflate(R.layout.login, container, false);
        unbinder = ButterKnife.bind(this, view);
        DaggerWrapper.INSTANCE.getComponent().inject(this);

        return view;
    }

    @Override
    public void onValidationSucceeded()
    {
        // TODO
    }

    @Override
    public void onValidationFailed(final List<ValidationError> errors)
    {
        // TODO
    }

    @Override
    public void onDestroyView()
    {
        unbinder.unbind();
        super.onDestroyView();
    }
}
