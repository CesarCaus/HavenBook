package com.example.HavenBook.controllers;

import com.example.HavenBook.domain.Genre;
import com.example.HavenBook.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping
    public List<Genre> getAllGenres() {
        return genreService.getGenresFromJson();
    }

    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable int id) {
        return genreService.getGenreById(id);
    }

    @PostMapping
    public void addGenre(@RequestBody Genre newGenre) {
        genreService.addGenre(newGenre);
    }

    @PutMapping("/{id}")
    public void updateGenre(@PathVariable int id, @RequestBody Genre updatedGenre) {
        genreService.updateGenre(id, updatedGenre);
    }

    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable int id) {
        genreService.deleteGenre(id);
    }
}
