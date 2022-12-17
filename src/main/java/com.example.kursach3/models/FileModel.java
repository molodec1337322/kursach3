package com.example.kursach3.models;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "files")
public class FileModel {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "contentType")
    private String contentType;

    @Column(name = "size")
    private Long size;

    @Lob
    @Column(name = "data")
    private byte[] data;

    @ManyToOne
    @JoinColumn(name = "ticket_id")
    private Ticket ticket;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private Answer answer;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContentType() {
        return contentType;
    }

    public Long getSize() {
        return size;
    }

    public byte[] getData() {
        return data;
    }

    public Ticket getTicket() {
        return ticket;
    }



    public Answer getAnswer() {
        return answer;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
