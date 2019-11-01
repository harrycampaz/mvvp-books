package com.dezzapps.booksstore.model;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BooksStoreRepository {

    private  CategoryDAO categoryDAO;
    private  BookDAO bookDAO;

    private LiveData<List<Category>> categories;

    private LiveData<List<Book>> books;


    public BooksStoreRepository(Application application){

        BooksStoreDatabase booksStoreDatabase = BooksStoreDatabase.getInstance(application);

        categoryDAO = booksStoreDatabase.categoryDAO();

        bookDAO = booksStoreDatabase.bookDAO();

    }

    public  LiveData<List<Category>> getCategories(){
        return  categoryDAO.getAllCategories();
    }

    public LiveData<List<Book>> getBooks(int category_id) {
        return bookDAO.getBook(category_id);
    }

    public  void insertCategory(final Category category){

       // new InsertCategoryAsyncTask(categoryDAO).execute(category);

        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {

                categoryDAO.insert(category);

            }
        });
    }


    public  void insertBook(final Book book){
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {

                bookDAO.insert(book);

            }
        });
    }


    public  void deleteCategory(final Category category){
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {

                categoryDAO.delete(category);

            }
        });
    }


    public  void deleteBook(final Book book){
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {

                bookDAO.delete(book);

            }
        });
    }



    public  void updateCategory(final Category category){
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {

                categoryDAO.update(category);

            }
        });
    }


    public  void updateBook(final Book book){
        Executor executor = Executors.newSingleThreadExecutor();

        executor.execute(new Runnable() {
            @Override
            public void run() {

                bookDAO.update(book);

            }
        });
    }


}
