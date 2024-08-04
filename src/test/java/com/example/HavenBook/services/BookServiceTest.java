package com.example.HavenBook.services;

import com.example.HavenBook.domain.Book;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe {@link BookService}.
 */
@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    private ObjectMapper mapper = new ObjectMapper();
    private Resource resource;

    /**
     * Configura o ambiente de teste antes de cada método de teste.
     * Inicializa o arquivo JSON com livros de exemplo.
     */
    @BeforeEach
    public void setup() throws IOException {
        resource = new ClassPathResource("static/books.json");

        List<Book> initialBooks = List.of(
                new Book("Title 1", "Author 1", new Date(), "Description 1", Arrays.asList("Genre1"), 200, 19.99),
                new Book("Title 2", "Author 2", new Date(), "Description 2", Arrays.asList("Genre2"), 250, 29.99)
        );
        mapper.writeValue(resource.getFile(), initialBooks);

        bookService.init();
    }

    /**
     * Testa o método {@link BookService#getBooksFromJson()}.
     * Verifica se os livros são carregados corretamente do arquivo JSON.
     */
    @Test
    public void testGetBooksFromJson() {
        List<Book> books = bookService.getBooksFromJson();
        assertNotNull(books);
        assertEquals(2, books.size());
        assertEquals("Title 1", books.get(0).getTitle());
    }

    /**
     * Testa o método {@link BookService#addBook(Book)}.
     * Verifica se um novo livro é adicionado corretamente.
     */
    @Test
    public void testAddBook() {
        Book newBook = new Book("New Title", "New Author", new Date(), "New Description", Arrays.asList("New Genre"), 300, 39.99);
        bookService.addBook(newBook);

        List<Book> books = bookService.getBooksFromJson();
        assertNotNull(books);
        assertEquals(3, books.size());
        assertEquals("New Title", books.get(2).getTitle());
    }

    /**
     * Testa o método {@link BookService#updateBook(int, Book)}.
     * Verifica se um livro existente é atualizado corretamente.
     */
    @Test
    public void testUpdateBook() {
        Book newBook = new Book("New Title", "New Author", new Date(), "New Description", Arrays.asList("New Genre"), 300, 39.99);
        bookService.addBook(newBook);

        Book updatedBook = new Book("Updated Title", "Updated Author", new Date(), "Updated Description", Arrays.asList("Updated Genre"), 350, 49.99);
        bookService.updateBook(newBook.getId(), updatedBook);

        Book retrievedBook = bookService.getBookById(newBook.getId());
        assertNotNull(retrievedBook);
        assertEquals("Updated Title", retrievedBook.getTitle());
    }

    /**
     * Testa o método {@link BookService#deleteBook(int)}.
     * Verifica se um livro é removido corretamente.
     */
    @Test
    public void testDeleteBook() {
        Book newBook = new Book("New Title", "New Author", new Date(), "New Description", Arrays.asList("New Genre"), 300, 39.99);
        bookService.addBook(newBook);

        bookService.deleteBook(newBook.getId());

        Book retrievedBook = bookService.getBookById(newBook.getId());
        assertNull(retrievedBook);
    }
}
