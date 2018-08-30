package com.appropel.schuss.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.appropel.schuss.view.util.VisualValidator;

/**
 * Fragment which supports Saripaar validation.
 */
public abstract class ValidatableFragment extends Fragment
{
    /** Visual validator. */
    private VisualValidator validator;

    /**
     * Calls the validator to update the state of the UI. This is intended for quick changes like the user typing
     * in an EditText, for example.
     */
    protected final void validateChange()
    {
        validator.validate();
    }

    /**
     * Calls the validator for a form submission, e.g. the user clicked on the submit button. This will display
     * an alert dialog if there are validation errors and will also call back the target object's handler methods.
     */
    protected final void validateSubmit()
    {
        validator.doSubmit();
    }

    // CSOFF: DesignForExtension - Android requires this style of overriding superclass methods.

    @Override
    public void onCreate(final Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        validator = new VisualValidator(this, getActivity());
    }

    // See the last answer here: http://stackoverflow.com/questions/14194029/fragment-become-visible
    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        view.getViewTreeObserver().addOnGlobalLayoutListener(
            new ViewTreeObserver.OnGlobalLayoutListener()
            {
                @Override
                public void onGlobalLayout()
                {
                    validateChange();
                    view.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
            });
    }
}
