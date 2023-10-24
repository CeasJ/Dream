package com.backend.dream.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private HttpServletRequest request;

    @RequestMapping("/home")
    public String index() {
        return "/user/home/index";
    }

    @RequestMapping("/about")
    public String about() {
        return "/user/home/about";
    }

    @RequestMapping("/cart")
    public String cart() {
        return "/user/cart/cart";
    }

    @GetMapping("/history")
    public String history() {
        return "/user/home/history";
    }

    @RequestMapping("/profile")
    public String profile() {
        return "/user/home/profile";
    }
}
