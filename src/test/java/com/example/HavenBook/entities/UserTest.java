package com.example.HavenBook.entities;

import com.example.HavenBook.domain.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe {@link User}.
 */
public class UserTest {

    /**
     * Testa o construtor da classe {@link User}.
     */
    @Test
    public void testUserConstructor() {
        User user = new User("John Doe", "password123", "johndoe", "IT");

        assertNotNull(user);
        assertEquals("John Doe", user.getName());
        assertEquals("password123", user.getPassword());
        assertEquals("johndoe", user.getUsername());
        assertEquals("IT", user.getDepartment());
    }

    /**
     * Testa os métodos setters e getters da classe {@link User}.
     */
    @Test
    public void testUserSettersAndGetters() {
        User user = new User();
        user.setName("Jane Doe");
        user.setUsername("janedoe");
        user.setPassword("newpassword");
        user.setDepartment("HR");

        assertEquals("Jane Doe", user.getName());
        assertEquals("janedoe", user.getUsername());
        assertEquals("newpassword", user.getPassword());
        assertEquals("HR", user.getDepartment());
    }
}
