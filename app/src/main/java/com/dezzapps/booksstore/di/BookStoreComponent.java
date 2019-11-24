package com.dezzapps.booksstore.di;


import com.dezzapps.booksstore.MainActivity;

import javax.inject.Singleton;

import dagger.Component;


@Singleton
@Component(modules = {AppMoodule.class, RepositoryModule.class})
public interface BookStoreComponent {


    void inject(MainActivity mainActivity);

}
