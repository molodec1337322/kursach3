package com.example.kursach3.models;

import javax.persistence.*;
import java.util.Date;


@Entity
@Table(name = "comments")
public class Comment {
    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "created_at", nullable = false)
    private Date created_at;

    @Column(name = "edited_at", nullable = false)
    private Date edited_at;

    @Column(name = "comment_text", nullable = false, columnDefinition="TEXT")
    private String comment_text;

    public Comment() {
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }

    public void setUser(User created_by) {
        this.user = created_by;
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

    public Answer getAnswer() {
        return answer;
    }

    public User getUser() {
        return user;
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
