package com.example.HavenBook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends Person{
    private String username;
    private String password;
    private String department;
    private int status;

    public User(String name, String password, String username, String department) {
        super(name);
        this.setPassword(password);
        this.setUsername(username);
        this.setDepartment(department);
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }
}