package com.example.kursach3.dao;

import com.example.kursach3.models.Answer;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Component
@Repository
@Transactional
public class AnswerDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Answer GetAnswerByID(int id){
        return sessionFactory.getCurrentSession().get(Answer.class, id);
    }
}