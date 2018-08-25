package com.appropel.schuss.dagger;

import com.appropel.schuss.MainActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Dagger component which identifies non-Dagger objects to be injected into.
 */
@Component(modules = SchussModule.class)
@Singleton
public interface SchussComponent
{
    void inject(MainActivity mainActivity);
}
