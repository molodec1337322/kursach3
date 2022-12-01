package com.example.kursach3.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "answers")
public class Answers {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private int ticket_id;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private int created_by_id;

    @Column(name = "created_at", nullable = false)
    private Date created_at;

    @Column(name = "edited_at", nullable = false)
    private Date edited_at;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "File")
    private int file_id;

    @Column(name = "answer_text", nullable = false)
    private String answer_text;

    public void setId(int id) {
        this.id = id;
    }

    public void setTicket_id(int ticket_id) {
        this.ticket_id = ticket_id;
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

    public void setAnswer_text(String answer_text) {
        this.answer_text = answer_text;
    }

    public int getId() {
        return id;
    }

    public int getTicket_id() {
        return ticket_id;
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

    public String getAnswer_text() {
        return answer_text;
    }
}
