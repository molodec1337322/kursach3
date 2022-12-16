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
public class AnswerDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Answer GetAnswerByID(int id){
        return sessionFactory.getCurrentSession().get(Answer.class, id);
    }

    public List<Answer> getAllAnswers(){
        return sessionFactory.getCurrentSession().createQuery("from Answer").list();
    }

    public List<Answer> getAllAnswersByTicket(int ticket_id){
        return sessionFactory.getCurrentSession().createQuery("from Answer where ticket_id=" + ticket_id).list();
    }

    public List<Answer> getAllAnswersByUser(int user_id){
        return sessionFactory.getCurrentSession().createQuery("from Answer where user_id=" + user_id).list();
    }

    public void createAnswer(Answer answer){
        sessionFactory.getCurrentSession().persist(answer);
    }
}
