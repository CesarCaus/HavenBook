package com.example.HavenBook.controllers;

import com.example.HavenBook.domain.Book;
import com.example.HavenBook.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciar livros.
 * Esta classe fornece endpoints para criar, ler, atualizar e excluir livros.
 */
@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    /**
     * Construtor para inicializar o controlador com o serviço de livros.
     *
     * @param bookService O serviço de livros a ser usado pelo controlador.
     */
    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    /**
     * Obtém todos os livros.
     *
     * @return Uma lista de livros.
     */
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getBooksFromJson();
    }

    /**
     * Obtém um livro pelo ID.
     *
     * @param id O ID do livro a ser recuperado.
     * @return O livro correspondente ao ID fornecido.
     */
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) {
        return bookService.getBookById(id);
    }

    /**
     * Adiciona um novo livro.
     *
     * @param newBook O livro a ser adicionado.
     */
    @PostMapping
    public void addBook(@RequestBody Book newBook) {
        bookService.addBook(newBook);
    }

    /**
     * Atualiza um livro existente.
     *
     * @param id           O ID do livro a ser atualizado.
     * @param updatedBook  O livro com as novas informações.
     */
    @PutMapping("/{id}")
    public void updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        bookService.updateBook(id, updatedBook);
    }

    /**
     * Remove um livro pelo ID.
     *
     * @param id O ID do livro a ser removido.
     */
    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
    }
}
