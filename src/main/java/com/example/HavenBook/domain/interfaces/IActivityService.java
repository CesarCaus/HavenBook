package com.example.HavenBook.domain.interfaces;

import com.example.HavenBook.domain.Activity;

import java.util.List;

/**
 * Interface para o serviço de atividades.
 */
public interface IActivityService {
    /**
     * Obtém a lista de atividades a partir do arquivo JSON.
     *
     * @return Lista de {@code Activity}.
     */
    List<Activity> getActivityFromJson();

    /**
     * Obtém uma atividade pelo seu ID.
     *
     * @param id ID da atividade.
     * @return A {@code Activity} com o ID especificado.
     */
    Activity getActivityById(int id);

    /**
     * Adiciona uma nova atividade à lista e salva a lista atualizada no arquivo JSON.
     *
     * @param newActivity A nova {@code Activity} a ser adicionada.
     */
    void addActivity(Activity newActivity);

    /**
     * Atualiza uma atividade existente com o ID especificado e salva a lista atualizada no arquivo JSON.
     *
     * @param id            ID da atividade a ser atualizada.
     * @param updatedActivity A {@code Activity} com as informações atualizadas.
     */
    void updateActivity(int id, Activity updatedActivity);

    /**
     * Remove uma atividade da lista com o ID especificado e salva a lista atualizada no arquivo JSON.
     *
     * @param id ID da atividade a ser removida.
     */
    void deleteActivity(int id);
}
