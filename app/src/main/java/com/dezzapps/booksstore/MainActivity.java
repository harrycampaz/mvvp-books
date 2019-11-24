package com.dezzapps.booksstore;

import android.content.Intent;
import android.os.Bundle;

import com.dezzapps.booksstore.databinding.ActivityMainBinding;
import com.dezzapps.booksstore.model.Book;
import com.dezzapps.booksstore.model.Category;
import com.dezzapps.booksstore.viewmodel.MainActivityViewModel;
import com.dezzapps.booksstore.viewmodel.MainActivityViewModelFactory;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity {
    

    private MainActivityViewModel mainActivityViewModel;
    private ArrayList<Category> categoriesList;

    private ArrayList<Book> booksList;

    private ActivityMainBinding activityMainBinding;

    private  MainActivityClickHandlers handlers;

    private  Category selectedCategory;

    private RecyclerView bookRecyclerView;
    private BooksAdapter booksAdapter;

    private  int selectedBookId;

    public  static final int  ADD_BOOK_REQUEST_CODE = 1;
    public  static final int  EDIT_BOOK_REQUEST_CODE = 2;


    @Inject
    public MainActivityViewModelFactory mainActivityViewModelFactory;

    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        handlers = new MainActivityClickHandlers();

        activityMainBinding.setClickHandlers(handlers);
        
       // mainActivityViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
        

        App.getApp().getBookStoreComponent().inject(this);

        mainActivityViewModel = ViewModelProviders.of(this, mainActivityViewModelFactory).get(MainActivityViewModel.class);



        mainActivityViewModel.getAllCategories().observe(this, new Observer<List<Category>>() {
            @Override
            public void onChanged(List<Category> categories) {
                categoriesList=(ArrayList<Category>)categories;
                for(Category c:categories){

                    Log.i("MyTAG",c.getCategoryName());
                }
                showOnSpinner();
            }
        });



    }

    private void showOnSpinner(){

        ArrayAdapter<Category> categoryArrayAdapter=new ArrayAdapter<Category>(this,R.layout.spinner_item,categoriesList);
        categoryArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        activityMainBinding.setSpinnerAdapter(categoryArrayAdapter);

    }

    private void loadBooksArrayList(int categoryId){
        mainActivityViewModel.getAllBooksByCategory(categoryId).observe(this, new Observer<List<Book>>() {
            @Override
            public void onChanged(List<Book> books) {

                for(Book b: books){
                    Log.i(TAG, b.getBookName());
                }
                booksList = (ArrayList<Book>) books;
                loadRecyclerView();
            }

        });
    }

    private void loadRecyclerView(){

        bookRecyclerView = activityMainBinding.secondaryLayout.rvBooks;
        bookRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        bookRecyclerView.setHasFixedSize(true);


        booksAdapter = new BooksAdapter();
        bookRecyclerView.setAdapter(booksAdapter);

        booksAdapter.setBooks(booksList);

        booksAdapter.setListener(new BooksAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Book book) {

                selectedBookId = book.getId();
                Intent intent = new Intent(MainActivity.this, AddAndEditActivity.class);

                intent.putExtra(AddAndEditActivity.BOOK_ID, selectedBookId);
                intent.putExtra(AddAndEditActivity.BOOK_NAME, book.getBookName());
                intent.putExtra(AddAndEditActivity.UNIT_PRICE, book.getUnitPrice());

                startActivityForResult(intent, EDIT_BOOK_REQUEST_CODE);



            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                Book bookDelete = booksList.get(viewHolder.getAdapterPosition());
                mainActivityViewModel.deleteBook(bookDelete);

            }
        }).attachToRecyclerView(bookRecyclerView);

    }


    public  class  MainActivityClickHandlers{
        public  void onFABClicked(View view){
            //Toast.makeText(getApplicationContext(),"FAB Clic", Toast.LENGTH_LONG).show();

            Intent intent = new Intent(MainActivity.this, AddAndEditActivity.class);
            startActivityForResult(intent, ADD_BOOK_REQUEST_CODE);

        }

        public void onSelectItem(AdapterView<?> parent, View view, int pos, long id) {

            selectedCategory = (Category) parent.getItemAtPosition(pos);

            String message = " id is " + selectedCategory.getId() + "\n name is " + selectedCategory.getCategoryName() + "\n email is " + selectedCategory.getCategoryDescription();

            // Showing selected spinner item
           // Toast.makeText(parent.getContext(), message, Toast.LENGTH_LONG).show();

            loadBooksArrayList(selectedCategory.getId());

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        int selectCategoryId = selectedCategory.getId();
        if(requestCode == ADD_BOOK_REQUEST_CODE && resultCode == RESULT_OK){
            Book book = new Book();
            book.setCategoryId(selectCategoryId);
            book.setBookName(data.getStringExtra(AddAndEditActivity.BOOK_NAME));
            book.setUnitPrice(data.getStringExtra(AddAndEditActivity.UNIT_PRICE));

            mainActivityViewModel.addNewBook(book);


        }else if(requestCode == EDIT_BOOK_REQUEST_CODE && resultCode == RESULT_OK){

            Book book = new Book();
            book.setCategoryId(selectCategoryId);
            book.setBookName(data.getStringExtra(AddAndEditActivity.BOOK_NAME));
            book.setUnitPrice(data.getStringExtra(AddAndEditActivity.UNIT_PRICE));
            book.setId(selectedBookId);


            mainActivityViewModel.updateBook(book);
        }

    }
}
