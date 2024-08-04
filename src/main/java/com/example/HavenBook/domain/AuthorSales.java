package com.example.HavenBook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa as estatísticas de vendas de um autor.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthorSales {
    private String author;
    private int quantity;
}
