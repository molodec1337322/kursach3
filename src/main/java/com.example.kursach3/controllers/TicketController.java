package com.example.kursach3.controllers;


import com.example.kursach3.dao.AnswerDAO;
import com.example.kursach3.dao.CommentsDAO;
import com.example.kursach3.dao.SubjectDAO;
import com.example.kursach3.dao.TicketDAO;
import com.example.kursach3.dao.UserDAO;
import com.example.kursach3.models.Answer;
import com.example.kursach3.models.Subject;
import com.example.kursach3.models.Ticket;
import com.example.kursach3.models.User;
import com.example.kursach3.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private UserDetailsServiceImpl userDetailsService;
    private TicketDAO ticketDAO;
    private AnswerDAO answerDAO;
    private CommentsDAO commentsDAO;
    private UserDAO userDAO;
    private SubjectDAO subjectDAO;

    @Autowired
    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService, TicketDAO ticketDAO, AnswerDAO answerDAO, CommentsDAO commentsDAO, UserDAO userDAO, SubjectDAO subjectDAO){
        this.userDetailsService = userDetailsService;
        this.ticketDAO = ticketDAO;
        this.answerDAO = answerDAO;
        this.commentsDAO = commentsDAO;
        this.userDAO = userDAO;
        this.subjectDAO = subjectDAO;
    }

    /*
    начальная страница с редиректом
     */
    @GetMapping("/redirect")
    public String TicketsList(Authentication authentication,
                              Model model){
        boolean isAuthenticated = false;
        boolean isTeacher = false;
        String username = null;

        if(authentication != null){
            isAuthenticated = authentication.isAuthenticated();
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
            isTeacher = isUserTeacher(authentication);
        }

        if(!isAuthenticated){
            return "redirect:/auth/login";
        }

        model.addAttribute("subjectsList", subjectDAO.getAllSubjectsList());
        model.addAttribute("logged_user", username);

        if(!isTeacher){
            return "redirect:/tickets/sent";
        }
        else{
            return "redirect:/tickets/createdTickets";
        }
    }

    /*





    Роутинг преподаватели





     */

    /*
    Список созданных билетов преподавателем
     */
    @GetMapping("/createdTickets")
    public String CreatedTickets(Authentication authentication,
                                 Model model){
        boolean isAuthenticated = false;
        boolean isTeacher = false;
        String username = null;

        if(authentication != null){
            isAuthenticated = authentication.isAuthenticated();
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
            isTeacher = isUserTeacher(authentication);
        }

        if(!isAuthenticated){
            return "redirect:/auth/login";
        }

        if(!isTeacher){
            return "redirect:/access_denial";
        }

        model.addAttribute("subjectsList", subjectDAO.getAllSubjectsList());
        model.addAttribute("logged_user", username);

        return "tickets/создание-варианта";
    }

    /*
    Создание нового билета
     */
    @GetMapping("/newTicket")
    public String CreateNewTicket(Authentication authentication,
                                  Model model){

        boolean isAuthenticated = false;
        boolean isTeacher = false;
        String username = null;

        if(authentication != null){
            isAuthenticated = authentication.isAuthenticated();
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
            isTeacher = isUserTeacher(authentication);
        }

        if(!isAuthenticated){
            return "redirect:/auth/login";
        }

        if(!isTeacher){
            return "redirect:/access_denial";
        }

        model.addAttribute("subjectsList", subjectDAO.getAllSubjectsList());
        model.addAttribute("logged_user", username);

        return "tickets/создание-варианта";
    }

    /*
    отправка на сервер билета
     */
    @PostMapping("/newTicket")
    public String CreateNewTicketPost(@RequestParam(value = "ticketTopic") String ticketTopic,
                                      @RequestParam(value = "ticketText") String ticketText,
                                      @RequestParam(value = "subject") int subjectId,
                                      Authentication authentication,
                                      Model model){

        boolean isAuthenticated = false;
        boolean isTeacher = false;
        String username = null;

        if(authentication != null){
            isAuthenticated = authentication.isAuthenticated();
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
            isTeacher = isUserTeacher(authentication);
        }

        if(!isAuthenticated){
            return "redirect:/auth/login";
        }

        if(!isTeacher){
            return "redirect:/access_denial";
        }

        UserDetails principals = (UserDetails) authentication.getPrincipal();
        User user = userDAO.getUserByEmail(principals.getUsername());
        Subject subject = subjectDAO.getSubjectByID(subjectId);

        Ticket ticket = new Ticket();
        ticket.setCreated_at(new Date());
        ticket.setEdited_at(new Date());
        ticket.setTicket_UID(java.util.UUID.randomUUID().toString());
        ticket.setTicket_text(ticketText);
        ticket.setTopic(ticketTopic);
        ticket.setSubject(subject);
        ticket.setUser(user);

        System.out.println();

        ticketDAO.createTicket(ticket);

        model.addAttribute("logged_user", username);

        return "redirect:/tickets/createdTickets";
    }

    /*
    Список присланных билетов на проверку со стороны преподавателя
     */
    @GetMapping("/toCheck")
    public String GetTicketsToCheck(Authentication authentication,
                                    Model model){

        boolean isAuthenticated = false;
        boolean isTeacher = false;
        String username = null;

        if(authentication != null){
            isAuthenticated = authentication.isAuthenticated();
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
            isTeacher = isUserTeacher(authentication);
        }

        if(!isAuthenticated){
            return "redirect:/auth/login";
        }

        if(!isTeacher){
            return "redirect:/access_denial";
        }

        model.addAttribute("logged_user", username);
        model.addAttribute("answersToCheck", answerDAO.getAllAnswers());
        model.addAttribute("tickets", ticketDAO.getAllTickets());

        return "tickets/ticketsToCheck";
    }

    /*
    Проверка билета преподавателем
     */
    @GetMapping("/toCheck/{id}")
    public String GetOneTicketToCheck(@PathVariable("id") int id,
                                      Authentication authentication,
                                      Model model){

        boolean isAuthenticated = false;
        boolean isTeacher = false;
        String username = null;

        if(authentication != null){
            isAuthenticated = authentication.isAuthenticated();
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
            isTeacher = isUserTeacher(authentication);
        }

        if(!isAuthenticated){
            return "redirect:/auth/login";
        }

        if(!isTeacher){
            return "redirect:/access_denial";
        }

        model.addAttribute("logged_user", username);
        model.addAttribute("answer", answerDAO.GetAnswerByID(id));
        model.addAttribute("comments", commentsDAO.GetCommentsAnswerID(id));

        return "tickets/Ответ-на-задание";
    }

    /*





    Роутинг для учащегося





     */

    /*
    Список отправленных ответов на проверку со стороны учащегося
     */
    @GetMapping("/sent")
    public String GetSentTickets(Authentication authentication,
                                 Model model){
        boolean isAuthenticated = false;
        boolean isTeacher = false;
        String username = null;

        if(authentication != null){
            isAuthenticated = authentication.isAuthenticated();
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
            isTeacher = isUserTeacher(authentication);
        }

        if(!isAuthenticated){
            return "redirect:/auth/login";
        }

        if(isTeacher){
            return "redirect:/access_denial";
        }

        model.addAttribute("logged_user", username);
        model.addAttribute("");
        return "tickets/ticketsSent";
    }

    /*
    Просмотр конкретного отправленного билета
     */
    @GetMapping("/sent/{id}")
    public String GetOneSentTicket(@PathVariable("id") int id,
                                   Authentication authentication,
                                   Model model){
        boolean isAuthenticated = false;
        boolean isTeacher = false;
        String username = null;

        if(authentication != null){
            isAuthenticated = authentication.isAuthenticated();
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
            isTeacher = isUserTeacher(authentication);
        }

        if(!isAuthenticated){
            return "redirect:/auth/login";
        }

        if(isTeacher){
            return "redirect:/access_denial";
        }

        model.addAttribute("logged_user", username);
        model.addAttribute("comments", commentsDAO.GetCommentsAnswerID(id));
        return "tickets/Ответ-на-задание";
    }

    /*
    Поиск билета по UID
     */
    @GetMapping("/ticket/getTicket")
    public String GetTicketByUIDToStudent(Authentication authentication,
                                          Model model){
        boolean isAuthenticated = false;
        boolean isTeacher = false;
        String username = null;

        if(authentication != null){
            isAuthenticated = authentication.isAuthenticated();
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
            isTeacher = isUserTeacher(authentication);
        }

        if(!isAuthenticated){
            return "redirect:/auth/login";
        }

        if(!isTeacher){
            return "redirect:/access_denial";
        }

        model.addAttribute("logged_user", username);
        return "tickets/Указание-варианта";
    }

    /*
    Поиск билета по UID
    Получение и редирект на форму выполнения задания
     */
    @PostMapping("/ticket/getTicket")
    public String GetTicketByUIDToStudent(@RequestParam("UID") String UID,
                                          Authentication authentication,
                                          Model model){
        boolean isAuthenticated = false;
        boolean isTeacher = false;
        //String username = null;

        if(authentication != null){
            isAuthenticated = authentication.isAuthenticated();
            //username = ((UserDetails) authentication.getPrincipal()).getUsername();
            isTeacher = isUserTeacher(authentication);
        }

        if(!isAuthenticated){
            return "redirect:/auth/login";
        }

        if(!isTeacher){
            return "redirect:/access_denial";
        }

        Ticket ticket = ticketDAO.getTicketByUID(UID);

        if(ticket == null){
            return "redirect:/not_found";
        }

        else{
            return "redirect:/tickets/ticket/{uid}";
        }
    }

    /*
    Форма выполнения билета
     */
    @GetMapping("/ticket/{uid}/")
    public String CreateNewAnswerToTicket(Authentication authentication,
                                          Model model){
        return "tickets/Ответ-на-задание";
    }


    boolean isUserTeacher(Authentication authentication){
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"));
    }
}
