package com.example.kursach3.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/access_denial")
public class AccessDenialController {

    @GetMapping("/access-denied")
    public String getAccessDenied() {
        return "/error/accessDenied";
    }

}
