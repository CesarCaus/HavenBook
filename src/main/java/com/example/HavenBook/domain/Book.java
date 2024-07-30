package com.example.HavenBook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Book extends Entity{
    private int id;
    private String title;
    private String author;
    private Date publicationDate;
    private String description;
    private List<String> genres;
    private int numberOfPages;
    private double value;

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
