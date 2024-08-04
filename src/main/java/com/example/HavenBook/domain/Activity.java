package com.example.HavenBook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Representa uma atividade com uma descrição, responsável e data final.
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Activity {
    private int id;
    private String description;
    private int responsableId;
    private Date finalDate;

    /**
     * Construtor para criar uma atividade com descrição, responsável e data final.
     *
     * @param description  Descrição da atividade.
     * @param responsableId ID do responsável pela atividade.
     * @param finalDate    Data final da atividade.
     */
    public Activity(String description, int responsableId, Date finalDate) {
        this.description = description;
        this.responsableId = responsableId;
        this.finalDate = finalDate;
    }
}
