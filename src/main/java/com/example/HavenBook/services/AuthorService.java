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

/**
 * Serviço para gerenciar os autores, incluindo operações de leitura, adição, atualização e exclusão de autores
 * armazenados em um arquivo JSON.
 */
@Service
@PropertySource("classpath:application.properties")
public class AuthorService implements IAuthorService {

    private static final Logger LOGGER = Logger.getLogger(AuthorService.class.getName());

    private final ResourceLoader resourceLoader;
    private final String absoluteJsonFilePath;

    private List<Author> authors;
    private int nextId;

    /**
     * Construtor para o serviço {@code AuthorService}.
     *
     * @param resourceLoader O carregador de recursos para obter o caminho absoluto do arquivo JSON.
     * @param jsonFilePath   O caminho relativo para o arquivo JSON que contém os autores.
     */
    @Autowired
    public AuthorService(ResourceLoader resourceLoader, @Value("static/authors.json") String jsonFilePath) {
        this.resourceLoader = resourceLoader;
        this.absoluteJsonFilePath = getAbsolutePath(jsonFilePath);
    }

    /**
     * Inicializa o serviço carregando os autores do arquivo JSON e definindo o próximo ID disponível.
     */
    @PostConstruct
    public void init() {
        this.authors = getAuthorsFromJson();
        if (this.authors.isEmpty()) {
            this.nextId = 1;
        } else {
            this.nextId = authors.stream().mapToInt(Author::getId).max().orElse(0) + 1;
        }
    }

    /**
     * Obtém o caminho absoluto do arquivo JSON a partir do caminho relativo fornecido.
     *
     * @param relativePath O caminho relativo do arquivo JSON.
     * @return O caminho absoluto do arquivo JSON.
     * @throws RuntimeException Se ocorrer um erro ao obter o caminho absoluto.
     */
    private String getAbsolutePath(String relativePath) {
        try {
            Resource resource = resourceLoader.getResource("classpath:" + relativePath);
            return resource.getFile().getAbsolutePath();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao obter o caminho absoluto", e);
            throw new RuntimeException("Erro ao obter o caminho absoluto", e);
        }
    }

    /**
     * Carrega a lista de autores do arquivo JSON.
     *
     * @return Uma lista de autores carregados do arquivo JSON.
     * @throws RuntimeException Se ocorrer um erro ao ler o arquivo JSON.
     */
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

    /**
     * Obtém um autor pelo ID.
     *
     * @param id O ID do autor a ser recuperado.
     * @return O autor correspondente ao ID fornecido ou {@code null} se não for encontrado.
     */
    public Author getAuthorById(int id) {
        Optional<Author> author = authors.stream().filter(a -> a.getId() == id).findFirst();
        return author.orElse(null);
    }

    /**
     * Adiciona um novo autor à lista e salva os autores no arquivo JSON.
     *
     * @param newAuthor O novo autor a ser adicionado.
     */
    public synchronized void addAuthor(Author newAuthor) {
        newAuthor.setId(nextId++);
        authors.add(newAuthor);
        saveAuthorsToJson();
    }

    /**
     * Atualiza um autor existente com base no ID fornecido e salva os autores no arquivo JSON.
     *
     * @param id              O ID do autor a ser atualizado.
     * @param updatedAuthor   O autor atualizado.
     */
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

    /**
     * Remove um autor com base no ID fornecido e salva os autores no arquivo JSON.
     *
     * @param id O ID do autor a ser removido.
     */
    public synchronized void deleteAuthor(int id) {
        authors.removeIf(author -> author.getId() == id);
        saveAuthorsToJson();
    }

    /**
     * Salva a lista atual de autores no arquivo JSON.
     *
     * @throws RuntimeException Se ocorrer um erro ao salvar o arquivo JSON.
     */
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
