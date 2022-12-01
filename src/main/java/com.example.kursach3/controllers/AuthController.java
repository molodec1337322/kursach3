package com.example.kursach3.controllers;

import com.example.kursach3.models.User;
import com.example.kursach3.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    public void setUserDetailsService(UserDetailsServiceImpl userDetailsService){
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/login")
    public String login(Authentication authentication){
        return "auth/auth/Войти";
    }

    @GetMapping("/registration")
    public String registration(){
        return "auth/auth/Зарегистрироваться";
    }

    @PostMapping("/registration")
    public String addUser(@RequestParam(value = "password") String password,
                          @RequestParam(value = "email") String email,
                          @RequestParam(value = "first_name") String first_name,
                          @RequestParam(value = "last_name") String last_name,
                          @RequestParam(value = "patronym") String patronym,
                          Model model){

        User newUser = new User();
        newUser.setEmail(email.toLowerCase(Locale.ROOT));
        newUser.setPassword(password);
        newUser.setRole("STUDENT");
        newUser.setFirst_name(first_name);
        newUser.setLast_name(last_name);
        newUser.setPatronym(patronym);

        if(!userDetailsService.saveUser(newUser)){
            return "redirect:/auth/registration?collision_error";
        }
        return "redirect:/subjects";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/subjects";
    }
}