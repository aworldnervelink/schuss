package com.appropel.schuss.view.validation;

import android.view.View;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Utility for hiding/showing various views based upon the validation status of another view.
 */
public final class VisualValidator extends Validator implements Validator.ValidationListener
{
    /** Logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger(VisualValidator.class);

    /** Target parent. */
    private final Object target;

    /** Parent view for finding alerts. */
    private final View parentView;

    /**
     * Constructs a new {@code ValidationFlagger} for the given target.
     * @param target target object.
     * @param parentView parent View.
     */
    public VisualValidator(final Object target, final View parentView)
    {
        super(target);
        this.target = target;
        this.parentView = parentView;
        setValidationListener(this);
    }

    @Override
    public void onValidationSucceeded()
    {
        updateAlerts(null);
        if (target instanceof ValidationListener)
        {
            ((ValidationListener) target).onValidationSucceeded();
        }
    }

    @Override
    public void onValidationFailed(final List<ValidationError> errors)
    {
        updateAlerts(errors);
        if (target instanceof ValidationListener)
        {
            ((ValidationListener) target).onValidationFailed(errors);
        }
    }

    /**
     * Updates the alert Views using the given list of errors. Each view is made visible if the corresponding
     * validating view is present in the error list. If errors is null all views are hidden.
     * @param errors list of validation errors.
     */
    public void updateAlerts(final List<ValidationError> errors)
    {
        // Gather up views with validation errors.
        final Set<View> errorViews = new HashSet<>();
        if (errors != null)
        {
            for (final ValidationError error : errors)
            {
                errorViews.add(error.getView());
            }
        }

        // Scan target class for annotated views
        final Field[] declaredFields = target.getClass().getDeclaredFields();
        for (final Field field : declaredFields)
        {
            if (View.class.isAssignableFrom(field.getType()))
            {
                final ValidationAlertView vav = field.getAnnotation(ValidationAlertView.class);
                if (vav != null)
                {
                    try
                    {
                        field.setAccessible(true);
                        final View targetView = (View) field.get(target);
                        final View alertView = parentView.findViewById(vav.value());
                        alertView.setVisibility(errorViews.contains(targetView) ? View.VISIBLE : View.INVISIBLE);
                    }
                    catch (IllegalAccessException e)
                    {
                        LOGGER.error("Problem highlighting validation view", e);
                    }
                }
            }
        }
    }
}
