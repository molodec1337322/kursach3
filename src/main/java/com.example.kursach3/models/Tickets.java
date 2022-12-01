package com.example.kursach3.models;

import javax.persistence.*;
import java.util.Date;

public class Tickets {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private int subject_id;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private int created_by_id;

    @Column(name = "created_at", nullable = false)
    private Date created_at;

    @Column(name = "edited_at", nullable = false)
    private Date edited_at;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "file_id")
    private int file_id;

    @Column(name = "ticket_UID", nullable = false)
    private String ticket_UID;

    @Column(name = "topic", nullable = false)
    private String topic;

    @Column(name = "ticket_text", nullable = false)
    private String ticket_text;
}
