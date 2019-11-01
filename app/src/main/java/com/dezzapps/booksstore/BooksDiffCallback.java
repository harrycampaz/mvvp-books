package com.dezzapps.booksstore;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DiffUtil;

import com.dezzapps.booksstore.model.Book;

import java.util.ArrayList;

public class BooksDiffCallback extends DiffUtil.Callback {

    ArrayList<Book> oldBookList;
    ArrayList<Book> newBookList;

    public BooksDiffCallback(ArrayList<Book> oldBookList, ArrayList<Book> newBookList) {
        this.oldBookList = oldBookList;
        this.newBookList = newBookList;
    }

    @Override
    public int getOldListSize() {
        return oldBookList == null ? 0 : oldBookList.size();
    }

    @Override
    public int getNewListSize() {
        return newBookList == null ? 0 : newBookList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

        return oldBookList.get(oldItemPosition).getId() == newBookList.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        return oldBookList.get(oldItemPosition).equals(newBookList.get(newItemPosition).getId());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
