package com.example.kursach3.dao;


import com.example.kursach3.models.Subject;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Repository
@Transactional
public class SubjectDAO {
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void addNewSubject(Subject subject){
        sessionFactory.getCurrentSession().persist(subject);
    }

    public Subject getSubjectByID(int id){
        return sessionFactory.getCurrentSession().get(Subject.class, id);
    }

    public List<Subject> getAllSubjectsList(){
        return sessionFactory.getCurrentSession().createQuery("from Subject").list();
    }
}
