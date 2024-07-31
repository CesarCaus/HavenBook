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

@Service
@PropertySource("classpath:application.properties")
public class UserService implements IUserService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

    private final ResourceLoader resourceLoader;
    private final String absoluteJsonFilePath;

    private List<User> users;
    private int nextId;

    @Autowired
    public UserService(ResourceLoader resourceLoader, @Value("${json.file.path:static/users.json}") String jsonFilePath) {
        this.resourceLoader = resourceLoader;
        this.absoluteJsonFilePath = getAbsolutePath(jsonFilePath);
    }

    @PostConstruct
    public void init() {
        this.users = getUsersFromJson();
        if (this.users.isEmpty()) {
            this.nextId = 1;
        } else {
            this.nextId = users.stream().mapToInt(User::getId).max().orElse(0) + 1;
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

    public User getUserById(int id) {
        Optional<User> user = users.stream().filter(u -> u.getId() == id).findFirst();
        return user.orElse(null);
    }

    public synchronized void addUser(User newUser) {
        newUser.setId(nextId++);
        users.add(newUser);
        saveUsersToJson();
    }

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

    public synchronized void deleteUser(int id) {
        users.removeIf(user -> user.getId() == id);
        saveUsersToJson();
    }

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

    public boolean validatePassword(int id, String password) {
        User user = getUserById(id);
        if (user != null) {
            return user.getPassword().equals(password);
        }
        return false;
    }
}
