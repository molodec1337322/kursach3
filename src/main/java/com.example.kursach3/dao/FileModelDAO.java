package com.example.kursach3.dao;

import com.example.kursach3.models.FileModel;
import com.example.kursach3.models.Ticket;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Repository
@Transactional
public class FileModelDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public FileModel getFileById(String id){
        return sessionFactory.getCurrentSession().get(FileModel.class, id);
    }

    public List<FileModel> getAllFilesByTicket(int ticket_id){
        return sessionFactory.getCurrentSession().createQuery("from FileModel where ticket_id=" + ticket_id).list();
    }

    public List<FileModel> getAllFilesByAnswers(int answer_id){
        return sessionFactory.getCurrentSession().createQuery("from FileModel where answer_id=" + answer_id).list();
    }
}
