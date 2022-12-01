package com.example.kursach3.models;

import javax.persistence.*;

@Entity
@Table(name = "Subjects")
public class Subjects {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "subject_name", nullable = false)
    private int subject_name;

    public Subjects() {
    }

    public int getId() {
        return id;
    }

    public int getSubject_name() {
        return subject_name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSubject_name(int subject_name) {
        this.subject_name = subject_name;
    }
}
