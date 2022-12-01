package com.example.kursach3.models;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "answers")
public class Answer {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "created_at", nullable = false)
    private Date created_at;

    @Column(name = "edited_at", nullable = false)
    private Date edited_at;

    @Column(name = "answer_text", nullable = false)
    private String answer_text;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "answer")
    private Set<Comment> comments = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public void setId(int id) {
        this.id = id;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setEdited_at(Date edited_at) {
        this.edited_at = edited_at;
    }

    public void setAnswer_text(String answer_text) {
        this.answer_text = answer_text;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getEdited_at() {
        return edited_at;
    }

    public String getAnswer_text() {
        return answer_text;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public User getUser() {
        return user;
    }
}
