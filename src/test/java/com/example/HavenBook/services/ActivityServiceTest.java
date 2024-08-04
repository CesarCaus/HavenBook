package com.example.HavenBook.services;

import com.example.HavenBook.domain.Activity;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe {@link ActivityService}.
 */
@SpringBootTest
public class ActivityServiceTest {

    @Autowired
    private ActivityService activityService;

    private ObjectMapper mapper = new ObjectMapper();
    private Resource resource;

    /**
     * Configura o ambiente de teste antes de cada método de teste.
     * Inicializa o arquivo JSON com atividades de exemplo.
     */
    @BeforeEach
    public void setup() throws IOException {
        resource = new ClassPathResource("static/activities.json");

        List<Activity> initialActivities = List.of(
                new Activity("Activity 1", 1, new Date()),
                new Activity("Activity 2", 2, new Date())
        );
        mapper.writeValue(resource.getFile(), initialActivities);
        activityService.init();
    }

    /**
     * Testa o método {@link ActivityService#getActivityFromJson()}.
     * Verifica se as atividades são carregadas corretamente do arquivo JSON.
     */
    @Test
    public void testGetActivitiesFromJson() {
        List<Activity> activities = activityService.getActivityFromJson();
        assertNotNull(activities);
        assertEquals(2, activities.size());
        assertEquals("Activity 1", activities.get(0).getDescription());
    }

    /**
     * Testa o método {@link ActivityService#addActivity(Activity)}.
     * Verifica se uma nova atividade é adicionada corretamente.
     */
    @Test
    public void testAddActivity() {
        Activity newActivity = new Activity("New Activity", 3, new Date());
        activityService.addActivity(newActivity);

        List<Activity> activities = activityService.getActivityFromJson();
        assertNotNull(activities);
        assertEquals(3, activities.size());
        assertEquals("New Activity", activities.get(2).getDescription());
    }

    /**
     * Testa o método {@link ActivityService#updateActivity(int, Activity)}.
     * Verifica se uma atividade existente é atualizada corretamente.
     */
    @Test
    public void testUpdateActivity() {
        Activity newActivity = new Activity("New Activity", 3, new Date());
        activityService.addActivity(newActivity);

        Activity updatedActivity = new Activity("Updated Activity", 3, new Date());
        activityService.updateActivity(newActivity.getId(), updatedActivity);

        Activity retrievedActivity = activityService.getActivityById(newActivity.getId());
        assertNotNull(retrievedActivity);
        assertEquals("Updated Activity", retrievedActivity.getDescription());
    }

    /**
     * Testa o método {@link ActivityService#deleteActivity(int)}.
     * Verifica se uma atividade é removida corretamente.
     */
    @Test
    public void testDeleteActivity() {
        Activity newActivity = new Activity("New Activity", 3, new Date());
        activityService.addActivity(newActivity);

        activityService.deleteActivity(newActivity.getId());

        Activity retrievedActivity = activityService.getActivityById(newActivity.getId());
        assertNull(retrievedActivity);
    }
}
