package com.example.HavenBook.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.swing.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor

public class Person extends Entity{
    private int id;
    private String name;

    public Person(String name) {
        this.setName(name);
    }
}
