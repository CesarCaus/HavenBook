package com.example.HavenBook.controllers;

import com.example.HavenBook.domain.Genre;
import com.example.HavenBook.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciar gêneros.
 * Esta classe fornece endpoints para criar, ler, atualizar e excluir gêneros.
 */
@RestController
@RequestMapping("/api/genres")
public class GenreController {

    private final GenreService genreService;

    /**
     * Construtor para inicializar o controlador com o serviço de gêneros.
     *
     * @param genreService O serviço de gêneros a ser usado pelo controlador.
     */
    @Autowired
    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    /**
     * Obtém todos os gêneros.
     *
     * @return Uma lista de gêneros.
     */
    @GetMapping
    public List<Genre> getAllGenres() {
        return genreService.getGenresFromJson();
    }

    /**
     * Obtém um gênero pelo ID.
     *
     * @param id O ID do gênero a ser recuperado.
     * @return O gênero correspondente ao ID fornecido.
     */
    @GetMapping("/{id}")
    public Genre getGenreById(@PathVariable int id) {
        return genreService.getGenreById(id);
    }

    /**
     * Adiciona um novo gênero.
     *
     * @param newGenre O gênero a ser adicionado.
     */
    @PostMapping
    public void addGenre(@RequestBody Genre newGenre) {
        genreService.addGenre(newGenre);
    }

    /**
     * Atualiza um gênero existente.
     *
     * @param id           O ID do gênero a ser atualizado.
     * @param updatedGenre O gênero com as novas informações.
     */
    @PutMapping("/{id}")
    public void updateGenre(@PathVariable int id, @RequestBody Genre updatedGenre) {
        genreService.updateGenre(id, updatedGenre);
    }

    /**
     * Remove um gênero pelo ID.
     *
     * @param id O ID do gênero a ser removido.
     */
    @DeleteMapping("/{id}")
    public void deleteGenre(@PathVariable int id) {
        genreService.deleteGenre(id);
    }
}
