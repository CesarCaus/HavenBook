package com.example.HavenBook.entities;

import com.example.HavenBook.domain.Book;
import org.junit.jupiter.api.Test;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe {@link Book}.
 */
public class BookTest {

    /**
     * Testa o construtor da classe {@link Book}.
     */
    @Test
    public void testBookConstructor() {
        Date publicationDate = new Date();
        Book book = new Book("Title", "Author", publicationDate, "Description", List.of("Genre1", "Genre2"), 300, 19.99);

        assertNotNull(book);
        assertEquals("Title", book.getTitle());
        assertEquals("Author", book.getAuthor());
        assertEquals(publicationDate, book.getPublicationDate());
        assertEquals("Description", book.getDescription());
        assertEquals(List.of("Genre1", "Genre2"), book.getGenres());
        assertEquals(300, book.getNumberOfPages());
        assertEquals(19.99, book.getValue());
    }

    /**
     * Testa os métodos setters e getters da classe {@link Book}.
     */
    @Test
    public void testBookSettersAndGetters() {
        Date publicationDate = new Date();
        Book book = new Book();
        book.setTitle("Updated Title");
        book.setAuthor("Updated Author");
        book.setPublicationDate(publicationDate);
        book.setDescription("Updated Description");
        book.setGenres(List.of("UpdatedGenre1"));
        book.setNumberOfPages(350);
        book.setValue(25.99);

        assertEquals("Updated Title", book.getTitle());
        assertEquals("Updated Author", book.getAuthor());
        assertEquals(publicationDate, book.getPublicationDate());
        assertEquals("Updated Description", book.getDescription());
        assertEquals(List.of("UpdatedGenre1"), book.getGenres());
        assertEquals(350, book.getNumberOfPages());
        assertEquals(25.99, book.getValue());
    }
}
