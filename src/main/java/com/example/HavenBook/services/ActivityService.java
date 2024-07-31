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

@Service
@PropertySource("classpath:application.properties")
public class ActivityService implements IActivityService {

    private static final Logger LOGGER = Logger.getLogger(ActivityService.class.getName());

    private final ResourceLoader resourceLoader;
    private final String absoluteJsonFilePath;

    private List<Activity> activities;
    private int nextId;

    @Autowired
    public ActivityService(ResourceLoader resourceLoader, @Value("static/activities.json") String jsonFilePath) {
        this.resourceLoader = resourceLoader;
        this.absoluteJsonFilePath = getAbsolutePath(jsonFilePath);
    }

    @PostConstruct
    public void init() {    
        this.activities = getActivityFromJson();
        if (this.activities.isEmpty()) {
            this.nextId = 1;
        } else {
            this.nextId = activities.stream().mapToInt(Activity::getId).max().orElse(0) + 1;
        }
    }

    private String getAbsolutePath(String relativePath) {
        try {
            Resource resource = resourceLoader.getResource("classpath:" + relativePath);
            return resource.getFile().getAbsolutePath();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao obter o caminho absoluto", e);
            throw new RuntimeException("Erro ao obter o caminho absoluto", e);
        }
    }

    public List<Activity> getActivityFromJson() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            File file = new File(absoluteJsonFilePath);
            if (!file.exists()) {
                mapper.writeValue(file, List.of()); //
            }
            return mapper.readValue(file, new TypeReference<List<Activity>>() {});
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Erro ao ler o arquivo JSON", e);
            throw new RuntimeException("Erro ao ler o arquivo JSON", e);
        }
    }

    public Activity getActivityById(int id) {
        Optional<Activity> activity = activities.stream().filter(a -> a.getId() == id).findFirst();
        return activity.orElse(null);
    }

    public synchronized void addActivity(Activity newActivity) {
        newActivity.setId(nextId++);
        activities.add(newActivity);
        saveActivitiesToJson();
    }

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

    public synchronized void deleteActivity(int id) {
        activities.removeIf(activity -> activity.getId() == id);
        saveActivitiesToJson();
    }

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