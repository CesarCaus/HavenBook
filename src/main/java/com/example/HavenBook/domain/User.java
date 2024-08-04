package com.example.HavenBook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa um usuário, que é uma especialização da classe {@link Person} e inclui informações como nome de usuário, senha, departamento e status.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User extends Person {
    private String username;
    private String password;
    private String department;
    private int status;

    /**
     * Construtor para criar um usuário com informações fornecidas.
     *
     * @param name       Nome do usuário.
     * @param password   Senha do usuário.
     * @param username   Nome de usuário.
     * @param department Departamento do usuário.
     */
    public User(String name, String password, String username, String department) {
        super(name);
        this.setPassword(password);
        this.setUsername(username);
        this.setDepartment(department);
    }

    /**
     * Obtém a senha do usuário. Esta informação é ignorada na serialização JSON.
     *
     * @return Senha do usuário.
     */
    @JsonIgnore
    public String getPassword() {
        return password;
    }
}
