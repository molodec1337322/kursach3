package com.example.kursach3.dao;

import com.example.kursach3.models.Answer;
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
public class CommentsDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public CommentsDAO GetCommentById(int id){
        return sessionFactory.getCurrentSession().get(CommentsDAO.class, id);
    }

    public List<Answer> GetCommentsAnswerID(int id){
        return sessionFactory.getCurrentSession().createQuery("from Comments where answer_id=" + id).list();
    }
}
