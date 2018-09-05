package com.appropel.schuss.view.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.appropel.schuss.view.validation.VisualValidator;

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

    // CSOFF: DesignForExtension - Android requires this style of overriding superclass methods.

    // See the last answer here: http://stackoverflow.com/questions/14194029/fragment-become-visible
    @Override
    public void onViewCreated(final View view, final Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);
        validator = new VisualValidator(this, view);
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
