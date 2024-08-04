package com.example.HavenBook.services;

import com.example.HavenBook.domain.Author;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe {@link AuthorService}.
 */
@SpringBootTest
public class AuthorServiceTest {

    @Autowired
    private AuthorService authorService;

    private ObjectMapper mapper = new ObjectMapper();
    private Resource resource;

    /**
     * Configura o ambiente de teste antes de cada método de teste.
     * Inicializa o arquivo JSON com autores de exemplo.
     */
    @BeforeEach
    public void setup() throws IOException {
        resource = new ClassPathResource("static/authors.json");

        List<Author> initialAuthors = List.of(new Author("Author 1"), new Author("Author 2"));
        mapper.writeValue(resource.getFile(), initialAuthors);

        authorService.init();
    }

    /**
     * Testa o método {@link AuthorService#getAuthorsFromJson()}.
     * Verifica se os autores são carregados corretamente do arquivo JSON.
     */
    @Test
    public void testGetAuthorsFromJson() {
        List<Author> authors = authorService.getAuthorsFromJson();
        assertNotNull(authors);
        assertEquals(2, authors.size());
        assertEquals("Author 1", authors.get(0).getName());
    }

    /**
     * Testa o método {@link AuthorService#addAuthor(Author)}.
     * Verifica se um novo autor é adicionado corretamente.
     */
    @Test
    public void testAddAuthor() {
        Author newAuthor = new Author("New Author");
        authorService.addAuthor(newAuthor);

        List<Author> authors = authorService.getAuthorsFromJson();
        assertNotNull(authors);
        assertEquals(3, authors.size());
        assertEquals("New Author", authors.get(2).getName());
    }

    /**
     * Testa o método {@link AuthorService#updateAuthor(int, Author)}.
     * Verifica se um autor existente é atualizado corretamente.
     */
    @Test
    public void testUpdateAuthor() {
        Author newAuthor = new Author("New Author");
        authorService.addAuthor(newAuthor);

        Author updatedAuthor = new Author("Updated Author");
        authorService.updateAuthor(newAuthor.getId(), updatedAuthor);

        Author retrievedAuthor = authorService.getAuthorById(newAuthor.getId());
        assertNotNull(retrievedAuthor);
        assertEquals("Updated Author", retrievedAuthor.getName());
    }

    /**
     * Testa o método {@link AuthorService#deleteAuthor(int)}.
     * Verifica se um autor é removido corretamente.
     */
    @Test
    public void testDeleteAuthor() {
        Author newAuthor = new Author("New Author");
        authorService.addAuthor(newAuthor);

        authorService.deleteAuthor(newAuthor.getId());

        Author retrievedAuthor = authorService.getAuthorById(newAuthor.getId());
        assertNull(retrievedAuthor);
    }
}
