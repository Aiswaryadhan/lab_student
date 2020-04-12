package com.gec.lab_student.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class WebController {

    @RequestMapping("/")
    @ResponseBody
    public String index() {
        return "Welcome";
    }

    @RequestMapping("/hello")
    @ResponseBody
    public String welcome(){
        return "Hello World";
    }

    @RequestMapping("/login")
    public String login() {
        return "login_page";
    }

    @RequestMapping("/home")
    public String home() {
        return "home";
    }

    @RequestMapping("/doubts")
    public String doubts() {
        return "doubts_page";
    }
}
