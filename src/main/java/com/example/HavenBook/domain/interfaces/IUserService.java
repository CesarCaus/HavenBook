package com.example.HavenBook.domain.interfaces;

import com.example.HavenBook.domain.User;

import java.util.List;

public interface IUserService {
    List<User> getUsersFromJson();
    User getUserById(int id);
    void addUser(User newUser);
    void updateUser(int id, User updatedUser);
    void deleteUser(int id);
}
