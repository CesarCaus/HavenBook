package com.example.HavenBook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

/**
 * Representa um livro com detalhes como título, autor, data de publicação, descrição, gêneros, número de páginas e valor.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Book {
    private int id;
    private String title;
    private String author;
    private Date publicationDate;
    private String description;
    private List<String> genres;
    private int numberOfPages;
    private double value;

    /**
     * Construtor para criar um livro com os detalhes fornecidos.
     *
     * @param title           Título do livro.
     * @param author          Autor do livro.
     * @param publicationDate Data de publicação do livro.
     * @param description     Descrição do livro.
     * @param genres          Gêneros do livro.
     * @param numberOfPages   Número de páginas do livro.
     * @param value           Valor do livro.
     */
    public Book(String title, String author, Date publicationDate, String description, List<String> genres, int numberOfPages, double value) {
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
        this.description = description;
        this.genres = genres;
        this.numberOfPages = numberOfPages;
        this.value = value;
    }
}
