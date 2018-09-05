package com.appropel.schuss.view.validation;

import android.support.annotation.IdRes;
import android.view.View;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Annotation to tie a View to another View which alerts the user when validation has failed.
 */
@Target(FIELD)
@Retention(RUNTIME)
public @interface ValidationAlertView
{
    /**
     * View ID to which the field will be bound.
     * @return id
     */
    @IdRes int value() default View.NO_ID;
}
