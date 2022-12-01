package com.example.kursach3.models;

import javax.persistence.*;

public class Teachers {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private int user_id;
}
