package org.beatrice.dgtuProject.model;


import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    @ManyToMany(mappedBy = "tags")
    Set<Task> tasks = new HashSet<>();

    public Tag(String name) {
        this.name = name;
    }

    public Tag() {

    }
}
