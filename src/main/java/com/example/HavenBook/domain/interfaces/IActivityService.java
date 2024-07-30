package com.example.HavenBook.domain.interfaces;

import com.example.HavenBook.domain.Activity;

import java.util.List;

public interface IActivityService {
    List<Activity> getActivityFromJson();
    Activity getActivityById(int id);
    void addActivity(Activity newActivity);
    void updateActivity(int id, Activity updatedActivity);
    void deleteActivity(int id);
}
