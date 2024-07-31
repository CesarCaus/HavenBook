package com.example.HavenBook.services;

import com.example.HavenBook.domain.Author;
import com.example.HavenBook.domain.interfaces.IAuthorService;
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
public class AuthorService implements IAuthorService {

    private static final Logger LOGGER = Logger.getLogger(AuthorService.class.getName());

    private final ResourceLoader resourceLoader;
    private final String absoluteJsonFilePath;

    private List<Author> authors;
    private int nextId;

    @Autowired
    public AuthorService(ResourceLoader resourceLoader, @Value("static/authors.json") String jsonFilePath) {
        this.resourceLoader = resourceLoader;
        this.absoluteJsonFilePath = getAbsolutePath(jsonFilePath);
    }

    @PostConstruct
    public void init() {
        this.authors = getAuthorsFromJson();
        if (this.authors.isEmpty()) {
            this.nextId = 1;
        } else {
            this.nextId = authors.stream().mapToInt(Author::getId).max().orElse(0) + 1;
        }
    }

    private String getAbsolutePath(String relativePath) {
        try {
            Resource resource = resourceLoader.getResource("classpath:" + relativePath);
            return resource.getFile().getAbsolutePath();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao obter o caminho absoluto", e);
            throw new RuntimeException("Erro ao obter o caminho absoluto", e);
        }
    }

    public List<Author> getAuthorsFromJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(absoluteJsonFilePath);
            if (!file.exists()) {
                mapper.writeValue(file, List.of());
            }
            return mapper.readValue(file, new TypeReference<List<Author>>() {});
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao ler o arquivo JSON", e);
            throw new RuntimeException("Erro ao ler o arquivo JSON", e);
        }
    }

    public Author getAuthorById(int id) {
        Optional<Author> author = authors.stream().filter(a -> a.getId() == id).findFirst();
        return author.orElse(null);
    }

    public synchronized void addAuthor(Author newAuthor) {
        newAuthor.setId(nextId++);
        authors.add(newAuthor);
        saveAuthorsToJson();
    }

    public synchronized void updateAuthor(int id, Author updatedAuthor) {
        for (int i = 0; i < authors.size(); i++) {
            if (authors.get(i).getId() == id) {
                updatedAuthor.setId(id);
                authors.set(i, updatedAuthor);
                saveAuthorsToJson();
                return;
            }
        }
    }

    public synchronized void deleteAuthor(int id) {
        authors.removeIf(author -> author.getId() == id);
        saveAuthorsToJson();
    }

    private synchronized void saveAuthorsToJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(absoluteJsonFilePath);
            mapper.writeValue(file, authors);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao salvar o arquivo JSON", e);
            throw new RuntimeException("Erro ao salvar o arquivo JSON", e);
        }
    }
}
