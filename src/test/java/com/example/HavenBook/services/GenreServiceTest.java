package com.example.HavenBook.services;

import com.example.HavenBook.domain.Genre;
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
 * Testes unitários para a classe {@link GenreService}.
 */
@SpringBootTest
public class GenreServiceTest {

    @Autowired
    private GenreService genreService;

    private ObjectMapper mapper = new ObjectMapper();
    private Resource resource;

    /**
     * Configura o ambiente de teste antes de cada método de teste.
     * Inicializa o arquivo JSON com gêneros de exemplo.
     */
    @BeforeEach
    public void setup() throws IOException {
        resource = new ClassPathResource("static/genres.json");

        List<Genre> initialGenres = List.of(
                new Genre("Genre 1"),
                new Genre("Genre 2")
        );
        mapper.writeValue(resource.getFile(), initialGenres);

        genreService.init();
    }

    /**
     * Testa o método {@link GenreService#getGenresFromJson()}.
     * Verifica se os gêneros são carregados corretamente do arquivo JSON.
     */
    @Test
    public void testGetGenresFromJson() {
        List<Genre> genres = genreService.getGenresFromJson();
        assertNotNull(genres);
        assertEquals(2, genres.size());
        assertEquals("Genre 1", genres.get(0).getName());
    }

    /**
     * Testa o método {@link GenreService#addGenre(Genre)}.
     * Verifica se um novo gênero é adicionado corretamente.
     */
    @Test
    public void testAddGenre() {
        Genre newGenre = new Genre("New Genre");
        genreService.addGenre(newGenre);

        List<Genre> genres = genreService.getGenresFromJson();
        assertNotNull(genres);
        assertEquals(3, genres.size());
        assertEquals("New Genre", genres.get(2).getName());
    }

    /**
     * Testa o método {@link GenreService#updateGenre(int, Genre)}.
     * Verifica se um gênero existente é atualizado corretamente.
     */
    @Test
    public void testUpdateGenre() {
        Genre newGenre = new Genre("New Genre");
        genreService.addGenre(newGenre);

        Genre updatedGenre = new Genre("Updated Genre");
        genreService.updateGenre(newGenre.getId(), updatedGenre);

        Genre retrievedGenre = genreService.getGenreById(newGenre.getId());
        assertNotNull(retrievedGenre);
        assertEquals("Updated Genre", retrievedGenre.getName());
    }

    /**
     * Testa o método {@link GenreService#deleteGenre(int)}.
     * Verifica se um gênero é removido corretamente.
     */
    @Test
    public void testDeleteGenre() {
        Genre newGenre = new Genre("New Genre");
        genreService.addGenre(newGenre);

        genreService.deleteGenre(newGenre.getId());

        Genre retrievedGenre = genreService.getGenreById(newGenre.getId());
        assertNull(retrievedGenre);
    }
}
