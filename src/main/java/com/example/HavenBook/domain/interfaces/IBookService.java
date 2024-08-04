package com.example.HavenBook.domain.interfaces;

import com.example.HavenBook.domain.Book;

import java.util.List;

/**
 * Interface para o serviço de livros.
 */
public interface IBookService {
    /**
     * Obtém a lista de livros a partir do arquivo JSON.
     *
     * @return Lista de {@code Book}.
     */
    List<Book> getBooksFromJson();

    /**
     * Obtém um livro pelo seu ID.
     *
     * @param id ID do livro.
     * @return O {@code Book} com o ID especificado.
     */
    Book getBookById(int id);

    /**
     * Adiciona um novo livro à lista e salva a lista atualizada no arquivo JSON.
     *
     * @param newBook O novo {@code Book} a ser adicionado.
     */
    void addBook(Book newBook);

    /**
     * Atualiza um livro existente com o ID especificado e salva a lista atualizada no arquivo JSON.
     *
     * @param id            ID do livro a ser atualizado.
     * @param updatedBook O {@code Book} com as informações atualizadas.
     */
    void updateBook(int id, Book updatedBook);

    /**
     * Remove um livro da lista com o ID especificado e salva a lista atualizada no arquivo JSON.
     *
     * @param id ID do livro a ser removido.
     */
    void deleteBook(int id);
}
