package com.example.HavenBook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Representa um livro mais vendido, com título e quantidade de vendas.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BestSellingBook {
    private String title;
    private long count;
}
