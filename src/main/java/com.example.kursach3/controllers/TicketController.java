package com.example.kursach3.controllers;

import com.example.kursach3.dao.AnswerDAO;
import com.example.kursach3.dao.CommentsDAO;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private UserDetailsServiceImpl userDetailsService;
    private TicketDAO ticketDAO;
    private AnswerDAO answerDAO;
    private CommentsDAO commentsDAO;

    @Autowired
    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService, TicketDAO ticketDAO, AnswerDAO answerDAO, CommentsDAO commentsDAO){
        this.userDetailsService = userDetailsService;
        this.ticketDAO = ticketDAO;
        this.answerDAO = answerDAO;
        this.commentsDAO = commentsDAO;
    }

    @GetMapping("/newTicket")
    public String CreateNewTicket(Authentication authentication,
                                  Model model){
        return "";
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
        model.addAttribute("tickets", ticketDAO.getAllTickets());

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

        model.addAttribute("logged_user", username);
        model.addAttribute("answer", answerDAO.GetAnswerByID(id));
        model.addAttribute("comments", commentsDAO.GetCommentsAnswerID(id));

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
        model.addAttribute("");
        return "tickets/ticketsSent";
    }

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
        return "tickets/ticketsSentOne";
    }

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
        return "";
    }

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

    @GetMapping("/ticket/{uid}/")
    public String CreateNewAnswerToTicket(Authentication authentication,
                                          Model model){
        return "";
    }


    boolean isUserTeacher(Authentication authentication){
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"));
    }
}
