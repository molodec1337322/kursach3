package com.example.kursach3.models;

import javax.persistence.*;

public class Subjects {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "Subjects")
    private int subject_id;

    public void setId(int id) {
        this.id = id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public int getId() {
        return id;
    }

    public int getSubject_id() {
        return subject_id;
    }
}
