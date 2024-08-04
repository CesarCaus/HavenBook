package com.example.HavenBook.domain.interfaces;

import com.example.HavenBook.domain.Genre;

import java.util.List;

/**
 * Interface para o serviço de gêneros.
 */
public interface IGenreService {
    /**
     * Obtém a lista de gêneros a partir do arquivo JSON.
     *
     * @return Lista de {@code Genre}.
     */
    List<Genre> getGenresFromJson();

    /**
     * Obtém um gênero pelo seu ID.
     *
     * @param id ID do gênero.
     * @return O {@code Genre} com o ID especificado.
     */
    Genre getGenreById(int id);

    /**
     * Adiciona um novo gênero à lista e salva a lista atualizada no arquivo JSON.
     *
     * @param newGenre O novo {@code Genre} a ser adicionado.
     */
    void addGenre(Genre newGenre);

    /**
     * Atualiza um gênero existente com o ID especificado e salva a lista atualizada no arquivo JSON.
     *
     * @param id            ID do gênero a ser atualizado.
     * @param updatedGenre O {@code Genre} com as informações atualizadas.
     */
    void updateGenre(int id, Genre updatedGenre);

    /**
     * Remove um gênero da lista com o ID especificado e salva a lista atualizada no arquivo JSON.
     *
     * @param id ID do gênero a ser removido.
     */
    void deleteGenre(int id);
}
