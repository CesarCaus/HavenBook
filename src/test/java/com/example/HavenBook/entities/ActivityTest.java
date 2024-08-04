package com.example.HavenBook.entities;

import com.example.HavenBook.domain.Activity;
import org.junit.jupiter.api.Test;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testes unitários para a classe {@link Activity}.
 */
public class ActivityTest {

    /**
     * Testa o construtor da classe {@link Activity}.
     */
    @Test
    public void testActivityConstructor() {
        Date date = new Date();
        Activity activity = new Activity("Test Activity", 1, date);

        assertNotNull(activity);
        assertEquals("Test Activity", activity.getDescription());
        assertEquals(1, activity.getResponsableId());
        assertEquals(date, activity.getFinalDate());
    }

    /**
     * Testa os métodos setters e getters da classe {@link Activity}.
     */
    @Test
    public void testActivitySettersAndGetters() {
        Date date = new Date();
        Activity activity = new Activity();
        activity.setDescription("Updated Activity");
        activity.setResponsableId(2);
        activity.setFinalDate(date);

        assertEquals("Updated Activity", activity.getDescription());
        assertEquals(2, activity.getResponsableId());
        assertEquals(date, activity.getFinalDate());
    }
}
