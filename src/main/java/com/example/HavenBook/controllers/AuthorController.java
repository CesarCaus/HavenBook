package com.example.HavenBook.controllers;

import com.example.HavenBook.domain.Author;
import com.example.HavenBook.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciar autores.
 * Esta classe fornece endpoints para criar, ler, atualizar e excluir autores.
 */
@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    /**
     * Construtor para inicializar o controlador com o serviço de autores.
     *
     * @param authorService O serviço de autores a ser usado pelo controlador.
     */
    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    /**
     * Obtém todos os autores.
     *
     * @return Uma lista de autores.
     */
    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAuthorsFromJson();
    }

    /**
     * Obtém um autor pelo ID.
     *
     * @param id O ID do autor a ser recuperado.
     * @return O autor correspondente ao ID fornecido.
     */
    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable int id) {
        return authorService.getAuthorById(id);
    }

    /**
     * Adiciona um novo autor.
     *
     * @param newAuthor O autor a ser adicionado.
     */
    @PostMapping
    public void addAuthor(@RequestBody Author newAuthor) {
        authorService.addAuthor(newAuthor);
    }

    /**
     * Atualiza um autor existente.
     *
     * @param id              O ID do autor a ser atualizado.
     * @param updatedAuthor   O autor com as novas informações.
     */
    @PutMapping("/{id}")
    public void updateAuthor(@PathVariable int id, @RequestBody Author updatedAuthor) {
        authorService.updateAuthor(id, updatedAuthor);
    }

    /**
     * Remove um autor pelo ID.
     *
     * @param id O ID do autor a ser removido.
     */
    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable int id) {
        authorService.deleteAuthor(id);
    }
}
