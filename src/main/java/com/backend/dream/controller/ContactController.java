package com.backend.dream.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ContactController {

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/contact")
    public String contact() {
        return "/user/contactus/contact";
    }
}
