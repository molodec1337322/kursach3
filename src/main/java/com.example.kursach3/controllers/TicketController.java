package com.example.kursach3.controllers;

import com.example.kursach3.dao.AnswerDAO;
import com.example.kursach3.dao.TicketDAO;
import com.example.kursach3.models.Answer;
import com.example.kursach3.models.Ticket;
import com.example.kursach3.models.User;
import com.example.kursach3.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private UserDetailsServiceImpl userDetailsService;
    private TicketDAO ticketDAO;
    private AnswerDAO answerDAO;

    @Autowired
    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService, TicketDAO ticketDAO, AnswerDAO answerDAO){
        this.userDetailsService = userDetailsService;
        this.ticketDAO = ticketDAO;
        this.answerDAO = answerDAO;
    }

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

        return "tickets/ticketsToCheck";
    }

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

        Answer answer = answerDAO.GetAnswerByID(id);
        Ticket ticket = answer.getTicket();
        User sentBy = answerDAO.GetAnswerByID(id).getUser();

        model.addAttribute("logged_user", username);
        model.addAttribute("answerText", answer.getAnswer_text());
        model.addAttribute("answerCreatedByName", sentBy.getFirst_name() + " " +
                sentBy.getLast_name() + " " +
                sentBy.getPassword());
        model.addAttribute("answerCreatedByID", sentBy.getId());
        model.addAttribute("ticketText", ticket.getTicket_text());

        return "tickets/oneTicketToCheck";
    }

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
        return "";
    }

    @GetMapping("/sent/{id}")
    public String GetOneSentTicket(Authentication authentication,
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
        return "";
    }



    boolean isUserTeacher(Authentication authentication){
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"));
    }
}
