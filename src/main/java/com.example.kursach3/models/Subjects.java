package com.example.kursach3.models;

import javax.persistence.*;

public class Subjects {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Subjects")
    private int subject_id;
}
