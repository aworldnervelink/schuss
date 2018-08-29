package com.appropel.schuss.dagger;

import com.appropel.schuss.view.activity.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dagger component which identifies non-Dagger objects to be injected into.
 */
@Component(modules = SchussModule.class)
@Singleton
public interface SchussComponent
{
    // CSOFF: EmptyLineSeparator
    // CSOFF: JavadocMethod
    void inject(MainActivity mainActivity);
}
