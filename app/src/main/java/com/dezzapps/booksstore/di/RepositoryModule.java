package com.dezzapps.booksstore.di;

import android.app.Application;

import com.dezzapps.booksstore.model.BooksStoreRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class RepositoryModule {

    @Singleton
    @Provides
    BooksStoreRepository providesBookStoreRepository(Application application){
        return new BooksStoreRepository(application);
    }
}
