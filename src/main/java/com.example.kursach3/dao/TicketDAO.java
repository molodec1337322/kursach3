package com.example.kursach3.dao;

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
public class TicketDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Ticket getTicketByID(int id){
        return sessionFactory.getCurrentSession().get(Ticket.class, id);
    }

    public void createTicket(Ticket ticket){
        sessionFactory.getCurrentSession().persist(ticket);
    }

    public List<Ticket> getAllTickets(){
        return sessionFactory.getCurrentSession().createQuery("from Ticket").list();
    }

    public Ticket getTicketByUID(String uid){
        return (Ticket) sessionFactory.getCurrentSession().createQuery("from Ticket where UID='" + uid + "'").uniqueResult();
    }

    public List<Ticket> getAllTicketsByUser(int user_id){
        return sessionFactory.getCurrentSession().createQuery("from Ticket where user_id=" + user_id).list();
    }

    public void deleteTicketByID(int id){
        sessionFactory.getCurrentSession().delete(getTicketByID(id));
    }
}
