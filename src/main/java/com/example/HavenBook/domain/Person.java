package com.example.HavenBook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa uma pessoa com um nome
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    private int id;
    private String name;

    /**
     * Construtor para criar uma pessoa com o nome fornecido.
     *
     * @param name Nome da pessoa.
     */
    public Person(String name) {
        this.setName(name);
    }
}
