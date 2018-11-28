package com.appropel.schuss.dagger;

import com.appropel.schuss.view.activity.MainActivity;
import com.appropel.schuss.view.fragment.DownhillProfileFragment;
import com.appropel.schuss.view.fragment.EditPersonFragment;
import com.appropel.schuss.view.fragment.EmployeeHomeFragment;
import com.appropel.schuss.view.fragment.HomeFragment;
import com.appropel.schuss.view.fragment.LoginFragment;
import com.appropel.schuss.view.fragment.RentalRequestFragment;
import com.appropel.schuss.view.fragment.RequestDetailsFragment;

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
    void inject(DownhillProfileFragment downhillProfileFragment);
    void inject(EditPersonFragment editPersonFragment);
    void inject(EmployeeHomeFragment employeeHomeFragment);
    void inject(HomeFragment loginFragment);
    void inject(LoginFragment loginFragment);
    void inject(MainActivity mainActivity);
    void inject(RentalRequestFragment rentalRequestFragment);
    void inject(RequestDetailsFragment requestDetailsFragment);
}
