package com.example.HavenBook.controllers;

import com.example.HavenBook.domain.User;
import com.example.HavenBook.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciar usuários.
 * Esta classe fornece endpoints para criar, ler, atualizar e excluir usuários, além de validar senhas.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    /**
     * Construtor para inicializar o controlador com o serviço de usuários.
     *
     * @param userService O serviço de usuários a ser usado pelo controlador.
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Obtém todos os usuários.
     *
     * @return Uma lista de usuários.
     */
    @GetMapping
    public List<User> getAllUsers() {
        return userService.getUsersFromJson();
    }

    /**
     * Obtém um usuário pelo ID.
     *
     * @param id O ID do usuário a ser recuperado.
     * @return O usuário correspondente ao ID fornecido.
     */
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    /**
     * Adiciona um novo usuário.
     *
     * @param newUser O usuário a ser adicionado.
     */
    @PostMapping
    public void addUser(@RequestBody User newUser) {
        userService.addUser(newUser);
    }

    /**
     * Atualiza um usuário existente.
     *
     * @param id          O ID do usuário a ser atualizado.
     * @param updatedUser O usuário com as novas informações.
     */
    @PutMapping("/{id}")
    public void updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        userService.updateUser(id, updatedUser);
    }

    /**
     * Remove um usuário pelo ID.
     *
     * @param id O ID do usuário a ser removido.
     */
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}
