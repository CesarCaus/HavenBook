package com.example.HavenBook.domain.interfaces;

import com.example.HavenBook.domain.Author;

import java.util.List;

public interface IAuthorService {
    List<Author> getAuthorsFromJson();
    Author getAuthorById(int id);
    void addAuthor(Author newAuthor);
    void updateAuthor(int id, Author updatedAuthor);
    void deleteAuthor(int id);
}
