package com.example.HavenBook.controllers;

import com.example.HavenBook.domain.Activity;
import com.example.HavenBook.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivitiesController {

    private final ActivityService activityService;

    @Autowired
    public ActivitiesController(ActivityService activityService) {
        this.activityService = activityService;
    }

    @GetMapping
    public List<Activity> getAllActivities() {
        return activityService.getActivityFromJson();
    }
    @GetMapping("/{id}")
    public Activity getActivityById(@PathVariable int id) {
        return activityService.getActivityById(id);
    }

    @PostMapping
    public void addActivity(@RequestBody Activity newActivity) {
        activityService.addActivity(newActivity);
    }

    @PutMapping("/{id}")
    public void updateActivity(@PathVariable int id, @RequestBody Activity updatedActivity) {
        activityService.updateActivity(id, updatedActivity);
    }

    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable int id) {
        activityService.deleteActivity(id);
    }
}
