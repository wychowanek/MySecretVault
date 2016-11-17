package com.jakubwyc.mysecretvault;

import android.app.Application;

import com.jakubwyc.mysecretvault.repository.Repository;

import timber.log.Timber;

public class VaultApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Repository.initialize(this);
        Timber.plant(new Timber.DebugTree());

    }
}
