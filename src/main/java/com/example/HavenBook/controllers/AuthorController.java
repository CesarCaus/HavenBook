package com.example.HavenBook.controllers;

import com.example.HavenBook.domain.Author;
import com.example.HavenBook.services.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public List<Author> getAllAuthors() {
        return authorService.getAuthorsFromJson();
    }

    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable int id) {
        return authorService.getAuthorById(id);
    }

    @PostMapping
    public void addAuthor(@RequestBody Author newAuthor) {
        authorService.addAuthor(newAuthor);
    }

    @PutMapping("/{id}")
    public void updateAuthor(@PathVariable int id, @RequestBody Author updatedAuthor) {
        authorService.updateAuthor(id, updatedAuthor);
    }

    @DeleteMapping("/{id}")
    public void deleteAuthor(@PathVariable int id) {
        authorService.deleteAuthor(id);
    }
}
