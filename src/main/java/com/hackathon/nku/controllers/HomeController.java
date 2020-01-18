package com.hackathon.nku.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
    @RequestMapping("/")
    public String home(){
        return "dashboard";
    }

    @RequestMapping("/login")
    public String login(){
        return "login";
    }

}
