package com.example.HavenBook.domain.interfaces;

import com.example.HavenBook.domain.Book;

import java.util.List;

public interface IBookService {
    List<Book> getBooksFromJson();
    Book getBookById(int id);
    void addBook(Book newBook);
    void updateBook(int id, Book updatedBook);
    void deleteBook(int id);
}
