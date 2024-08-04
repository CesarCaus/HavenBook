package com.example.HavenBook.services;

import com.example.HavenBook.domain.User;
import com.example.HavenBook.domain.interfaces.IUserService;
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
 * Serviço para gerenciar usuários, incluindo operações de leitura, escrita, atualização e exclusão de usuários armazenados em um arquivo JSON.
 */
@Service
@PropertySource("classpath:application.properties")
public class UserService implements IUserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    private final ResourceLoader resourceLoader;
    private final String absoluteJsonFilePath;

    private List<User> users;
    private int nextId;

    /**
     * Construtor da classe {@code UserService}.
     *
     * @param resourceLoader Carregador de recursos para obter o caminho absoluto do arquivo JSON.
     * @param jsonFilePath   Caminho para o arquivo JSON que armazena os usuários.
     */
    @Autowired
    public UserService(ResourceLoader resourceLoader, @Value("${json.file.path:static/users.json}") String jsonFilePath) {
        this.resourceLoader = resourceLoader;
        this.absoluteJsonFilePath = getAbsolutePath(jsonFilePath);
    }

    /**
     * Inicializa o serviço carregando os usuários do arquivo JSON e define o próximo ID disponível.
     */
    @PostConstruct
    public void init() {
        this.users = getUsersFromJson();
        if (this.users.isEmpty()) {
            this.nextId = 1;
        } else {
            this.nextId = users.stream().mapToInt(User::getId).max().orElse(0) + 1;
        }
    }

    /**
     * Obtém o caminho absoluto do arquivo JSON a partir do caminho relativo fornecido.
     *
     * @param relativePath Caminho relativo para o arquivo JSON.
     * @return Caminho absoluto para o arquivo JSON.
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
     * Obtém a lista de usuários a partir do arquivo JSON.
     *
     * @return Lista de {@code User} obtida do arquivo JSON.
     * @throws RuntimeException Se ocorrer um erro ao ler o arquivo JSON.
     */
    public List<User> getUsersFromJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(absoluteJsonFilePath);
            if (!file.exists()) {
                mapper.writeValue(file, List.of());
            }
            return mapper.readValue(file, new TypeReference<List<User>>() {});
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao ler o arquivo JSON", e);
            throw new RuntimeException("Erro ao ler o arquivo JSON", e);
        }
    }

    /**
     * Obtém um usuário pelo seu ID.
     *
     * @param id ID do usuário.
     * @return O {@code User} com o ID especificado, ou {@code null} se não for encontrado.
     */
    public User getUserById(int id) {
        Optional<User> user = users.stream().filter(u -> u.getId() == id).findFirst();
        return user.orElse(null);
    }

    /**
     * Adiciona um novo usuário à lista e salva a lista atualizada no arquivo JSON.
     *
     * @param newUser O {@code User} a ser adicionado.
     */
    public synchronized void addUser(User newUser) {
        newUser.setId(nextId++);
        users.add(newUser);
        saveUsersToJson();
    }

    /**
     * Atualiza um usuário existente com o ID especificado e salva a lista atualizada no arquivo JSON.
     *
     * @param id          ID do usuário a ser atualizado.
     * @param updatedUser O {@code User} com as informações atualizadas.
     */
    public synchronized void updateUser(int id, User updatedUser) {
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getId() == id) {
                updatedUser.setId(id);
                users.set(i, updatedUser);
                saveUsersToJson();
                return;
            }
        }
    }

    /**
     * Remove um usuário da lista com o ID especificado e salva a lista atualizada no arquivo JSON.
     *
     * @param id ID do usuário a ser removido.
     */
    public synchronized void deleteUser(int id) {
        users.removeIf(user -> user.getId() == id);
        saveUsersToJson();
    }

    /**
     * Salva a lista de usuários no arquivo JSON.
     *
     * @throws RuntimeException Se ocorrer um erro ao salvar o arquivo JSON.
     */
    private synchronized void saveUsersToJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(absoluteJsonFilePath);
            mapper.writeValue(file, users);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao salvar o arquivo JSON", e);
            throw new RuntimeException("Erro ao salvar o arquivo JSON", e);
        }
    }
}
