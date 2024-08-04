package com.example.HavenBook.services;

import com.example.HavenBook.domain.Activity;
import com.example.HavenBook.domain.interfaces.IActivityService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Serviço para gerenciar as atividades, incluindo operações de leitura, adição, atualização e exclusão de atividades
 * armazenadas em um arquivo JSON.
 */
@Service
@PropertySource("classpath:application.properties")
public class ActivityService implements IActivityService {

    private static final Logger LOGGER = Logger.getLogger(ActivityService.class.getName());

    private final ResourceLoader resourceLoader;
    private final String absoluteJsonFilePath;

    private List<Activity> activities;
    private int nextId;

    /**
     * Construtor para o serviço {@code ActivityService}.
     *
     * @param resourceLoader O carregador de recursos para obter o caminho absoluto do arquivo JSON.
     * @param jsonFilePath   O caminho relativo para o arquivo JSON que contém as atividades.
     */
    @Autowired
    public ActivityService(ResourceLoader resourceLoader, @Value("static/activities.json") String jsonFilePath) {
        this.resourceLoader = resourceLoader;
        this.absoluteJsonFilePath = getAbsolutePath(jsonFilePath);
    }

    /**
     * Inicializa o serviço carregando as atividades do arquivo JSON e definindo o próximo ID disponível.
     */
    @PostConstruct
    public void init() {
        this.activities = getActivityFromJson();
        if (this.activities.isEmpty()) {
            this.nextId = 1;
        } else {
            this.nextId = activities.stream().mapToInt(Activity::getId).max().orElse(0) + 1;
        }
    }

    /**
     * Obtém o caminho absoluto do arquivo JSON a partir do caminho relativo fornecido.
     *
     * @param relativePath O caminho relativo do arquivo JSON.
     * @return O caminho absoluto do arquivo JSON.
     * @throws RuntimeException Se ocorrer um erro ao obter o caminho absoluto.
     */
    private String getAbsolutePath(String relativePath) {
        try {
            Resource resource = resourceLoader.getResource("classpath:" + relativePath);
            return resource.getFile().getAbsolutePath();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao obter o caminho absoluto", e);
            throw new RuntimeException("Erro ao obter o caminho absoluto", e);
        }
    }

    /**
     * Carrega a lista de atividades do arquivo JSON.
     *
     * @return Uma lista de atividades carregadas do arquivo JSON.
     * @throws RuntimeException Se ocorrer um erro ao ler o arquivo JSON.
     */
    public List<Activity> getActivityFromJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(absoluteJsonFilePath);
            if (!file.exists()) {
                mapper.writeValue(file, List.of());
            }
            return mapper.readValue(file, new TypeReference<List<Activity>>() {});
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao ler o arquivo JSON", e);
            throw new RuntimeException("Erro ao ler o arquivo JSON", e);
        }
    }

    /**
     * Obtém uma atividade pelo ID.
     *
     * @param id O ID da atividade a ser recuperada.
     * @return A atividade correspondente ao ID fornecido ou {@code null} se não for encontrada.
     */
    public Activity getActivityById(int id) {
        Optional<Activity> activity = activities.stream().filter(a -> a.getId() == id).findFirst();
        return activity.orElse(null);
    }

    /**
     * Adiciona uma nova atividade à lista e salva as atividades no arquivo JSON.
     *
     * @param newActivity A nova atividade a ser adicionada.
     */
    public synchronized void addActivity(Activity newActivity) {
        newActivity.setId(nextId++);
        activities.add(newActivity);
        saveActivitiesToJson();
    }

    /**
     * Atualiza uma atividade existente com base no ID fornecido e salva as atividades no arquivo JSON.
     *
     * @param id              O ID da atividade a ser atualizada.
     * @param updatedActivity A atividade atualizada.
     */
    public synchronized void updateActivity(int id, Activity updatedActivity) {
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i).getId() == id) {
                updatedActivity.setId(id);
                activities.set(i, updatedActivity);
                saveActivitiesToJson();
                return;
            }
        }
    }

    /**
     * Remove uma atividade com base no ID fornecido e salva as atividades no arquivo JSON.
     *
     * @param id O ID da atividade a ser removida.
     */
    public synchronized void deleteActivity(int id) {
        activities.removeIf(activity -> activity.getId() == id);
        saveActivitiesToJson();
    }

    /**
     * Salva a lista atual de atividades no arquivo JSON.
     *
     * @throws RuntimeException Se ocorrer um erro ao salvar o arquivo JSON.
     */
    private synchronized void saveActivitiesToJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(absoluteJsonFilePath);
            mapper.writeValue(file, activities);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao salvar o arquivo JSON", e);
            throw new RuntimeException("Erro ao salvar o arquivo JSON", e);
        }
    }
}
