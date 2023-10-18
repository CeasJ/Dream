package com.backend.dream.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @RequestMapping("/home")
    public String index() {
        return "/user/home/index";
    }

    @RequestMapping("/about")
    public String about() {
        return "/user/home/about";
    }

    @GetMapping("/product")
    public String product() {
        return "/user/product/product";
    }

    @RequestMapping("/cart")
    public String cart() {
        return "/user/cart/cart";
    }

    @RequestMapping("/profile")
    public String profile() {
        return "/user/home/profile";
    }
}
