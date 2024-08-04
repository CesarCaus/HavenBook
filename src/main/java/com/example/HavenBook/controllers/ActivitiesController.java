package com.example.HavenBook.controllers;

import com.example.HavenBook.domain.Activity;
import com.example.HavenBook.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador para gerenciar atividades.
 * Esta classe fornece endpoints para criar, ler, atualizar e excluir atividades.
 */
@RestController
@RequestMapping("/api/activities")
public class ActivitiesController {

    private final ActivityService activityService;

    /**
     * Construtor para inicializar o controlador com o serviço de atividades.
     *
     * @param activityService O serviço de atividades a ser usado pelo controlador.
     */
    @Autowired
    public ActivitiesController(ActivityService activityService) {
        this.activityService = activityService;
    }

    /**
     * Obtém todas as atividades.
     *
     * @return Uma lista de atividades.
     */
    @GetMapping
    public List<Activity> getAllActivities() {
        return activityService.getActivityFromJson();
    }

    /**
     * Obtém uma atividade pelo ID.
     *
     * @param id O ID da atividade a ser recuperada.
     * @return A atividade correspondente ao ID fornecido.
     */
    @GetMapping("/{id}")
    public Activity getActivityById(@PathVariable int id) {
        return activityService.getActivityById(id);
    }

    /**
     * Adiciona uma nova atividade.
     *
     * @param newActivity A atividade a ser adicionada.
     */
    @PostMapping
    public void addActivity(@RequestBody Activity newActivity) {
        activityService.addActivity(newActivity);
    }

    /**
     * Atualiza uma atividade existente.
     *
     * @param id              O ID da atividade a ser atualizada.
     * @param updatedActivity A atividade com as novas informações.
     */
    @PutMapping("/{id}")
    public void updateActivity(@PathVariable int id, @RequestBody Activity updatedActivity) {
        activityService.updateActivity(id, updatedActivity);
    }

    /**
     * Remove uma atividade pelo ID.
     *
     * @param id O ID da atividade a ser removida.
     */
    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable int id) {
        activityService.deleteActivity(id);
    }
}
