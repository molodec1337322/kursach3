package com.example.kursach3.controllers;

import com.example.kursach3.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService){
        this.userDetailsService = userDetailsService;
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

        return "tickets/ticketsToCheck";
    }

    @GetMapping("/toCheck/{id}")
    public String GetOneTicketToCheck(Authentication authentication,
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

        return "tickets/oneTicketToCheck";
    }



    boolean isUserTeacher(Authentication authentication){
        return authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_TEACHER"));
    }
}
