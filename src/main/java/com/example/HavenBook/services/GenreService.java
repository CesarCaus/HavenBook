package com.example.HavenBook.services;

import com.example.HavenBook.domain.Genre;
import com.example.HavenBook.domain.interfaces.IGenreService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

@Service
@PropertySource("classpath:application.properties")
public class GenreService implements IGenreService {

    private static final Logger LOGGER = Logger.getLogger(GenreService.class.getName());

    private final ResourceLoader resourceLoader;
    private final String absoluteJsonFilePath;

    private List<Genre> genres;
    private int nextId;

    @Autowired
    public GenreService(ResourceLoader resourceLoader, @Value("static/genres.json") String jsonFilePath) {
        this.resourceLoader = resourceLoader;
        this.absoluteJsonFilePath = getAbsolutePath(jsonFilePath);
    }

    @PostConstruct
    public void init() {
        this.genres = getGenresFromJson();
        if (this.genres.isEmpty()) {
            this.nextId = 1;
        } else {
            this.nextId = genres.stream().mapToInt(Genre::getId).max().orElse(0) + 1;
        }
    }

    private String getAbsolutePath(String relativePath) {
        try {
            Resource resource = resourceLoader.getResource("classpath:" + relativePath);
            return resource.getFile().getAbsolutePath();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error obtaining absolute path", e);
            throw new RuntimeException("Error obtaining absolute path", e);
        }
    }

    public List<Genre> getGenresFromJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(absoluteJsonFilePath);
            if (!file.exists()) {
                mapper.writeValue(file, List.of());
            }
            return mapper.readValue(file, new TypeReference<List<Genre>>() {});
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error reading JSON file", e);
            throw new RuntimeException("Error reading JSON file", e);
        }
    }

    public Genre getGenreById(int id) {
        Optional<Genre> genre = genres.stream().filter(g -> g.getId() == id).findFirst();
        return genre.orElse(null);
    }

    public synchronized void addGenre(Genre newGenre) {
        newGenre.setId(nextId++);
        genres.add(newGenre);
        saveGenresToJson();
    }

    public synchronized void updateGenre(int id, Genre updatedGenre) {
        for (int i = 0; i < genres.size(); i++) {
            if (genres.get(i).getId() == id) {
                updatedGenre.setId(id);
                genres.set(i, updatedGenre);
                saveGenresToJson();
                return;
            }
        }
    }

    public synchronized void deleteGenre(int id) {
        genres.removeIf(genre -> genre.getId() == id);
        saveGenresToJson();
    }

    private synchronized void saveGenresToJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(absoluteJsonFilePath);
            mapper.writeValue(file, genres);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Error saving to JSON file", e);
            throw new RuntimeException("Error saving to JSON file", e);
        }
    }
}
