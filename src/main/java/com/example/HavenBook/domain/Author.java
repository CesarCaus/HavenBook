package com.example.HavenBook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor(force = true)
public class Author extends Person {
    public Author(String name) {
        super(name);
    }
}
