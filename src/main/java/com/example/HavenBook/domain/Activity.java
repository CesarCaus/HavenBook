package com.example.HavenBook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Activity extends Entity{
    private int id;
    private String description;
    private int responsableId;
    private Date finalDate;

    public Activity(String description, int responsableId, Date finalDate) {
        this.description = description;
        this.responsableId = responsableId;
        this.finalDate = finalDate;
    }
}
