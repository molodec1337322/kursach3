package com.example.kursach3.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "Comments")
public class Comments {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private int answer_id;

    @ManyToOne
    @JoinColumn(name = "created_by_id")
    private int created_by_id;

    @Column(name = "created_at", nullable = false)
    private Date created_at;

    @Column(name = "edited_at", nullable = false)
    private Date edited_at;

    @Column(name = "comment_text", nullable = false)
    private String comment_text;

    public Comments() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAnswer_id(int answer_id) {
        this.answer_id = answer_id;
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

    public void setComment_text(String comment_text) {
        this.comment_text = comment_text;
    }

    public int getId() {
        return id;
    }

    public int getAnswer_id() {
        return answer_id;
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

    public String getComment_text() {
        return comment_text;
    }
}
