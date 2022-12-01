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

    public void setId(int id) {
        this.id = id;
    }

    public void setSubject_id(int subject_id) {
        this.subject_id = subject_id;
    }

    public void setCreated_by_id(int created_by_id) {
        this.created_by_id = created_by_id;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public void setEdited_at(Date edited_at) {
        this.edited_at = edited_at;
    }

    public void setFile_id(int file_id) {
        this.file_id = file_id;
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

    public int getId() {
        return id;
    }

    public int getSubject_id() {
        return subject_id;
    }

    public int getCreated_by_id() {
        return created_by_id;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public Date getEdited_at() {
        return edited_at;
    }

    public int getFile_id() {
        return file_id;
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
}
