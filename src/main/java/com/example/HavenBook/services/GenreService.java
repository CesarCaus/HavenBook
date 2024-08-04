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

/**
 * Serviço para gerenciar os gêneros, incluindo operações de leitura, adição, atualização e exclusão de gêneros
 * armazenados em um arquivo JSON.
 */
@Service
@PropertySource("classpath:application.properties")
public class GenreService implements IGenreService {

    private static final Logger LOGGER = Logger.getLogger(GenreService.class.getName());

    private final ResourceLoader resourceLoader;
    private final String absoluteJsonFilePath;

    private List<Genre> genres;
    private int nextId;

    /**
     * Construtor para o serviço {@code GenreService}.
     *
     * @param resourceLoader O carregador de recursos para obter o caminho absoluto do arquivo JSON.
     * @param jsonFilePath   O caminho relativo para o arquivo JSON que contém os gêneros.
     */
    @Autowired
    public GenreService(ResourceLoader resourceLoader, @Value("static/genres.json") String jsonFilePath) {
        this.resourceLoader = resourceLoader;
        this.absoluteJsonFilePath = getAbsolutePath(jsonFilePath);
    }

    /**
     * Inicializa o serviço carregando os gêneros do arquivo JSON e definindo o próximo ID disponível.
     */
    @PostConstruct
    public void init() {
        this.genres = getGenresFromJson();
        if (this.genres.isEmpty()) {
            this.nextId = 1;
        } else {
            this.nextId = genres.stream().mapToInt(Genre::getId).max().orElse(0) + 1;
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
     * Carrega a lista de gêneros do arquivo JSON.
     *
     * @return Uma lista de gêneros carregados do arquivo JSON.
     * @throws RuntimeException Se ocorrer um erro ao ler o arquivo JSON.
     */
    public List<Genre> getGenresFromJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(absoluteJsonFilePath);
            if (!file.exists()) {
                mapper.writeValue(file, List.of());
            }
            return mapper.readValue(file, new TypeReference<List<Genre>>() {});
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao ler o arquivo JSON", e);
            throw new RuntimeException("Erro ao ler o arquivo JSON", e);
        }
    }

    /**
     * Obtém um gênero pelo ID.
     *
     * @param id O ID do gênero a ser recuperado.
     * @return O gênero correspondente ao ID fornecido ou {@code null} se não for encontrado.
     */
    public Genre getGenreById(int id) {
        Optional<Genre> genre = genres.stream().filter(g -> g.getId() == id).findFirst();
        return genre.orElse(null);
    }

    /**
     * Adiciona um novo gênero à lista e salva os gêneros no arquivo JSON.
     *
     * @param newGenre O novo gênero a ser adicionado.
     */
    public synchronized void addGenre(Genre newGenre) {
        newGenre.setId(nextId++);
        genres.add(newGenre);
        saveGenresToJson();
    }

    /**
     * Atualiza um gênero existente com base no ID fornecido e salva os gêneros no arquivo JSON.
     *
     * @param id            O ID do gênero a ser atualizado.
     * @param updatedGenre  O gênero atualizado.
     */
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

    /**
     * Remove um gênero com base no ID fornecido e salva os gêneros no arquivo JSON.
     *
     * @param id O ID do gênero a ser removido.
     */
    public synchronized void deleteGenre(int id) {
        genres.removeIf(genre -> genre.getId() == id);
        saveGenresToJson();
    }

    /**
     * Salva a lista atual de gêneros no arquivo JSON.
     *
     * @throws RuntimeException Se ocorrer um erro ao salvar o arquivo JSON.
     */
    private synchronized void saveGenresToJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(absoluteJsonFilePath);
            mapper.writeValue(file, genres);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao salvar o arquivo JSON", e);
            throw new RuntimeException("Erro ao salvar o arquivo JSON", e);
        }
    }
}
