package com.example.HavenBook.entities;

import com.example.HavenBook.domain.Author;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe {@link Author}.
 */
public class AuthorTest {

    /**
     * Testa o construtor da classe {@link Author}.
     */
    @Test
    public void testAuthorConstructor() {
        Author author = new Author("J.K. Rowling");

        assertNotNull(author);
        assertEquals("J.K. Rowling", author.getName());
    }

    /**
     * Testa os métodos setters e getters da classe {@link Author}.
     */
    @Test
    public void testAuthorSettersAndGetters() {
        Author author = new Author();
        author.setName("George R.R. Martin");

        assertEquals("George R.R. Martin", author.getName());
    }
}
