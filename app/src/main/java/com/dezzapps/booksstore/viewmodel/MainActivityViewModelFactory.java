package com.dezzapps.booksstore.viewmodel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.dezzapps.booksstore.model.BooksStoreRepository;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private final BooksStoreRepository booksStoreRepository;

    @Inject
    public MainActivityViewModelFactory(BooksStoreRepository booksStoreRepository) {
        this.booksStoreRepository = booksStoreRepository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(booksStoreRepository);
    }
}
