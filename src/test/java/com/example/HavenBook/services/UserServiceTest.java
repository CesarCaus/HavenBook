package com.example.HavenBook.services;

import com.example.HavenBook.domain.User;
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
 * Testes unitários para a classe {@link UserService}.
 */
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    private ObjectMapper mapper = new ObjectMapper();
    private Resource resource;

    /**
     * Configura o ambiente de teste antes de cada método de teste.
     * Inicializa o arquivo JSON com usuários de exemplo.
     */
    @BeforeEach
    public void setup() throws IOException {
        resource = new ClassPathResource("static/users.json");

        List<User> initialUsers = List.of(
                new User("User 1", "password1", "user1", "Department 1"),
                new User("User 2", "password2", "user2", "Department 2")
        );
        mapper.writeValue(resource.getFile(), initialUsers);

        userService.init();
    }

    /**
     * Testa o método {@link UserService#getUsersFromJson()}.
     * Verifica se os usuários são carregados corretamente do arquivo JSON.
     */
    @Test
    public void testGetUsersFromJson() {
        List<User> users = userService.getUsersFromJson();
        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("User 1", users.get(0).getName());
    }

    /**
     * Testa o método {@link UserService#addUser(User)}.
     * Verifica se um novo usuário é adicionado corretamente.
     */
    @Test
    public void testAddUser() {
        User newUser = new User("New User", "newpassword", "newuser", "New Department");
        userService.addUser(newUser);

        List<User> users = userService.getUsersFromJson();
        assertNotNull(users);
        assertEquals(3, users.size());
        assertEquals("New User", users.get(2).getName());
    }

    /**
     * Testa o método {@link UserService#updateUser(int, User)}.
     * Verifica se um usuário existente é atualizado corretamente.
     */
    @Test
    public void testUpdateUser() {
        User newUser = new User("New User", "newpassword", "newuser", "New Department");
        userService.addUser(newUser);

        User updatedUser = new User("Updated User", "updatedpassword", "updateduser", "Updated Department");
        userService.updateUser(newUser.getId(), updatedUser);

        User retrievedUser = userService.getUserById(newUser.getId());
        assertNotNull(retrievedUser);
        assertEquals("Updated User", retrievedUser.getName());
    }

    /**
     * Testa o método {@link UserService#deleteUser(int)}.
     * Verifica se um usuário é removido corretamente.
     */
    @Test
    public void testDeleteUser() {
        User newUser = new User("New User", "newpassword", "newuser", "New Department");
        userService.addUser(newUser);

        userService.deleteUser(newUser.getId());

        User retrievedUser = userService.getUserById(newUser.getId());
        assertNull(retrievedUser);
    }
}
