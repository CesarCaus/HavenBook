package com.example.HavenBook.domain.interfaces;

import com.example.HavenBook.domain.Genre;

import java.util.List;

public interface IGenreService {
    List<Genre> getGenresFromJson();
    Genre getGenreById(int id);
    void addGenre(Genre newGenre);
    void updateGenre(int id, Genre updatedGenre);
    void deleteGenre(int id);
}
