package com.example.kursach3.models;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "tickets")
public class Ticket {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @Column(name = "created_at", nullable = false)
    private Date created_at;

    @Column(name = "edited_at", nullable = false)
    private Date edited_at;

    @Column(name = "ticket_UID", nullable = false)
    private String ticket_UID;

    @Column(name = "topic", nullable = false)
    private String topic;

    @Column(name = "ticket_text", nullable = false, columnDefinition="TEXT")
    private String ticket_text;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ticket")
    private Set<Answer> answers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "answer")
    private Set<FileModel> fileModels = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;


    public Ticket() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setEdited_at(Date edited_at) {
        this.edited_at = edited_at;
    }

    public void setTicket_UID(String ticket_UID) {
        this.ticket_UID = ticket_UID;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setTicket_text(String ticket_text) {
        this.ticket_text = ticket_text;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public void setFileModels(Set<FileModel> fileModels) {
        this.fileModels = fileModels;
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

    public String getTicket_UID() {
        return ticket_UID;
    }

    public String getTopic() {
        return topic;
    }

    public String getTicket_text() {
        return ticket_text;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public User getUser() {
        return user;
    }

    public Subject getSubject() {
        return subject;
    }

    public Set<FileModel> getFileModels() {
        return fileModels;
    }
}
