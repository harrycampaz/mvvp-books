package com.dezzapps.booksstore.di;

import android.app.Application;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppMoodule {

    private Application application;

    public AppMoodule(Application application) {
        this.application = application;
    }

    @Singleton
    @Provides
    Application providesApplication(){
        return application;
    }
}
