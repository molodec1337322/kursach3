package com.example.kursach3.models;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "subjects")
public class Subject {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "subject_name", nullable = false)
    private String subject_name;

    @OneToMany(cascade = {CascadeType.ALL}, mappedBy = "subject")
    private Set<Ticket> tickets = new HashSet<>();

    public Subject() {
    }

    public int getId() {
        return id;
    }

    public String getSubject_name() {
        return subject_name;
    }

    public Set<Ticket> getTickets() {
        return tickets;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSubject_name(String subject_name) {
        this.subject_name = subject_name;
    }

    public void setTickets(Set<Ticket> tickets) {
        this.tickets = tickets;
    }
}
