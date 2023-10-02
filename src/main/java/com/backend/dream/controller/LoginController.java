package com.backend.dream.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @GetMapping("/form")
    public String formLogin(){
        return "loginForm";
    }
    @RequestMapping("/demo")
    public String testUI(){
        return "test";
    }
}
