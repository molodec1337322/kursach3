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
import com.example.kursach3.utils.CustomTripleSet;
import com.example.kursach3.utils.CustomQuadSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

        if(authentication != null){
            isAuthenticated = authentication.isAuthenticated();
            isTeacher = isUserTeacher(authentication);
        }

        if(!isAuthenticated){
            return "redirect:/auth/login";
        }

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
            UserDetails principals = (UserDetails) authentication.getPrincipal();
            User user = userDAO.getUserByEmail(principals.getUsername());
            username = user.getLast_name() + " " + user.getFirst_name() + " " + user.getPatronym();
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

        model.addAttribute("ticketsList", ticketDAO.getAllTicketsByUser(user.getId()));
        model.addAttribute("logged_user", username);

        return "tickets/ticketsCreatedList";
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
            UserDetails principals = (UserDetails) authentication.getPrincipal();
            User user = userDAO.getUserByEmail(principals.getUsername());
            username = user.getLast_name() + " " + user.getFirst_name() + " " + user.getPatronym();
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

        return "tickets/createTicket";
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
            UserDetails principals = (UserDetails) authentication.getPrincipal();
            User user = userDAO.getUserByEmail(principals.getUsername());
            username = user.getLast_name() + " " + user.getFirst_name() + " " + user.getPatronym();
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

    @GetMapping("/createdTickets/delete/{id}")
    public String DeleteTicket(@RequestParam(value = "id") int id,
                               Authentication authentication,
                               Model model){

        boolean isAuthenticated = false;
        boolean isTeacher = false;

        if(authentication != null){
            isAuthenticated = authentication.isAuthenticated();
            isTeacher = isUserTeacher(authentication);
        }

        if(!isAuthenticated){
            return "redirect:/auth/login";
        }

        if(!isTeacher){
            return "redirect:/access_denial";
        }

        ticketDAO.deleteTicketByID(id);

        return "redirect:/tickets/createdTickets";
    }

    /*
    Список присланных билетов на проверку к преподавателю
     */
    @GetMapping("/toCheck")
    public String GetTicketsToCheck(Authentication authentication,
                                    Model model){

        boolean isAuthenticated = false;
        boolean isTeacher = false;
        String username = null;

        if(authentication != null){
            isAuthenticated = authentication.isAuthenticated();
            UserDetails principals = (UserDetails) authentication.getPrincipal();
            User user = userDAO.getUserByEmail(principals.getUsername());
            username = user.getLast_name() + " " + user.getFirst_name() + " " + user.getPatronym();
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
        List<Ticket> tickets = ticketDAO.getAllTicketsByUser(user.getId());
        List<Answer> answers = new ArrayList<>();
        for (Ticket ticket: tickets) {
            answers.addAll(answerDAO.getAllAnswersByTicket(ticket.getId()));
        }
        List<CustomQuadSet<String, String, Integer, String>> answersInfo = new ArrayList<>();
        Ticket tempTicket = new Ticket();
        User tempUser = new User();
        for (Answer answer: answers){
            tempTicket = answer.getTicket();
            tempUser = answer.getUser();
            answersInfo.add(new CustomQuadSet<String, String, Integer, String>(tempUser.getLast_name() + " " + tempUser.getLast_name() + " " + tempUser.getPatronym(),
                    tempTicket.getTopic(),
                    answer.getId(),
                    answer.getEdited_at().toString()));
        }
        

        model.addAttribute("logged_user", username);
        model.addAttribute("answersList", answersInfo);

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
            UserDetails principals = (UserDetails) authentication.getPrincipal();
            User user = userDAO.getUserByEmail(principals.getUsername());
            username = user.getLast_name() + " " + user.getFirst_name() + " " + user.getPatronym();
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

        return "tickets/checkAnswer";
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
            UserDetails principals = (UserDetails) authentication.getPrincipal();
            User user = userDAO.getUserByEmail(principals.getUsername());
            username = user.getLast_name() + " " + user.getFirst_name() + " " + user.getPatronym();
            isTeacher = isUserTeacher(authentication);
        }

        if(!isAuthenticated){
            return "redirect:/auth/login";
        }

        if(isTeacher){
            return "redirect:/access_denial";
        }

        UserDetails principals = (UserDetails) authentication.getPrincipal();
        User user = userDAO.getUserByEmail(principals.getUsername());

        List<Answer> answers = answerDAO.getAllAnswersByUser(user.getId());
        List<CustomTripleSet<String, String, String>> answersInfo = new ArrayList<>();
        for(Answer answer: answers){
            answersInfo.add(new CustomTripleSet<String, String, String>(String.valueOf(answer.getId()), answer.getTicket().getTopic(), answer.getEdited_at().toString()));
        }

        model.addAttribute("logged_user", username);
        model.addAttribute("answersInfo", answersInfo);
        return "tickets/ticketsSentList";
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
        return "tickets/createAnswer";
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
            UserDetails principals = (UserDetails) authentication.getPrincipal();
            User user = userDAO.getUserByEmail(principals.getUsername());
            username = user.getLast_name() + " " + user.getFirst_name() + " " + user.getPatronym();
            isTeacher = isUserTeacher(authentication);
        }

        if(!isAuthenticated){
            return "redirect:/auth/login";
        }

        if(!isTeacher){
            return "redirect:/access_denial";
        }



        model.addAttribute("logged_user", username);
        return "tickets/findTicketByUID";
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
            return "redirect:/tickets/getTicket?not_found_error";
        }

        else{
            return "redirect:/tickets/ticket/" + UID;
        }
    }

    /*
    Форма выполнения билета
     */
    @GetMapping("/ticket/{uid}/")
    public String CreateNewAnswerToTicket(Authentication authentication,
                                          Model model){
        return "tickets/crateAnswer";
    }


    boolean isUserTeacher(Authentication authentication){
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"));
    }
}
