package com.example.HavenBook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Genre extends Entity{
    private int id;
    private String name;

    public Genre(String name) {
        this.name = name;
    }
}
