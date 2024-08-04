package com.example.HavenBook.domain.interfaces;

import com.example.HavenBook.domain.Author;

import java.util.List;

/**
 * Interface para o serviço de autores.
 */
public interface IAuthorService {
    /**
     * Obtém a lista de autores a partir do arquivo JSON.
     *
     * @return Lista de {@code Author}.
     */
    List<Author> getAuthorsFromJson();

    /**
     * Obtém um autor pelo seu ID.
     *
     * @param id ID do autor.
     * @return O {@code Author} com o ID especificado.
     */
    Author getAuthorById(int id);

    /**
     * Adiciona um novo autor à lista e salva a lista atualizada no arquivo JSON.
     *
     * @param newAuthor O novo {@code Author} a ser adicionado.
     */
    void addAuthor(Author newAuthor);

    /**
     * Atualiza um autor existente com o ID especificado e salva a lista atualizada no arquivo JSON.
     *
     * @param id            ID do autor a ser atualizado.
     * @param updatedAuthor O {@code Author} com as informações atualizadas.
     */
    void updateAuthor(int id, Author updatedAuthor);

    /**
     * Remove um autor da lista com o ID especificado e salva a lista atualizada no arquivo JSON.
     *
     * @param id ID do autor a ser removido.
     */
    void deleteAuthor(int id);
}
