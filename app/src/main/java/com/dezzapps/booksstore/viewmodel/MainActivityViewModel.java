package com.dezzapps.booksstore.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;


import com.dezzapps.booksstore.model.Book;
import com.dezzapps.booksstore.model.BooksStoreRepository;
import com.dezzapps.booksstore.model.Category;

import java.util.List;

public class MainActivityViewModel extends ViewModel {

    private BooksStoreRepository booksStoreRepository;


    private LiveData<List<Category>> allCategories;

    private LiveData<List<Book>> allBooksByCategory;

    public MainActivityViewModel(BooksStoreRepository booksStoreRepository) {

       this.booksStoreRepository =  booksStoreRepository;
    }

    public LiveData<List<Category>> getAllCategories() {

        allCategories = booksStoreRepository.getCategories();

        return allCategories;
    }

    public LiveData<List<Book>> getAllBooksByCategory(int categoryId) {
        allBooksByCategory =  booksStoreRepository.getBooks(categoryId);

        return allBooksByCategory;
    }

    public  void addNewBook(Book book){
        booksStoreRepository.insertBook(book);
    }

    public  void updateBook(Book book){
        booksStoreRepository.updateBook(book);
    }

    public  void deleteBook(Book book){
        booksStoreRepository.deleteBook(book);
    }
}
