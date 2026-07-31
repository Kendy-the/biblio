package com.biblio.observer;

import com.biblio.models.Book;

public interface BookListener {
    public void update(Object object);
    public void addedBook(Book book);
    public void editedBook(Book book);
    public void deletedBook(Book book);
}
