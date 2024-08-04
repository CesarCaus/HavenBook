package com.example.HavenBook.domain.interfaces;

import com.example.HavenBook.domain.User;

import java.util.List;

/**
 * Interface para o serviço de usuários.
 */
public interface IUserService {
    /**
     * Obtém a lista de usuários a partir do arquivo JSON.
     *
     * @return Lista de {@code User}.
     */
    List<User> getUsersFromJson();

    /**
     * Obtém um usuário pelo seu ID.
     *
     * @param id ID do usuário.
     * @return O {@code User} com o ID especificado.
     */
    User getUserById(int id);

    /**
     * Adiciona um novo usuário à lista e salva a lista atualizada no arquivo JSON.
     *
     * @param newUser O novo {@code User} a ser adicionado.
     */
    void addUser(User newUser);

    /**
     * Atualiza um usuário existente com o ID especificado e salva a lista atualizada no arquivo JSON.
     *
     * @param id          ID do usuário a ser atualizado.
     * @param updatedUser O {@code User} com as informações atualizadas.
     */
    void updateUser(int id, User updatedUser);

    /**
     * Remove um usuário da lista com o ID especificado e salva a lista atualizada no arquivo JSON.
     *
     * @param id ID do usuário a ser removido.
     */
    void deleteUser(int id);
}
