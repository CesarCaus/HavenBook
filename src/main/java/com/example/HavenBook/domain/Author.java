package com.example.HavenBook.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa um autor, que é uma especialização da classe {@link Person}.
 */
@Setter
@Getter
@NoArgsConstructor(force = true)
public class Author extends Person {

    /**
     * Construtor para criar um autor com o nome fornecido.
     *
     * @param name Nome do autor.
     */
    public Author(String name) {
        super(name);
    }
}
