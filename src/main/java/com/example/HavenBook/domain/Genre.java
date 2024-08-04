package com.example.HavenBook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa um gênero, que é uma especialização da classe {@link Entity}.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Genre {
    private int id;
    private String name;

    /**
     * Construtor para criar um gênero com o nome fornecido.
     *
     * @param name Nome do gênero.
     */
    public Genre(String name) {
        this.name = name;
    }
}
