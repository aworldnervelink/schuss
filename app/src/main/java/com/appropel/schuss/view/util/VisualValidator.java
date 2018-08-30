package com.appropel.schuss.view.util;

import android.content.Context;
import android.view.View;
import android.widget.EditText;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;

/**
 * Utility for hiding/showing warnings based upon the validation status of the view. Note that this class is NOT
 * thread safe.
 */
public final class VisualValidator extends Validator implements Validator.ValidationListener
{
    /** Target parent. */
    private final Object target;

    /** Android Context. */
    private final Context context;

    /**
     * Flag indicating that we are in "submit" mode, i.e. the user clicked a button and we want to show
     * all validation errors.
     */
    private boolean validatingForSubmit;

    /**
     * Constructs a new {@code ValidationFlagger} for the given target.
     * @param target target object.
     * @param context Android Context.
     */
    public VisualValidator(final Object target, final Context context)
    {
        super(target);
        this.target = target;
        this.context = context;

        setValidationListener(this);
    }

    @Override
    public void onValidationSucceeded()
    {
        if (validatingForSubmit)
        {
            if (target instanceof ValidationListener)
            {
                ((ValidationListener) target).onValidationSucceeded();
            }
            validatingForSubmit = false;
        }
    }

    @Override
    public void onValidationFailed(final List<ValidationError> errors)
    {
        if (validatingForSubmit)
        {
            for (ValidationError error : errors)
            {
                View view = error.getView();
                String message = error.getCollatedErrorMessage(context);

                if (view instanceof EditText)
                {
                    view.requestFocus();
                    ((EditText) view).setError(message, null);
                }
            }

            if (target instanceof ValidationListener)
            {
                ((ValidationListener) target).onValidationFailed(errors);
            }

            validatingForSubmit = false;
        }
    }

    /**
     * Performs a validation with special handling. This method is intended to be called on a button click when
     * the user wants to submit the form. If validation fails, an error dialog is displayed with the various
     * error messages from the failed validations. If the target object implements
     * {@link ValidationListener} then those methods will be called.
     */
    public void doSubmit()
    {
        // Implementation note: using a flag is not the ideal way to do this, but since we have to transfer control
        // to Saripaar and back, and validation may be asynchronous, this is the best solution I can see.
        validatingForSubmit = true;
        validate();
    }

}
