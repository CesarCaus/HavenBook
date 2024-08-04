package com.example.HavenBook.entities;

import com.example.HavenBook.domain.Genre;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe {@link Genre}.
 */
public class GenreTest {

    /**
     * Testa o construtor da classe {@link Genre}.
     */
    @Test
    public void testGenreConstructor() {
        Genre genre = new Genre("Science Fiction");

        assertNotNull(genre);
        assertEquals("Science Fiction", genre.getName());
    }

    /**
     * Testa os métodos setters e getters da classe {@link Genre}.
     */
    @Test
    public void testGenreSettersAndGetters() {
        Genre genre = new Genre();
        genre.setName("Fantasy");

        assertEquals("Fantasy", genre.getName());
    }
}
