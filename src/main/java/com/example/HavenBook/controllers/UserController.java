package com.example.HavenBook.controllers;

import com.example.HavenBook.domain.User;
import com.example.HavenBook.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getUsersFromJson();
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping
    public void addUser(@RequestBody User newUser) {
        userService.addUser(newUser);
    }

    @PutMapping("/{id}")
    public void updateUser(@PathVariable int id, @RequestBody User updatedUser) {
        userService.updateUser(id, updatedUser);
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @PostMapping("/{id}/validate-password")
    public boolean validatePassword(@PathVariable int id, @RequestBody String password) {
        return userService.validatePassword(id, password);
    }
}
