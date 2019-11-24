package com.dezzapps.booksstore;

import android.app.Application;

import com.dezzapps.booksstore.di.AppMoodule;
import com.dezzapps.booksstore.di.BookStoreComponent;
import com.dezzapps.booksstore.di.DaggerBookStoreComponent;

public class App extends Application {

    private static  App app;
    private BookStoreComponent bookStoreComponent;


    @Override
    public void onCreate() {
        super.onCreate();

        app = this;

        bookStoreComponent = DaggerBookStoreComponent
        .builder()
        .appMoodule(new AppMoodule(this))
        .build();
    }

    public static App getApp() {
        return app;
    }

    public BookStoreComponent getBookStoreComponent() {
        return bookStoreComponent;
    }
}
